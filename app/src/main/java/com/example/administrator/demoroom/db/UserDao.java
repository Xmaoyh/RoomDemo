package com.example.administrator.demoroom.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * 用户dao
 *
 * @author MaoYiHan
 * @date 2018/8/1
 */
@Dao
public interface UserDao {
    @Insert
    void insert(UserEntity userEntity);

    @Insert
    void insertAll(List<UserEntity> userEntities);

    @Delete
    void delete(UserEntity userEntity);

    @Delete
    void deleteAll(List<UserEntity> userEntities);

    @Update()
    void update(UserEntity userEntity);

    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getAll();

    @Query("SELECT * FROM UserEntity")
    LiveData<List<UserEntity>> getAllByLivedata();

    @Query("SELECT * FROM UserEntity WHERE uid  = :uid")
    Flowable<UserEntity> getByUid(int uid);

    @Query("SELECT * FROM UserEntity WHERE name like '%D'")
    Single<List<UserEntity>> getAllLikeD();

    @RawQuery
    Single<List<UserEntity>> getByRaw(SupportSQLiteQuery query);

}
