package edu.cnm.deepdive.animate.service;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import edu.cnm.deepdive.animate.R;
import edu.cnm.deepdive.animate.model.Animate;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AnimateService {

  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final int BUFFER_SIZE = 16_384;
  @SuppressLint("StaticFieldLeak")
  private static Context context;

  private final AnimateProxy proxy;
  private final String apiKey;
  private final Scheduler scheduler;

  private AnimateService() throws IOException {
    proxy = AnimateProxy.getInstance();
    apiKey = context.getString(R.string.api_key);
    scheduler = Schedulers.io();
  }

  public static void setContext(Context context) {
    AnimateService.context = context;
  }

  public Single<Animate> getAnimate(LocalDate date) {
    return proxy
        .get(date, apiKey)
        .subscribeOn(scheduler); // returning instead of an animate object, the piece of machinery that will fetch the Animate and pass it downstream WHEN TURNED ON
  }
  public Single<List<Animate>> getAnimates(LocalDate startDate, LocalDate endDate) {
    return proxy
        .get(startDate, endDate, apiKey)
        .subscribeOn(scheduler)
        .map(Arrays::asList);
  }

  public Single<Uri> downloadImage(String title, URL url) {
    return Single.create((SingleEmitter<Uri> emitter) -> {
      Response<ResponseBody> response = proxy.download(url.toString()).execute();
      if (response.isSuccessful()) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = addEntry(title, response, resolver);
        if (uri != null) {
          writeFileFromResponse(emitter, response, resolver, uri);
        } else {
          emitter.onError(new RuntimeException()); // should never get here!
        }
      } else {
        emitter.onError(new IOException(response.message()));
      }
    })
        .subscribeOn(scheduler);
  }

  public static AnimateService getInstance() {
    return Holder.INSTANCE;
  }

  private static void writeFileFromResponse(SingleEmitter<Uri> emitter, Response<ResponseBody> response,
      ContentResolver resolver, Uri uri) {
    //noinspection DataFlowIssue
    try (
        ResponseBody responseBody = response.body(); // implements closeable, so can be used here
        InputStream input = responseBody.byteStream(); // if we got this far, we know this is not null
        OutputStream output = resolver.openOutputStream(uri) // hey resolver, open output stream w uri you gave me earlier (write the file in fs)
    ) {
      byte[] buffer = new byte[BUFFER_SIZE];
      int bytesRead;
      while ((bytesRead = input.read(buffer)) >= 0) {
        output.write(buffer, 0, bytesRead);
      }
      ContentValues confirmationValues = new ContentValues();
      confirmationValues.put(MediaColumns.IS_PENDING, 0);
      resolver.update(uri, confirmationValues, null, null);
      emitter.onSuccess(uri);
    } catch (IOException e) {
      resolver.delete(uri, null, null);
      emitter.onError(e);
    }
  }

  private static Uri addEntry(String title, Response<ResponseBody> response,
      ContentResolver resolver) {
    ContentValues values = new ContentValues();
    values.put(MediaColumns.DISPLAY_NAME, title);
    values.put(MediaColumns.RELATIVE_PATH,
        context.getString(R.string.animate_directory_format, Environment.DIRECTORY_PICTURES));
    values.put(MediaColumns.IS_PENDING, 1);
    String mimeType = response.headers().get(CONTENT_TYPE_HEADER);
    if (mimeType != null) {
      values.put(MediaColumns.MIME_TYPE, mimeType);
    }
    return resolver.insert(Media.EXTERNAL_CONTENT_URI, values); // hey content resolver, get me the uri that you get when you put this in the database
  }

  private static class Holder {

    @SuppressLint("StaticFieldLeak")
    private static final AnimateService INSTANCE;

    static {
      try {
        INSTANCE = new AnimateService();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }

  }

}
