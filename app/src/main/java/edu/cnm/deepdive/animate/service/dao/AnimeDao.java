package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.entity.Anime;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface AnimeDao {

  @Insert
  Single<Long> insert(Anime anime); // int or long doesn't allow for running on the same thread --> machinery to pass along Long primary key

  default Single<Anime> insertAndRefresh(Anime anime) {
//    return insert(anime)
//        .doOnSuccess((id) -> anime.setId(id)) // consumer of the value
//        .map((id) -> anime);
    return Single
        .just(anime)
        .doOnSuccess((a) -> {
          Instant now = Instant.now();
          a.setCreated(now);
          a.setModified(now);
        })
        .flatMap(this::insert)
        .doOnSuccess((id) -> anime.setId(id))
        .map((id) -> anime);
  }

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(List<Anime> animes);  // can pass an array of anime instead of a list of anime

  default Single<List<Anime>> insertAndRefresh(List<Anime> animes) { // pass downstream some subset of this list (animes)
//    return insert(animes)
//        .map((ids) -> {
//          Iterator<Long> idIterator = ids.iterator();
//          Iterator<Anime> animeIterator = animes.iterator();
//          while (idIterator.hasNext() && animeIterator.hasNext()) {
//            animeIterator.next().setId(idIterator.next());
//          }
//          animes.removeIf((anime) -> anime.getId() <= 0);
//          return animes;
//        });
    return Single
        .just(animes)
        .doOnSuccess((as) -> {
          Instant now = Instant.now();
          as.forEach((a) -> {
            a.setCreated(now);
            a.setModified(now);
          });
        })
        .flatMap(this::insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Anime> animeIterator = animes.iterator();
          while (idIterator.hasNext() && animeIterator.hasNext()) {
            animeIterator.next().setId(idIterator.next());
          }
          animes.removeIf((anime) -> anime.getId() <= 0);
        })
        .map((ids) -> animes);
  }

  @Insert
  Single<List<Long>> insert(Anime... animes);

  @Update
  Single<Integer> update(Anime anime);

  default Single<Anime> updateAndRefresh(Anime anime) {
    return Single
        .just(anime)
        .doOnSuccess((a) -> {
          a.setModified(Instant.now());
        })
        .flatMap(this::update)
        .map((count) -> anime);
  }

  @Update
  Single<Integer> update(Collection<Anime> animes);

  default Single<Collection<Anime>> updateAndRefresh(Collection<Anime> animes) {
    return Single
        .just(animes)
        .doOnSuccess((as) -> {
          as.forEach((a) -> {
            a.setModified(Instant.now());
          });
        })
        .flatMap(this::update)
        .map((count) -> animes);
  }

  @Delete
  Single<Integer> delete(Anime anime);

  @Query("DELETE FROM anime") // truncate
  Single<Integer> deleteAll();


  @Query("SELECT * FROM anime WHERE anime_id = :id") // most queries will be LiveData, sometimes reactivex (FindByID in Spring repository)
  LiveData<Anime> get(long id); // returning a list here makes no sense

  // Get animes associated with a certain tag id:
  @Query("SELECT a.* FROM anime AS a "
      + "JOIN anime_tag AS at ON at.anime_id = a.anime_id "
      + "JOIN tag AS t ON t.tag_id = at.tag_id "
      + "WHERE t.tag_id = :tagId "
      + "ORDER BY at.date_tagged DESC")
  LiveData<List<Anime>> getAnimeByTagOrderByDateTaggedDesc(long tagId);

  // Get animes associated with a certain user id via favorites (i.e., a user's favorites):
  @Query("SELECT a.* FROM anime AS a "
      + "JOIN favorite AS f ON f.anime_id = a.anime_id "
      + "JOIN user AS u ON u.user_id = f.user_id "
      + "WHERE u.user_id = :userId "
      + "ORDER BY f.date_favorited DESC")
  LiveData<List<Anime>> getAnimeByUserOrderByDateFavoritedDesc(long userId);

  @Query("SELECT * FROM anime WHERE genre = :genre ORDER BY release_date DESC")
  LiveData<List<Anime>> getAnimeByGenreOrderByReleaseDateDesc(String genre);

  @Query("SELECT * FROM anime WHERE genre = :genre ORDER BY score DESC")
  LiveData<List<Anime>> getAnimeByGenreOrderByScoreDesc(String genre);

  @Query ("SELECT * FROM anime ORDER BY release_date DESC")
  LiveData<List<Anime>> getAnimeOrderByReleaseDateDesc();

}
