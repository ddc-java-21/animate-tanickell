package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.Genre;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface GenreDao {

  @Insert
  Single<Long> insert(Genre genre);

  default Single<Genre> insertAndRefresh(Genre genre) {
    return Single
        .just(genre)
        .doOnSuccess((g) -> {
          Instant now = Instant.now();
          g.setCreated(now);
          g.setModified(now);
        })
        .flatMap(this::insert)
        .doOnSuccess(genre::setId)
        .map((id) -> genre);
  }

  @Insert Single<List<Long>> insert(List<Genre> genres);

  default Single<List<Genre>> insertAndRefresh(List<Genre> genres) {
    return Single
        .just(genres)
        .doOnSuccess((gs) -> {
          Instant now = Instant.now();
          gs.forEach(g -> {
            g.setCreated(now);
            g.setModified(now);
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Genre> genreIterator = genres.iterator();
          while (idIterator.hasNext() && genreIterator.hasNext()) {
            genreIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> genres);
  }

  @Update
  Single<Integer> update(Genre genre);

  default Single<Genre> updateAndRefresh(Genre genre) {
    return Single
        .just(genre)
        .doOnSuccess((g) -> {
          g.setModified(Instant.now());
        })
        .flatMap(this::update)
        .map((count) -> genre);
  }

  @Update
  Single<Integer> update(Collection<Genre> genres);

  default Single<Collection<Genre>> updateAndRefresh(Collection<Genre> genres) {
    return Single
        .just(genres)
        .doOnSuccess((gs) -> {
          gs.forEach((g) -> {
            g.setModified(Instant.now());
          });
        })
        .flatMap(this::update)
        .map((count) -> genres);
  }

  @Delete
  Single<Integer> delete(Genre genre);

  @Query("DELETE FROM genre") // truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM genre WHERE genre_id = :id")
  LiveData<Genre> get(long id);

  @Query("SELECT * FROM genre ORDER BY name ASC")
  LiveData<List<Genre>> getAll();

  // Get all genres associated with an anime
  @Query("SELECT g.* FROM genre AS g "
      + "JOIN anime_genre AS ag ON ag.genre_id = g.genre_id "
      + "JOIN anime AS a ON a.anime_id = ag.anime_id "
      + "WHERE a.anime_id = :animeId "
      + "ORDER BY g.name ASC")
  LiveData<List<Genre>> getGenreByAnimeOrderByNameAsc(long animeId);

}
