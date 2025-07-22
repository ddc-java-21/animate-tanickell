package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.AnimeProducer;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface AnimeProducerDao {

  @Insert
  Single<Long> insert(AnimeProducer animeProducer);

  default Single<AnimeProducer> insertAndRefresh(AnimeProducer animeProducer) {
    return Single
        .just(animeProducer)
        .doOnSuccess((ap) -> ap.setCreated(Instant.now()))
        .flatMap(this::insert)
        .doOnSuccess(animeProducer::setId)
        .map((id) -> animeProducer);
  }

  @Insert
  Single<List<Long>> insert(List<AnimeProducer> animeProducers);

  default Single<List<AnimeProducer>> insertAndRefresh(List<AnimeProducer> animeProducers) {
    return Single
        .just(animeProducers)
        .doOnSuccess((aps) -> {
          aps.forEach((ap) -> {
            ap.setCreated(Instant.now());
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> iterator = ids.iterator();
          Iterator<AnimeProducer> animeProducerIterator = animeProducers.iterator();
          while (iterator.hasNext() && animeProducerIterator.hasNext()) {
            animeProducerIterator.next().setId(iterator.next());
          }
        })
        .map((ids) -> animeProducers);
  }

  @Update
  Single<Integer> update(AnimeProducer animeProducer);

  @Update
  Single<Integer> update(Collection<AnimeProducer> animeProducers);

  @Delete
  Single<Integer> delete(AnimeProducer animeProducer);

  @Query("DELETE FROM anime_producer")
  Single<Integer> deleteAll();

  @Query("SELECT * FROM anime_producer WHERE anime_producer_id = :id")
  LiveData<AnimeProducer> get(long id);

  @Query("SELECT * FROM anime_producer")
  LiveData<List<AnimeProducer>> getAll();

  @Query("SELECT * FROM anime_producer WHERE producer_id = :id")
  LiveData<List<AnimeProducer>> getAllByProducer(long id);

  @Query("SELECT * FROM anime_producer WHERE anime_id = :id")
  LiveData<List<AnimeProducer>> getAllByAnime(long id);

}
