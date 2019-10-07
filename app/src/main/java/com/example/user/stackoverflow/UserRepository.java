package com.example.user.stackoverflow;

import com.example.user.stackoverflow.model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class UserRepository implements UserDataSource {
    private static UserRepository sInstance;

    public static UserRepository getInstance(UserDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new UserRepository(localDataSource);
        }
        return sInstance;
    }

    private UserDataSource mLocalDataSource;

    public UserRepository(UserDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    @Override
    public Flowable<List<User>> getAllUser() {
        return mLocalDataSource.getAllUser();
    }

    @Override
    public Single<User> getUser(String userId) {
        return mLocalDataSource.getUser(userId);
    }

    @Override
    public void insertUser(User user) {
        mLocalDataSource.insertUser(user);
    }

    @Override
    public void deleteUser(User user) {
        mLocalDataSource.deleteUser(user);
    }

    @Override
    public void updateUser(User user) {
        mLocalDataSource.updateUser(user);
    }
}
