package edu.cnm.deepdive.animate;

import android.app.Application;
import com.squareup.picasso.Picasso;
import dagger.hilt.android.HiltAndroidApp;
import edu.cnm.deepdive.animate.service.ApodProxy;
import edu.cnm.deepdive.animate.service.ApodService;

@HiltAndroidApp
public class AnimateApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    ApodProxy.setContext(this);
    ApodService.setContext(this);
    Picasso.setSingletonInstance(new Picasso.Builder(this).build());
  }
}
