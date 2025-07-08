package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.Apod;
import edu.cnm.deepdive.animate.model.Apod.MediaType;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface ApodDao {

  @Insert
  Single<Long> insert(Apod apod); // int or long doesn't allow for running on the same thread --> machinery to pass along Long primary key

//  List<Long> insert(List<Apod> apods); // can't invoke from the UI thread
//  void insert(List<Apod> apods);
//  Single<List<Long>> insert(List<Apod> apods);
//  Completable insert(List<Apod> apods);

//  @Insert
//  Single<List<Long>> insert(Apod[] apods);
//

  default Single<Apod> insertAndRefresh(Apod apod) {
    return insert(apod)
        .doOnSuccess((id) -> apod.setId(id)) // consumer of the value
        .map((id) -> apod);
  }

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(List<Apod> apods);  // can pass an array of apod instead of a list of apod

  default Single<List<Apod>> insertAndRefresh(List<Apod> apods) { // pass downstream some subset of this list (apods)
    return insert(apods)
        .map((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Apod> apodIterator = apods.iterator();
          while (idIterator.hasNext() && apodIterator.hasNext()) {
            apodIterator.next().setId(idIterator.next());
          }
          apods.removeIf((apod) -> apod.getId() <= 0);
          return apods;
        });
  }

//  @Insert
//  Single<List<Long>> insert(Apod... apods);

  // can do the same thing for this one

  // if we need to do any updates (e.g., I need to update a note that's already been saved to the db)


//  int update(Apod apod); // returns number of records modified directly or indirectly as a result of this update (by cascade)
//  void update(Apod apod);
//  Single<Integer> update(Apod apod);
//  Completable update(Apod apod);

  @Insert
  Single<List<Long>> insert(Apod... apods);

  @Update
  Single<Integer> update(Apod apod);

  @Update
  Single<Integer> update(Collection<Apod> apods);

  @Delete
  Single<Integer> delete(Apod apod);

  @Query("DELETE FROM apod")// truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM apod WHERE apod_id = :id") // most queries will be LiveData, sometimes reactivex (FindByID in Spring repository)
  LiveData<Apod> get(long id); // returning a list here makes no sense

  @Query("SELECT * FROM apod WHERE media_type = :mediaType ORDER BY date_created")
  LiveData<List<Apod>> get(MediaType mediaType);

}
