package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.Tag;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface TagDao {

  @Insert
  Single<Long> insert(Tag tag);

  default Single<Tag> insertAndRefresh(Tag tag) {
    return Single
        .just(tag)
        .doOnSuccess((t) -> {
          Instant now = Instant.now();
          t.setCreated(now);
          t.setModified(now);
        })
        .flatMap(this::insert)
        .doOnSuccess(tag::setId)
        .map((id) -> tag);
  }

  @Insert Single<List<Long>> insert(List<Tag> tags);

  default Single<List<Tag>> insertAndRefresh(List<Tag> tags) {
    return Single
        .just(tags)
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
          Iterator<Tag> tagIterator = tags.iterator();
          while (idIterator.hasNext() && tagIterator.hasNext()) {
            tagIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> tags);
  }

  @Update
  Single<Integer> update(Tag tag);

  default Single<Tag> updateAndRefresh(Tag tag) {
    return Single
        .just(tag)
        .doOnSuccess((t) -> {
          t.setModified(Instant.now());
        })
        .flatMap(this::update)
        .map((count) -> tag);
  }

  @Update
  Single<Integer> update(Collection<Tag> tags);

  default Single<Collection<Tag>> updateAndRefresh(Collection<Tag> tags) {
    return Single
        .just(tags)
        .doOnSuccess((ts) -> {
          ts.forEach((t) -> {
            t.setModified(Instant.now());
          });
        })
        .flatMap(this::update)
        .map((count) -> tags);
  }

  @Delete
  Single<Integer> delete(Tag tag);

  @Query("DELETE FROM tag") // truncate
  Single<Integer> deleteAll();

  // Get a single tag by id
  @Query("SELECT * FROM tag WHERE tag_id = :id")
  LiveData<Tag> get(long id);

  // Get all tags ordered by name
  @Query("SELECT * FROM tag ORDER BY name ASC")
  LiveData<List<Tag>> getAll();

  // Get all tags associated with an anime
  @Query("SELECT t.* FROM tag AS t "
      + "JOIN anime_tag AS at ON at.tag_id = t.tag_id "
      + "JOIN anime AS a ON a.anime_id = at.anime_id "
      + "WHERE a.anime_id = :animeId "
      + "ORDER BY t.name ASC")
  LiveData<List<Tag>> getByAnimeId(long animeId);

  // Get all tags associated with a user
  @Query("SELECT t.* FROM tag as t "
      + "JOIN anime_tag AS at ON at.tag_id = t.tag_id "
      + "JOIN user AS u ON u.user_id = at.user_id "
      + "WHERE u.user_id = :userId "
      + "ORDER BY t.name ASC")
  LiveData<List<Tag>> getByUserId(long userId);

  // Get "favorites tags"
  @Query("SELECT t.* FROM tag as t "
      + "JOIN anime_tag AS at ON at.tag_id = t.tag_id "
      + "JOIN user AS u ON u.user_id = at.user_id "
      + "JOIN anime AS a ON a.anime_id = at.anime_id "
      + "WHERE u.user_id = :userId AND a.anime_id = :animeId "
      + "ORDER BY t.name ASC")
  LiveData<List<Tag>> getTagsByUserAndAnime(long userId, long animeId);

}
