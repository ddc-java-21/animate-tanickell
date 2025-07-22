package edu.cnm.deepdive.animate.service;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.animate.model.entity.Anime;
import edu.cnm.deepdive.animate.model.entity.AnimeGenre;
import edu.cnm.deepdive.animate.model.entity.AnimeLicensor;
import edu.cnm.deepdive.animate.model.entity.AnimeProducer;
import edu.cnm.deepdive.animate.model.entity.AnimeStudio;
import edu.cnm.deepdive.animate.model.entity.AnimeTag;
import edu.cnm.deepdive.animate.model.entity.AnimeTheme;
import edu.cnm.deepdive.animate.model.entity.Favorite;
import edu.cnm.deepdive.animate.model.entity.Genre;
import edu.cnm.deepdive.animate.model.entity.Licensor;
import edu.cnm.deepdive.animate.model.entity.Producer;
import edu.cnm.deepdive.animate.model.entity.Studio;
import edu.cnm.deepdive.animate.model.entity.Tag;
import edu.cnm.deepdive.animate.model.entity.Theme;
import edu.cnm.deepdive.animate.model.entity.User;
import edu.cnm.deepdive.animate.service.AnimateDatabase.Converters;
import edu.cnm.deepdive.animate.service.dao.AnimeDao;
import edu.cnm.deepdive.animate.service.dao.AnimeGenreDao;
import edu.cnm.deepdive.animate.service.dao.AnimeLicensorDao;
import edu.cnm.deepdive.animate.service.dao.AnimeProducerDao;
import edu.cnm.deepdive.animate.service.dao.AnimeStudioDao;
import edu.cnm.deepdive.animate.service.dao.AnimeTagDao;
import edu.cnm.deepdive.animate.service.dao.AnimeThemeDao;
import edu.cnm.deepdive.animate.service.dao.FavoriteDao;
import edu.cnm.deepdive.animate.service.dao.GenreDao;
import edu.cnm.deepdive.animate.service.dao.LicensorDao;
import edu.cnm.deepdive.animate.service.dao.ProducerDao;
import edu.cnm.deepdive.animate.service.dao.StudioDao;
import edu.cnm.deepdive.animate.service.dao.TagDao;
import edu.cnm.deepdive.animate.service.dao.ThemeDao;
import edu.cnm.deepdive.animate.service.dao.UserDao;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

@Database(
    entities = {
        User.class, Anime.class, Tag.class,
        Genre.class, Studio.class, Theme.class,
        Producer.class, Licensor.class,
        Favorite.class, AnimeTag.class, AnimeGenre.class,
        AnimeStudio.class, AnimeTheme.class,
        AnimeProducer.class, AnimeLicensor.class
    },
    version = AnimateDatabase.VERSION
)
@TypeConverters({Converters.class})
public abstract class AnimateDatabase extends RoomDatabase {

  static final int VERSION = 1;

  private static final String NAME = "animate-db";

  public static String getName() {
    return NAME;
  }

  public abstract UserDao getUserDao();
  public abstract AnimeDao getAnimeDao();
  public abstract TagDao getTagDao();
  public abstract GenreDao getGenreDao();
  public abstract StudioDao getStudioDao();
  public abstract ThemeDao getThemeDao();
  public abstract ProducerDao getProducerDao();
  public abstract LicensorDao getLicensorDao();
  public abstract FavoriteDao getFavoriteDao();
  public abstract AnimeTagDao getAnimeTagDao();
  public abstract AnimeGenreDao getAnimeGenreDao();
  public abstract AnimeStudioDao getAnimeStudioDao();
  public abstract AnimeThemeDao getAnimeThemeDao();
  public abstract AnimeProducerDao getAnimeProducerDao();
  public abstract AnimeLicensorDao getAnimeLicensorDao();

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
      return (value != null) ? value.toString() : null;
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
