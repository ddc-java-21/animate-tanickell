package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.AnimeTheme;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface AnimeThemeDao {

  @Insert
  Single<Long> insert(AnimeTheme animeTheme);

  default Single<AnimeTheme> insertAndRefresh(AnimeTheme animeTheme) {
    return Single
        .just(animeTheme)
        .doOnSuccess((at) -> at.setCreated(Instant.now()))
        .flatMap(this::insert)
        .doOnSuccess(animeTheme::setId)
        .map((id) -> animeTheme);
  }

  @Insert
  Single<List<Long>> insert(List<AnimeTheme> animeThemes);

  default Single<List<AnimeTheme>> insertAndRefresh(List<AnimeTheme> animeThemes) {
    return Single
        .just(animeThemes)
        .doOnSuccess((ats) -> {
          ats.forEach((at) -> {
            at.setCreated(Instant.now());
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> iterator = ids.iterator();
          Iterator<AnimeTheme> animeThemeIterator = animeThemes.iterator();
          while (iterator.hasNext() && animeThemeIterator.hasNext()) {
            animeThemeIterator.next().setId(iterator.next());
          }
        })
        .map((ids) -> animeThemes);
  }

  @Update
  Single<Integer> update(AnimeTheme animeTheme);

  @Update
  Single<Integer> update(Collection<AnimeTheme> animeThemes);

  @Delete
  Single<Integer> delete(AnimeTheme animeTheme);

  @Query("DELETE FROM anime_theme")
  Single<Integer> deleteAll();

  @Query("SELECT * FROM anime_theme WHERE anime_theme_id = :id")
  LiveData<AnimeTheme> get(long id);

  @Query("SELECT * FROM anime_theme")
  LiveData<List<AnimeTheme>> getAll();

  @Query("SELECT * FROM anime_theme WHERE theme_id = :id")
  LiveData<List<AnimeTheme>> getAllByTheme(long id);

  @Query("SELECT * FROM anime_theme WHERE anime_id = :id")
  LiveData<List<AnimeTheme>> getAllByAnime(long id);

}
