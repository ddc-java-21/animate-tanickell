package edu.cnm.deepdive.animate;

import android.app.Application;
import com.squareup.picasso.Picasso;
import dagger.hilt.android.HiltAndroidApp;
import edu.cnm.deepdive.animate.service.AnimateProxy;
import edu.cnm.deepdive.animate.service.AnimateService;

@HiltAndroidApp
public class AnimateApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AnimateProxy.setContext(this);
    AnimateService.setContext(this);
    Picasso.setSingletonInstance(new Picasso.Builder(this).build());
  }
}
