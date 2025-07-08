package edu.cnm.deepdive.animate.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.animate.model.Animate;
import edu.cnm.deepdive.animate.model.Animate.MediaType;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Dao
public interface AnimateDao {

  @Insert
  Single<Long> insert(Animate animate); // int or long doesn't allow for running on the same thread --> machinery to pass along Long primary key

//  List<Long> insert(List<Animate> animates); // can't invoke from the UI thread
//  void insert(List<Animate> animates);
//  Single<List<Long>> insert(List<Animate> animates);
//  Completable insert(List<Animate> animates);

//  @Insert
//  Single<List<Long>> insert(Animate[] animates);
//

  default Single<Animate> insertAndRefresh(Animate animate) {
    return insert(animate)
        .doOnSuccess((id) -> animate.setId(id)) // consumer of the value
        .map((id) -> animate);
  }

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(List<Animate> animates);  // can pass an array of animate instead of a list of animate

  default Single<List<Animate>> insertAndRefresh(List<Animate> animates) { // pass downstream some subset of this list (animates)
    return insert(animates)
        .map((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Animate> animateIterator = animates.iterator();
          while (idIterator.hasNext() && animateIterator.hasNext()) {
            animateIterator.next().setId(idIterator.next());
          }
          animates.removeIf((animate) -> animate.getId() <= 0);
          return animates;
        });
  }

//  @Insert
//  Single<List<Long>> insert(Animate... animates);

  // can do the same thing for this one

  // if we need to do any updates (e.g., I need to update a note that's already been saved to the db)


//  int update(Animate animate); // returns number of records modified directly or indirectly as a result of this update (by cascade)
//  void update(Animate animate);
//  Single<Integer> update(Animate animate);
//  Completable update(Animate animate);

  @Insert
  Single<List<Long>> insert(Animate... animates);

  @Update
  Single<Integer> update(Animate animate);

  @Update
  Single<Integer> update(Collection<Animate> animates);

  @Delete
  Single<Integer> delete(Animate animate);

  @Query("DELETE FROM animate")// truncate
  Single<Integer> deleteAll();

  @Query("SELECT * FROM animate WHERE animate_id = :id") // most queries will be LiveData, sometimes reactivex (FindByID in Spring repository)
  LiveData<Animate> get(long id); // returning a list here makes no sense

  @Query("SELECT * FROM animate WHERE media_type = :mediaType ORDER BY date_created")
  LiveData<List<Animate>> get(MediaType mediaType);

}
