package edu.cnm.deepdive.animate;

import android.app.Application;
import androidx.room.RoomDatabase;
import com.squareup.picasso.Picasso;
import dagger.hilt.android.HiltAndroidApp;
import edu.cnm.deepdive.animate.service.AnimateDatabase;
import edu.cnm.deepdive.animate.service.AnimeProxy;
import edu.cnm.deepdive.animate.service.AnimeService;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltAndroidApp
public class AnimateApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AnimeProxy.setContext(this);
    AnimeService.setContext(this);
    Picasso.setSingletonInstance(new Picasso.Builder(this).build());

    new RoomDatabase.Builder<>(this, AnimateDatabase.class, "animate-db")
        .build()
        .getAnimeDao()
        .deleteAll()
        .subscribeOn(Schedulers.io())
        .subscribe();
  }

}
