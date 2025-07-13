package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.Studio;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface StudioDao {

  @Insert
  Single<Long> insert(Studio studio);

  default Single<Studio> insertAndRefresh(Studio studio) {
    return Single
        .just(studio)
        .doOnSuccess((s) -> {
          Instant now = Instant.now();
          s.setCreated(now);
          s.setModified(now);
        })
        .flatMap(this::insert)
        .doOnSuccess(studio::setId)
        .map((id) -> studio);
  }

  @Insert Single<List<Long>> insert(List<Studio> studios);

  default Single<List<Studio>> insertAndRefresh(List<Studio> studios) {
    return Single
        .just(studios)
        .doOnSuccess((ss) -> {
          Instant now = Instant.now();
          ss.forEach(s -> {
            s.setCreated(now);
            s.setModified(now);
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Studio> studioIterator = studios.iterator();
          while (idIterator.hasNext() && studioIterator.hasNext()) {
            studioIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> studios);
  }

  @Update
  Single<Integer> update(Studio studio);

  default Single<Studio> updateAndRefresh(Studio studio) {
    return Single
        .just(studio)
        .doOnSuccess((s) -> {
          s.setModified(Instant.now());
        })
        .flatMap(this::update)
        .map((count) -> studio);
  }

  @Update
  Single<Integer> update(Collection<Studio> studios);

  default Single<Collection<Studio>> updateAndRefresh(Collection<Studio> studios) {
    return Single
        .just(studios)
        .doOnSuccess((ss) -> {
          ss.forEach((s) -> {
            s.setModified(Instant.now());
          });
        })
        .flatMap(this::update)
        .map((count) -> studios);
  }

  @Delete
  Single<Integer> delete(Studio studio);

  @Query("DELETE FROM studio") // truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM studio WHERE studio_id = :id")
  LiveData<Studio> get(long id);

  @Query("SELECT * FROM studio ORDER BY name ASC")
  LiveData<List<Studio>> getAll();

  // Get all studios associated with an anime
  @Query("SELECT s.* FROM studio AS s "
      + "JOIN anime AS a ON a.anime_id = s.anime_id "
      + "WHERE a.anime_id = :animeId "
      + "ORDER BY s.name ASC")
  LiveData<List<Studio>> getStudioByAnimeOrderByNameAsc(long animeId);

}
