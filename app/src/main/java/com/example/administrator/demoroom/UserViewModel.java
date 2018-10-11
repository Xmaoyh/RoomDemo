package com.example.administrator.demoroom;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.administrator.demoroom.db.DataRepository;
import com.example.administrator.demoroom.db.UserEntity;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author MaoYiHan
 * @date 2018/8/1
 */
public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<List<UserEntity>> mUserEntityList;
    private DataRepository mDataRepository;

    private LiveData<List<UserEntity>> mObservableUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        if (mUserEntityList == null) {
            mUserEntityList = new MutableLiveData<>();
        }
        mDataRepository = DataRepository.getInstance();
    }

    public void getUserByLiveData() {
        mObservableUsers = mDataRepository.getAllByLiveData();
    }

    public LiveData<List<UserEntity>> getObservableUsers() {
        return mObservableUsers;
    }

    public void getUserFromDb() {
        MyApplication.getInstance().getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mUserEntityList.postValue(mDataRepository.getAll());
            }
        });
    }

    public void insertUser(List<UserEntity> userEntities) {
        MyApplication.getInstance().getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDataRepository.insertAll(userEntities);
                getUserFromDb();
            }
        });
    }

    public void deleteUser(UserEntity userEntity) {
        MyApplication.getInstance().getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDataRepository.delete(userEntity);
            }
        });
    }


    @SuppressLint("CheckResult")
    public void deleteMao() {
        mDataRepository.getAllLikeMao().
                subscribeOn(Schedulers.io()).
                observeOn(Schedulers.io()).
                subscribe(userEntities -> {
                    mDataRepository.deleteAll(userEntities);
                    mUserEntityList.postValue(mDataRepository.getAll());
                });
    }

    public void updateUser(UserEntity userEntity) {
        userEntity.setName("更新啦");
        userEntity.setAddress("更新啦yeyeye");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                mDataRepository.update(userEntity);
                e.onNext("success");
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                mUserEntityList.postValue(mDataRepository.getAll());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 一定要以userEntity -> Logger.t("id").d(userEntity)或者new Consumer<UserEntity>() {
     *
     * @Override public void accept(UserEntity userEntity) throws Exception {
     * Logger.t("id").d(userEntity);
     * }
     * }这种Consumer获取
     * 用new FlowableSubscriber<UserEntity>() {}只会回调onSubscribe方法
     * 貌似Maybe、Single、Flowable都支持Consumer
     */
    @SuppressLint("CheckResult")
    public void getUserById(int id) {
        mDataRepository.getByUid(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userEntity -> Logger.t("id").d(userEntity));
    }

    public MutableLiveData<List<UserEntity>> getUserEntityList() {
        return mUserEntityList;
    }
}
