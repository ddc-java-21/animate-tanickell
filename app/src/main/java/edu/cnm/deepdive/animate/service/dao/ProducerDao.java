package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.Producer;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface ProducerDao {

  @Insert
  Single<Long> insert(Producer producer);

  default Single<Producer> insertAndRefresh(Producer producer) {
    return Single
        .just(producer)
        .doOnSuccess((p) -> {
          Instant now = Instant.now();
          p.setCreated(now);
          p.setModified(now);
        })
        .flatMap(this::insert)
        .doOnSuccess(producer::setId)
        .map((id) -> producer);
  }

  @Insert Single<List<Long>> insert(List<Producer> producers);

  default Single<List<Producer>> insertAndRefresh(List<Producer> producers) {
    return Single
        .just(producers)
        .doOnSuccess((ps) -> {
          Instant now = Instant.now();
          ps.forEach(p -> {
            p.setCreated(now);
            p.setModified(now);
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Producer> producerIterator = producers.iterator();
          while (idIterator.hasNext() && producerIterator.hasNext()) {
            producerIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> producers);
  }

  @Update
  Single<Integer> update(Producer producer);

  default Single<Producer> updateAndRefresh(Producer producer) {
    return Single
        .just(producer)
        .doOnSuccess((p) -> {
          p.setModified(Instant.now());
        })
        .flatMap(this::update)
        .map((count) -> producer);
  }

  @Update
  Single<Integer> update(Collection<Producer> producers);

  default Single<Collection<Producer>> updateAndRefresh(Collection<Producer> producers) {
    return Single
        .just(producers)
        .doOnSuccess((ps) -> {
          ps.forEach((p) -> {
            p.setModified(Instant.now());
          });
        })
        .flatMap(this::update)
        .map((count) -> producers);
  }

  @Delete
  Single<Integer> delete(Producer producer);

  @Query("DELETE FROM producer") // truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM producer WHERE producer_id = :id")
  LiveData<Producer> get(long id);

  @Query("SELECT * FROM producer ORDER BY name ASC")
  LiveData<List<Producer>> getAll();

  // Get all producers associated with an anime
  @Query("SELECT p.* FROM producer AS p "
      + "JOIN anime_producer AS ap ON ap.producer_id = p.producer_id "
      + "JOIN anime AS a ON a.anime_id = ap.anime_id "
      + "WHERE a.anime_id = :animeId "
      + "ORDER BY p.name ASC")
  LiveData<List<Producer>> getProducerByAnimeOrderByNameAsc(long animeId);

}
