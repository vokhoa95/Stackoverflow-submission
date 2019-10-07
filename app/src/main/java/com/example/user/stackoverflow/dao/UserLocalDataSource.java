package com.example.user.stackoverflow.dao;

import com.example.user.stackoverflow.UserDataSource;
import com.example.user.stackoverflow.model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class UserLocalDataSource implements UserDataSource {
    private static UserLocalDataSource sInstance;

    public static UserLocalDataSource getInstance(UserDAO userDAO) {
        if (sInstance == null) {
            sInstance = new UserLocalDataSource(userDAO);
        }
        return sInstance;
    }

    private UserDAO mUserDAO;

    public UserLocalDataSource(UserDAO userDAO) {
        mUserDAO = userDAO;
    }

    @Override
    public Flowable<List<User>> getAllUser() {
        return mUserDAO.selectAllUser();
    }

    @Override
    public Single<User> getUser(String userId) {
        return mUserDAO.selectUserByUserId(userId);
    }

    @Override
    public void insertUser(User user) {
        mUserDAO.insertUser(user);
    }

    @Override
    public void deleteUser(User user) {
        mUserDAO.deleteUser(user);
    }

    @Override
    public void updateUser(User user) {
        mUserDAO.updateUser(user);
    }
}
