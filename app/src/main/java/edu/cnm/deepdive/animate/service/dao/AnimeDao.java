package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.Anime;
import edu.cnm.deepdive.animate.model.Anime.MediaType;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface AnimeDao {

  @Insert
  Single<Long> insert(Anime anime); // int or long doesn't allow for running on the same thread --> machinery to pass along Long primary key

//  List<Long> insert(List<Anime> animes); // can't invoke from the UI thread
//  void insert(List<Anime> animes);
//  Single<List<Long>> insert(List<Anime> animes);
//  Completable insert(List<Anime> animes);

//  @Insert
//  Single<List<Long>> insert(Anime[] animes);
//

  default Single<Anime> insertAndRefresh(Anime anime) {
    return insert(anime)
        .doOnSuccess((id) -> anime.setId(id)) // consumer of the value
        .map((id) -> anime);
  }

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(List<Anime> animes);  // can pass an array of anime instead of a list of anime

  default Single<List<Anime>> insertAndRefresh(List<Anime> animes) { // pass downstream some subset of this list (animes)
    return insert(animes)
        .map((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Anime> animeIterator = animes.iterator();
          while (idIterator.hasNext() && animeIterator.hasNext()) {
            animeIterator.next().setId(idIterator.next());
          }
          animes.removeIf((anime) -> anime.getId() <= 0);
          return animes;
        });
  }

//  @Insert
//  Single<List<Long>> insert(Anime... animes);

  // can do the same thing for this one

  // if we need to do any updates (e.g., I need to update a note that's already been saved to the db)


//  int update(Anime anime); // returns number of records modified directly or indirectly as a result of this update (by cascade)
//  void update(Anime anime);
//  Single<Integer> update(Anime anime);
//  Completable update(Anime anime);

  @Insert
  Single<List<Long>> insert(Anime... animes);

  @Update
  Single<Integer> update(Anime anime);

  @Update
  Single<Integer> update(Collection<Anime> animes);

  @Delete
  Single<Integer> delete(Anime anime);

  @Query("DELETE FROM anime")// truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM anime WHERE anime_id = :id") // most queries will be LiveData, sometimes reactivex (FindByID in Spring repository)
  LiveData<Anime> get(long id); // returning a list here makes no sense

  @Query("SELECT * FROM anime WHERE media_type = :mediaType ORDER BY date_created")
  LiveData<List<Anime>> get(MediaType mediaType);

}
