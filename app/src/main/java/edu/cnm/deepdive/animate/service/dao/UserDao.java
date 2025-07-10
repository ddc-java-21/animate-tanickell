package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.User;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.List;

@Dao
public interface UserDao {

  @Insert
  Single<Long> insert(User user); // When should we use user.setId(...) to update the object in memory // single

  default Single<User> insertAndRefresh(User user) {
    return Single
        .just(user)
        .doOnSuccess((u) -> {
          Instant now = Instant.now();
          u.setCreated(now);
          u.setModified(now);
        })
        .flatMap(this::insert)
        .doOnSuccess(user::setId)
        .map((id) -> user);
  }

  @Update
  Single<Integer> update(User user); // when Single<Integer>, returns number of records overall that were affected (modified) by this update // no update is performed if info is the same

  default Single<User> updateAndRefresh(User user) {
    return Single
        .just(user)
        .doOnSuccess((u) -> u.setModified(Instant.now()))
        .flatMap(this::update)
        .map((count) -> user);
  }

  @Delete
  Single<Integer> delete(User user);

  @Query("SELECT * FROM user WHERE user_id = :userId")
  LiveData<User> select(long userId);

  @Query("SELECT * FROM user WHERE oauth_key = :oauthKey")
  Maybe<User> select(String oauthKey);

  @Query("SELECT * FROM user ORDER BY display_name ASC") // ASC or DESC; if blank, ASC assumed (default)
  LiveData<List<User>> select();

}
