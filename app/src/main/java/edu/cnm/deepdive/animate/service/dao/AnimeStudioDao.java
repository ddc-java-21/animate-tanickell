package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.AnimeStudio;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface AnimeStudioDao {

  @Insert
  Single<Long> insert(AnimeStudio animeStudio);

  default Single<AnimeStudio> insertAndRefresh(AnimeStudio animeStudio) {
    return Single
        .just(animeStudio)
        .doOnSuccess((ast) -> ast.setCreated(Instant.now()))
        .flatMap(this::insert)
        .doOnSuccess(animeStudio::setId)
        .map((id) -> animeStudio);
  }

  @Insert
  Single<List<Long>> insert(List<AnimeStudio> animeStudios);

  default Single<List<AnimeStudio>> insertAndRefresh(List<AnimeStudio> animeStudios) {
    return Single
        .just(animeStudios)
        .doOnSuccess((asts) -> {
          asts.forEach((ast) -> {
            ast.setCreated(Instant.now());
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> iterator = ids.iterator();
          Iterator<AnimeStudio> animeStudioIterator = animeStudios.iterator();
          while (iterator.hasNext() && animeStudioIterator.hasNext()) {
            animeStudioIterator.next().setId(iterator.next());
          }
        })
        .map((ids) -> animeStudios);
  }

  @Update
  Single<Integer> update(AnimeStudio animeStudio);

  @Update
  Single<Integer> update(Collection<AnimeStudio> animeStudios);

  @Delete
  Single<Integer> delete(AnimeStudio animeStudio);

  @Query("DELETE FROM anime_studio")
  Single<Integer> deleteAll();

  @Query("SELECT * FROM anime_studio WHERE anime_studio_id = :id")
  LiveData<AnimeStudio> get(long id);

  @Query("SELECT * FROM anime_studio")
  LiveData<List<AnimeStudio>> getAll();

  @Query("SELECT * FROM anime_studio WHERE studio_id = :id")
  LiveData<List<AnimeStudio>> getAllByStudio(long id);

  @Query("SELECT * FROM anime_studio WHERE anime_id = :id")
  LiveData<List<AnimeStudio>> getAllByAnime(long id);

}
