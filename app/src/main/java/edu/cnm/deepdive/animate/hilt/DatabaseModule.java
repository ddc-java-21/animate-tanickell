package edu.cnm.deepdive.animate.hilt;

import android.content.Context;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.cnm.deepdive.animate.service.AnimateDatabase;
import edu.cnm.deepdive.animate.service.dao.UserDao;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

  @Provides
  @Singleton
  AnimateDatabase provideDatabase(@ApplicationContext Context context) { // DONE: 6/11/25 Add preloader if necessary.
    return Room.databaseBuilder(context, AnimateDatabase.class, AnimateDatabase.getName())
//        .addCallback(preloader)
        .build();
  }

  @Provides
  @Singleton
  UserDao provideUserDao(AnimateDatabase database) {
    return database.getUserDao();
  }


}
