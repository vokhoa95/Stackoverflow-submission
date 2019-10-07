package com.example.user.stackoverflow;

import com.example.user.stackoverflow.model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


public interface UserDataSource {
    Flowable<List<User>> getAllUser();

    Single<User> getUser(String userId);

    void insertUser(User user);

    void deleteUser(User user);

    void updateUser(User user);
}