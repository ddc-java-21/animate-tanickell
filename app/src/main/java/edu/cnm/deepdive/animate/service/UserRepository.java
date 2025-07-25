package edu.cnm.deepdive.animate.service;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.animate.model.entity.User;
import edu.cnm.deepdive.animate.service.dao.UserDao;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

  private final GoogleSignInService signInService;
  private final UserDao userDao;
  private final Scheduler scheduler;

  @Inject
  UserRepository(GoogleSignInService signInService, UserDao userDao) { // package-private: don't want it to be part of API, won't be included in Javadocs (no documentation for package-private)
    this.signInService = signInService;
    this.userDao = userDao;
    scheduler = Schedulers.io(); // utility class that returns a scheduler
  }

  /** @noinspection DataFlowIssue*/
  public Single<User> getCurrentUser() {
    return signInService
        .refresh()
        .observeOn(scheduler)
        .flatMap((account) -> {
          String oauthKey = account.getId();
          return userDao
              .select(oauthKey)
              .switchIfEmpty(
                  Single.just(new User())
                      .doOnSuccess((user) -> {
                        user.setOauthKey(oauthKey);
                        user.setDisplayName(account.getDisplayName());
                      })
                      .flatMap(userDao::insertAndRefresh)
              );
        });
  }

  public Single<User> save(User user) {
    return (
        (user.getId() == 0)
        ? userDao.insertAndRefresh(user)
        : userDao.updateAndRefresh(user)
    )
        .subscribeOn(scheduler);
  }

  public LiveData<User> get(long userId) { // delegate this to the dao
    return userDao.select(userId);
  }

  public Completable remove(User user) {
    return userDao
        .delete(user)
        .ignoreElement()
        .subscribeOn(scheduler); // attach to thread
  }

  public LiveData<List<User>> getAll() {
    return userDao.select();
  }



}
