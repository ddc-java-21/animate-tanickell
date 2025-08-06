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
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.animate.model.dto.Anime;
import edu.cnm.deepdive.animate.model.entity.Apod;
import edu.cnm.deepdive.animate.model.entity.User;
import edu.cnm.deepdive.animate.service.AnimeService;
import edu.cnm.deepdive.animate.service.UserRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

@HiltViewModel
public class AnimeViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private static final String TAG = AnimeViewModel.class.getSimpleName();

  private final UserRepository userRepository;


  private final MutableLiveData<User> user;
  private final MutableLiveData<Anime> anime;
  private final MutableLiveData<List<Anime>> animes;
  private final MutableLiveData<Uri> downloadedImage;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final AnimeService animeService;

//  @Inject
  public AnimeViewModel(@NonNull Application application, UserRepository userRepository,
      MutableLiveData<User> user) {
    super(application);
    this.userRepository = userRepository;
    this.user = user;
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

  public void fetchUser() { // could query the notes for the user before the user is loaded;
    throwable.setValue(null);
    userRepository
        .getCurrentUser()
        .subscribe(
            user::postValue,
            this::postThrowable,
            pending
        );
  }

  private void postThrowable(Throwable throwable) {
    Log.e(TAG, throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}
