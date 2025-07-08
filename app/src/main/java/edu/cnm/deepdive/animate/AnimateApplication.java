package edu.cnm.deepdive.animate;

import android.app.Application;
import com.squareup.picasso.Picasso;
import dagger.hilt.android.HiltAndroidApp;
import edu.cnm.deepdive.animate.service.AnimeProxy;
import edu.cnm.deepdive.animate.service.AnimeService;

@HiltAndroidApp
public class AnimateApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AnimeProxy.setContext(this);
    AnimeService.setContext(this);
    Picasso.setSingletonInstance(new Picasso.Builder(this).build());
  }

}
