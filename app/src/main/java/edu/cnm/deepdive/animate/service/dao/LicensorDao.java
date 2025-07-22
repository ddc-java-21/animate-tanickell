package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.Licensor;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface LicensorDao {

  @Insert
  Single<Long> insert(Licensor licensor);

  default Single<Licensor> insertAndRefresh(Licensor licensor) {
    return Single
        .just(licensor)
        .doOnSuccess((l) -> {
          Instant now = Instant.now();
          l.setCreated(now);
          l.setModified(now);
        })
        .flatMap(this::insert)
        .doOnSuccess(licensor::setId)
        .map((id) -> licensor);
  }

  @Insert Single<List<Long>> insert(List<Licensor> licensors);

  default Single<List<Licensor>> insertAndRefresh(List<Licensor> licensors) {
    return Single
        .just(licensors)
        .doOnSuccess((ls) -> {
          Instant now = Instant.now();
          ls.forEach(l -> {
            l.setCreated(now);
            l.setModified(now);
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Licensor> licensorIterator = licensors.iterator();
          while (idIterator.hasNext() && licensorIterator.hasNext()) {
            licensorIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> licensors);
  }

  @Update
  Single<Integer> update(Licensor licensor);

  default Single<Licensor> updateAndRefresh(Licensor licensor) {
    return Single
        .just(licensor)
        .doOnSuccess((l) -> {
          l.setModified(Instant.now());
        })
        .flatMap(this::update)
        .map((count) -> licensor);
  }

  @Update
  Single<Integer> update(Collection<Licensor> licensors);

  default Single<Collection<Licensor>> updateAndRefresh(Collection<Licensor> licensors) {
    return Single
        .just(licensors)
        .doOnSuccess((ls) -> {
          ls.forEach((l) -> {
            l.setModified(Instant.now());
          });
        })
        .flatMap(this::update)
        .map((count) -> licensors);
  }

  @Delete
  Single<Integer> delete(Licensor licensor);

  @Query("DELETE FROM licensor") // truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM licensor WHERE licensor_id = :id")
  LiveData<Licensor> get(long id);

  @Query("SELECT * FROM licensor ORDER BY name ASC")
  LiveData<List<Licensor>> getAll();

  // Get all licensors associated with an anime
  @Query("SELECT l.* FROM licensor AS l "
      + "JOIN anime_licensor AS al ON al.licensor_id = l.licensor_id "
      + "JOIN anime AS a ON a.anime_id = al.anime_id "
      + "WHERE a.anime_id = :animeId "
      + "ORDER BY l.name ASC")
  LiveData<List<Licensor>> getLicensorByAnimeOrderByNameAsc(long animeId);

}
