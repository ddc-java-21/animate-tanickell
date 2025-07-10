package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.Anime;
import edu.cnm.deepdive.animate.model.entity.AnimeTag;
import edu.cnm.deepdive.animate.model.entity.Tag;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface AnimeTagDao {

  @Insert
  Single<Long> insert(AnimeTag animeTag);

  default Single<AnimeTag> insertAndRefresh(AnimeTag animeTag) {
    return Single
        .just(animeTag)
        .doOnSuccess((at) -> at.setCreated(Instant.now()))
        .flatMap(this::insert)
        .doOnSuccess(animeTag::setId)
        .map((id) -> animeTag);
  }

  @Insert Single<List<Long>> insert(List<AnimeTag> animeTags);

  default Single<List<AnimeTag>> insertAndRefresh(List<AnimeTag> animeTags) {
    return Single
        .just(animeTags)
        .doOnSuccess((ats) -> {
          ats.forEach(at -> {
            at.setCreated(Instant.now());
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<AnimeTag> animeTagIterator = animeTags.iterator();
          while (idIterator.hasNext() && animeTagIterator.hasNext()) {
            animeTagIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> animeTags);
  }

  @Update
  Single<Integer> update(AnimeTag animeTag);

  @Update
  Single<Integer> update(Collection<AnimeTag> animeTags);

  @Delete
  Single<Integer> delete(AnimeTag animeTag);

  @Query("DELETE FROM anime_tag") // truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM anime_tag WHERE anime_tag_id = :id") // most queries will be LiveData, sometimes reactivex (FindByID in Spring repository)
  LiveData<AnimeTag> get(long id);

  @Query("SELECT * FROM anime_tag")
  LiveData<List<AnimeTag>> getAll();

  @Query("SELECT * FROM anime_tag WHERE user_id = :id")
  LiveData<List<AnimeTag>> getAllByUser(long id);

  @Query("SELECT * FROM anime_tag WHERE anime_id = :id")
  LiveData<List<AnimeTag>> getAllByAnime(long id);

  @Query("SELECT * FROM anime_tag WHERE tag_id = :id")
  LiveData<List<AnimeTag>> getAllByTag(long id);

}
