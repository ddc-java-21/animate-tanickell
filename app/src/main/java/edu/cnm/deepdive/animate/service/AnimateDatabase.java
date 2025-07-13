package edu.cnm.deepdive.animate.service;

import android.net.Uri;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.animate.model.entity.Anime;
import edu.cnm.deepdive.animate.model.entity.AnimeTag;
import edu.cnm.deepdive.animate.model.entity.Favorite;
import edu.cnm.deepdive.animate.model.entity.Tag;
import edu.cnm.deepdive.animate.model.entity.User;
import edu.cnm.deepdive.animate.service.AnimateDatabase.Converters;
import edu.cnm.deepdive.animate.service.dao.AnimeDao;
import edu.cnm.deepdive.animate.service.dao.AnimeTagDao;
import edu.cnm.deepdive.animate.service.dao.FavoriteDao;
import edu.cnm.deepdive.animate.service.dao.TagDao;
import edu.cnm.deepdive.animate.service.dao.UserDao;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

@Database(
    entities = {User.class, Anime.class, Tag.class, Favorite.class, AnimeTag.class},
    version = AnimateDatabase.VERSION
)
@TypeConverters({Converters.class})
public abstract class AnimateDatabase extends RoomDatabase {

  static final int VERSION = 1;

  private static final String NAME = "animate-db";

  public static String getName() { return NAME; }

  public abstract UserDao getUserDao();
  public abstract AnimeDao getAnimeDao();
  public abstract TagDao getTagDao();
  public abstract FavoriteDao getFavoriteDao();
  public abstract AnimeTagDao getAnimeTagDao();

  public static class Converters {

    @TypeConverter
    public static Long fromInstant(Instant value) {
      return (value != null) ? value.toEpochMilli() : null;
    }

    @TypeConverter
    public static Instant fromLong(Long value) {
      return (value != null) ? Instant.ofEpochMilli(value) : null;
    }

//    @TypeConverter
//    public static String fromUri(Uri value) {
//      return  (value != null) ? value.toString() : null;
//    }
//
//    @TypeConverter
//    public static Uri fromString(String value) {
//      return (value != null) ? Uri.parse(value) : null;
//    }

    @TypeConverter
    public static String fromURL(URL value) {
      return  (value != null) ? value.toString() : null;
    }

    @TypeConverter
    public static URL fromString(String value) {
      try {
        return (value != null) ? new URL(value) : null;
      } catch (MalformedURLException e) {
        throw new RuntimeException(e);
      }
    }

  }

}
