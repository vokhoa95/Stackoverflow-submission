package com.example.user.stackoverflow.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user.stackoverflow.model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDAO {
    @Insert(onConflict = REPLACE)
    void insertUser(User user);

    @Update(onConflict = REPLACE)
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users")
    Flowable<List<User>> selectAllUser();

    @Query("SELECT * FROM users WHERE user_Id = :userId")
    Single<User> selectUserByUserId(String userId);
}
