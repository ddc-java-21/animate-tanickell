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
import edu.cnm.deepdive.animate.model.Animate;
import edu.cnm.deepdive.animate.service.AnimateService;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class AnimateViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private static final String TAG = AnimateViewModel.class.getSimpleName();

  private final MutableLiveData<Animate> animate;
  private final MutableLiveData<List<Animate>> animates;
  private final MutableLiveData<Uri> downloadedImage;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final AnimateService animateService;

  public AnimateViewModel(@NonNull Application application) {
    super(application);
    animate = new MutableLiveData<>();
    animates = new MutableLiveData<>();
    downloadedImage = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    animateService = AnimateService.getInstance(); // gets reference to the one AnimateService instance that exists
    LocalDate today = LocalDate.now();
    LocalDate lastMonth = today.minusMonths(1);
    fetch(lastMonth, today);
  }

  public LiveData<Animate> getAnimate() {
    return animate;
  }

  public void setAnimate(Animate animate) {
    this.animate.setValue(animate);
  }

  public LiveData<List<Animate>> getAnimates() {
    return animates;
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

  public void fetch(LocalDate date) {
    throwable.setValue(null); // monitoring no object right now // we're sure we're on the UI thread
    animateService
        .getAnimate(date)
        .subscribe( // consumers:
            animate::postValue, // invoked on thread other than UI thread
            this::postThrowable, // downstream
            pending
        );
  }

  public void fetch(LocalDate startDate, LocalDate endDate) {
    throwable.setValue(null);
    animateService
        .getAnimates(startDate, endDate)
        .subscribe(
            animates::postValue,
            this::postThrowable,
            pending
        );
  }

  public void downloadImage(String title, URL url) {
    throwable.setValue(null);
    clearDownloadedImage();
    animateService
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
