package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.Theme;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface ThemeDao {

  @Insert
  Single<Long> insert(Theme theme);

  default Single<Theme> insertAndRefresh(Theme theme) {
    return Single
        .just(theme)
        .doOnSuccess((t) -> {
          Instant now = Instant.now();
          t.setCreated(now);
          t.setModified(now);
        })
        .flatMap(this::insert)
        .doOnSuccess(theme::setId)
        .map((id) -> theme);
  }

  @Insert Single<List<Long>> insert(List<Theme> themes);

  default Single<List<Theme>> insertAndRefresh(List<Theme> themes) {
    return Single
        .just(themes)
        .doOnSuccess((ts) -> {
          Instant now = Instant.now();
          ts.forEach(t -> {
            t.setCreated(now);
            t.setModified(now);
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Theme> themeIterator = themes.iterator();
          while (idIterator.hasNext() && themeIterator.hasNext()) {
            themeIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> themes);
  }

  @Update
  Single<Integer> update(Theme theme);

  default Single<Theme> updateAndRefresh(Theme theme) {
    return Single
        .just(theme)
        .doOnSuccess((t) -> {
          t.setModified(Instant.now());
        })
        .flatMap(this::update)
        .map((count) -> theme);
  }

  @Update
  Single<Integer> update(Collection<Theme> themes);

  default Single<Collection<Theme>> updateAndRefresh(Collection<Theme> themes) {
    return Single
        .just(themes)
        .doOnSuccess((ts) -> {
          ts.forEach((t) -> {
            t.setModified(Instant.now());
          });
        })
        .flatMap(this::update)
        .map((count) -> themes);
  }

  @Delete
  Single<Integer> delete(Theme theme);

  @Query("DELETE FROM theme") // truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM theme WHERE theme_id = :id")
  LiveData<Theme> get(long id);

  @Query("SELECT * FROM theme ORDER BY name ASC")
  LiveData<List<Theme>> getAll();

  // Get all themes associated with an anime
  @Query("SELECT t.* FROM theme AS t "
      + "JOIN anime_theme AS at ON at.theme_id = t.theme_id "
      + "JOIN anime AS a ON a.anime_id = at.anime_id "
      + "WHERE a.anime_id = :animeId "
      + "ORDER BY t.name ASC")
  LiveData<List<Theme>> getThemeByAnimeOrderByNameAsc(long animeId);

}
