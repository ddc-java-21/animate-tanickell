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
import edu.cnm.deepdive.animate.model.dto.Anime;       // USING DTO ANIME, NOT ENTITY ANIME
import edu.cnm.deepdive.animate.model.dto.Anime.InstanceWrapper;
import edu.cnm.deepdive.animate.model.dto.Anime.ListWrapper;
import edu.cnm.deepdive.animate.model.entity.Apod;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.functions.Function;
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

public class AnimeService {

  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final int BUFFER_SIZE = 16_384;
  @SuppressLint("StaticFieldLeak")
  private static Context context;

  private final AnimeProxy proxy;
//  private final String apiKey;
  private final Scheduler scheduler;

  private AnimeService() throws IOException {
    proxy = AnimeProxy.getInstance();
//    apiKey = context.getString(R.string.api_key); // TN 2025-07-24 don't need API key for now
    scheduler = Schedulers.io();
  }

  public static void setContext(Context context) {
    AnimeService.context = context;
  }

  public Single<Anime> getAnime(int malId) {
    return proxy
        .get(malId)
        .map(new Function<InstanceWrapper, Anime>() {
          @Override
          public Anime apply(InstanceWrapper instanceWrapper) {
            return instanceWrapper.getData();
          }
        })
        .subscribeOn(scheduler); // returning instead of an anime object, the piece of machinery that will fetch the Anime and pass it downstream WHEN TURNED ON
  }
  public Single<List<Anime>> getAnimes(Boolean sfw) {
    return proxy
        .get(sfw)
        .map(new Function<ListWrapper, List<Anime>>() {
          @Override
          public List<Anime> apply(ListWrapper listWrapper) {
            return listWrapper.getData();
          }
        })
        .subscribeOn(scheduler);
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

  public static AnimeService getInstance() {
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
        context.getString(R.string.anime_directory_format, Environment.DIRECTORY_PICTURES));
    values.put(MediaColumns.IS_PENDING, 1);
    String mimeType = response.headers().get(CONTENT_TYPE_HEADER);
    if (mimeType != null) {
      values.put(MediaColumns.MIME_TYPE, mimeType);
    }
    return resolver.insert(Media.EXTERNAL_CONTENT_URI, values); // hey content resolver, get me the uri that you get when you put this in the database
  }

  private static class Holder {

    @SuppressLint("StaticFieldLeak")
    private static final AnimeService INSTANCE;

    static {
      try {
        INSTANCE = new AnimeService();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }

  }

}
