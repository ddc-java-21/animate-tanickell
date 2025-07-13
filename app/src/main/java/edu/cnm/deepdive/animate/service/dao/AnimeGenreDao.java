package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.AnimeGenre;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface AnimeGenreDao {

  @Insert
  Single<Long> insert(AnimeGenre animeGenre);

  default Single<AnimeGenre> insertAndRefresh(AnimeGenre animeGenre) {
    return Single
        .just(animeGenre)
        .doOnSuccess((ag) -> ag.setCreated(Instant.now()))
        .flatMap(this::insert)
        .doOnSuccess(animeGenre::setId)
        .map((id) -> animeGenre);
  }

  @Insert
  Single<List<Long>> insert(List<AnimeGenre> animeGenres);

  default Single<List<AnimeGenre>> insertAndRefresh(List<AnimeGenre> animeGenres) {
    return Single
        .just(animeGenres)
        .doOnSuccess((ags) -> {
          ags.forEach((ag) -> {
            ag.setCreated(Instant.now());
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> iterator = ids.iterator();
          Iterator<AnimeGenre> animeGenreIterator = animeGenres.iterator();
          while (iterator.hasNext() && animeGenreIterator.hasNext()) {
            animeGenreIterator.next().setId(iterator.next());
          }
        })
        .map((ids) -> animeGenres);
  }

  @Update
  Single<Integer> update(AnimeGenre animeGenre);

  @Update
  Single<Integer> update(Collection<AnimeGenre> animeGenres);

  @Delete
  Single<Integer> delete(AnimeGenre animeGenre);

  @Query("DELETE FROM anime_genre")
  Single<Integer> deleteAll();

  @Query("SELECT * FROM anime_genre WHERE anime_genre_id = :id")
  LiveData<AnimeGenre> get(long id);

  @Query("SELECT * FROM anime_genre")
  LiveData<List<AnimeGenre>> getAll();

  @Query("SELECT * FROM anime_genre WHERE genre_id = :id")
  LiveData<List<AnimeGenre>> getAllByGenre(long id);

  @Query("SELECT * FROM anime_genre WHERE anime_id = :id")
  LiveData<List<AnimeGenre>> getAllByAnime(long id);

}
