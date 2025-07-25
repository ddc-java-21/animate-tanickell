package edu.cnm.deepdive.animate.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.animate.model.dto.Anime;
import edu.cnm.deepdive.animate.model.entity.Apod;
import edu.cnm.deepdive.animate.service.AnimeService;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class AnimeViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private static final String TAG = AnimeViewModel.class.getSimpleName();

  private final MutableLiveData<Anime> anime;
  private final MutableLiveData<List<Anime>> animes;
  private final MutableLiveData<Uri> downloadedImage;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final AnimeService animeService;

  public AnimeViewModel(@NonNull Application application) {
    super(application);
    anime = new MutableLiveData<>();
    animes = new MutableLiveData<>();
    downloadedImage = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    animeService = AnimeService.getInstance(); // gets reference to the one AnimeService instance that exists
    Boolean sfw = true;
    fetch(sfw);
  }

  public LiveData<Anime> getAnime() {
    return anime;
  }

  public void setAnime(Anime anime) {
    this.anime.setValue(anime);
  }

  public LiveData<List<Anime>> getAnimes() {
    return animes;
  }

  public LiveData<Uri> getDownloadedImage() {
    return downloadedImage;
  }

  public void clearDownloadedImage() {
    downloadedImage.setValue(null);
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void fetch(int malId) {
    throwable.setValue(null); // monitoring no object right now // we're sure we're on the UI thread
    animeService
        .getAnime(malId)
        .subscribe( // consumers:
            anime::postValue, // invoked on thread other than UI thread
            this::postThrowable, // downstream
            pending
        );
  }

  public void fetch(Boolean sfw) {
    throwable.setValue(null);
    animeService
        .getAnimes(sfw)
        .subscribe(
            animes::postValue,
            this::postThrowable,
            pending
        );
  }

  public void downloadImage(String title, URL url) {
    throwable.setValue(null);
    clearDownloadedImage();
    animeService
        .downloadImage(title, url)
        .subscribe(
            downloadedImage::postValue,
            this::postThrowable,
            pending
        );
  }

  @Override
  public void onStop(@NotNull LifecycleOwner owner) {
    pending.clear();
    DefaultLifecycleObserver.super.onStop(owner);
  }

  private void postThrowable(Throwable throwable) {
    Log.e(TAG, throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}
