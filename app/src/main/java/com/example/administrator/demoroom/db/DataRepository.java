package com.example.administrator.demoroom.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteQuery;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * 数据源管理
 *
 * @author MaoYiHan
 * @date 2018/8/1
 */
public class DataRepository {
    private AppDatabase mAppDatabase;
//    private IApiAction mApiAction;

    private static class DataRepositoryHolder {
        public static DataRepository instance = new DataRepository();
    }

    private DataRepository() {
        mAppDatabase = AppDatabase.getInstance();
//        mApiAction = RetrofitUtil.getInstance().api();
    }

    public static DataRepository getInstance() {
        return DataRepositoryHolder.instance;
    }

    //-------------------------数据库----------------------//
    public void insert(UserEntity userEntity) {
        mAppDatabase.userDao().insert(userEntity);
    }

    public void insertAll(List<UserEntity> userEntities) {
        mAppDatabase.userDao().insertAll(userEntities);
    }

    public void delete(UserEntity userEntity) {
        mAppDatabase.userDao().delete(userEntity);
    }

    public void deleteAll(List<UserEntity> userEntities) {
        mAppDatabase.userDao().deleteAll(userEntities);
    }

    public void update(UserEntity userEntity) {
        mAppDatabase.userDao().update(userEntity);
    }

    public List<UserEntity> getAll() {
        return mAppDatabase.userDao().getAll();
    }

    public LiveData<List<UserEntity>> getAllByLiveData() {
        return mAppDatabase.userDao().getAllByLivedata();
    }

    public Flowable<UserEntity> getByUid(int uid) {
        return mAppDatabase.userDao().getByUid(uid);
    }

    public Single<List<UserEntity>> getAllLikeMao() {
        return mAppDatabase.userDao().getAllLikeD();
    }

    public Single<List<UserEntity>> getByRaw(SupportSQLiteQuery query) {
        return mAppDatabase.userDao().getByRaw(query);
    }

    //-------------------------网络----------------------//
/*    public Observable<NewsBean> getNews() {
        return mApiAction.getNews();
    }

    public Observable<NewsDetailBean> getNewsDetail(String nid) {
        return mApiAction.getNewsDetail(nid);
    }

    public Observable<ThreadsBean> getThreads() {
        return mApiAction.getThreads();
    }

    public Observable<ThreadDetailBean> getThreadDetail(String tid) {
        return mApiAction.getThreadDetail(tid);
    }

    public Observable<PhotoArticleBean> getPhotoArticle(String category, String time) {
        return mApiAction.getPhotoArticle(category, time);
    }*/
}
