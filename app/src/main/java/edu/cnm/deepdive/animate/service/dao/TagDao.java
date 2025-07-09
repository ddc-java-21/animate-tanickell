package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import edu.cnm.deepdive.animate.model.entity.Tag;
import java.util.List;

@Dao
public interface TagDao {

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
