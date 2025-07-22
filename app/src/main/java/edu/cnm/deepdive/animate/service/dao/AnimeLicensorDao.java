package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.AnimeLicensor;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface AnimeLicensorDao {

  @Insert
  Single<Long> insert(AnimeLicensor animeLicensor);

  default Single<AnimeLicensor> insertAndRefresh(AnimeLicensor animeLicensor) {
    return Single
        .just(animeLicensor)
        .doOnSuccess((al) -> al.setCreated(Instant.now()))
        .flatMap(this::insert)
        .doOnSuccess(animeLicensor::setId)
        .map((id) -> animeLicensor);
  }

  @Insert
  Single<List<Long>> insert(List<AnimeLicensor> animeLicensors);

  default Single<List<AnimeLicensor>> insertAndRefresh(List<AnimeLicensor> animeLicensors) {
    return Single
        .just(animeLicensors)
        .doOnSuccess((als) -> {
          als.forEach((al) -> {
            al.setCreated(Instant.now());
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> iterator = ids.iterator();
          Iterator<AnimeLicensor> animeLicensorIterator = animeLicensors.iterator();
          while (iterator.hasNext() && animeLicensorIterator.hasNext()) {
            animeLicensorIterator.next().setId(iterator.next());
          }
        })
        .map((ids) -> animeLicensors);
  }

  @Update
  Single<Integer> update(AnimeLicensor animeLicensor);

  @Update
  Single<Integer> update(Collection<AnimeLicensor> animeLicensors);

  @Delete
  Single<Integer> delete(AnimeLicensor animeLicensor);

  @Query("DELETE FROM anime_licensor")
  Single<Integer> deleteAll();

  @Query("SELECT * FROM anime_licensor WHERE anime_licensor_id = :id")
  LiveData<AnimeLicensor> get(long id);

  @Query("SELECT * FROM anime_licensor")
  LiveData<List<AnimeLicensor>> getAll();

  @Query("SELECT * FROM anime_licensor WHERE licensor_id = :id")
  LiveData<List<AnimeLicensor>> getAllByLicensor(long id);

  @Query("SELECT * FROM anime_licensor WHERE anime_id = :id")
  LiveData<List<AnimeLicensor>> getAllByAnime(long id);

}
