package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.AnimeTag;
import edu.cnm.deepdive.animate.model.entity.Favorite;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface FavoriteDao {

  @Insert
  Single<Long> insert(Favorite favorite);

  default Single<Favorite> insertAndRefresh(Favorite favorite) {
    return Single
        .just(favorite)
        .doOnSuccess((f) -> f.setCreated(Instant.now()))
        .flatMap(this::insert)
        .doOnSuccess(favorite::setId)
        .map((id) -> favorite);
  }

  @Insert
  Single<List<Long>> insert(List<Favorite> favorites);

  default Single<List<Favorite>> insertAndRefresh(List<Favorite> favorites) {
    return Single
        .just(favorites)
        .doOnSuccess((fs) -> {
          fs.forEach((f) -> {
            f.setCreated(Instant.now());
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> iterator = ids.iterator();
          Iterator<Favorite> favoriteIterator = favorites.iterator();
          while (iterator.hasNext() && favoriteIterator.hasNext()) {
            favoriteIterator.next().setId(iterator.next());
          }
        })
        .map((ids) -> favorites);
  }

  @Update
  Single<Integer>  update(Favorite favorite);

  @Update
  Single<Integer> update(Collection<Favorite> favorites);

  @Delete
  Single<Integer>  delete(Favorite favorite);

  @Query("DELETE FROM favorite")
  Single<Integer> deleteAll();

  @Query("SELECT * FROM favorite WHERE favorite_id = :id")
  LiveData<Favorite> get(long id);

  @Query("SELECT * FROM favorite")
  LiveData<List<Favorite>> getAll();

  @Query("SELECT * FROM favorite WHERE user_id = :id")
  LiveData<List<Favorite>> getAllByUser(long id);

  @Query("SELECT * FROM favorite WHERE anime_id = :id")
  LiveData<List<Favorite>> getAllByAnime(long id);

}
