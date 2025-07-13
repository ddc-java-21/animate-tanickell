package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.Title;
import edu.cnm.deepdive.animate.model.entity.Tag;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface TitleDao {

  @Insert
  Single<Long> insert(Title title);

  default Single<Title> insertAndRefresh(Title title) {
    return Single
        .just(title)
        .doOnSuccess((t) -> {
          Instant now = Instant.now();
          t.setCreated(now);
          t.setModified(now);
        })
        .flatMap(this::insert)
        .doOnSuccess(title::setId)
        .map((id) -> title);
  }

  @Insert Single<List<Long>> insert(List<Title> titles);

  default Single<List<Title>> insertAndRefresh(List<Title> titles) {
    return Single
        .just(titles)
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
          Iterator<Title> titleIterator = titles.iterator();
          while (idIterator.hasNext() && titleIterator.hasNext()) {
            titleIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> titles);
  }

  @Update
  Single<Integer> update(Title title);

  default Single<Title> updateAndRefresh(Title title) {
    return Single
        .just(title)
        .doOnSuccess((t) -> {
          t.setModified(Instant.now());
        })
        .flatMap(this::update)
        .map((count) -> title);
  }

  @Update
  Single<Integer> update(Collection<Title> titles);

  default Single<Collection<Title>> updateAndRefresh(Collection<Title> titles) {
    return Single
        .just(titles)
        .doOnSuccess((ts) -> {
          ts.forEach((t) -> {
            t.setModified(Instant.now());
          });
        })
        .flatMap(this::update)
        .map((count) -> titles);
  }

  @Delete
  Single<Integer> delete(Title title);

  @Query("DELETE FROM title") // truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM title WHERE title_id = :id")
  LiveData<Title> get(long id);

  @Query("SELECT * FROM title ORDER BY name ASC")
  LiveData<List<Title>> getAll();

  // Get all titles associated with an anime
  @Query("SELECT t.* FROM title AS t "
      + "JOIN anime AS a ON a.anime_id = t.anime_id "
      + "WHERE a.anime_id = :animeId "
      + "ORDER BY t.name ASC")
  LiveData<List<Title>> getTitleByAnimeOrderByNameAsc(long animeId);

}
