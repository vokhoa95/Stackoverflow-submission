package com.example.user.stackoverflow.util;

import com.example.user.stackoverflow.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackService {
    // Use for SERVER TEST
    @GET("/users")
    Call<String> getAllUsersJSON(@Query("page") int page, @Query("pagesize") int pageSize, @Query("site") String site);

    // Use for SERVER TEST
    @GET("/users/{userid}/reputation-history")
    Call<String> getAllReputationJSON(@Path(value = "userid", encoded = true) String userId,@Query("page") int page, @Query("pagesize") int pageSize, @Query("site") String site);
}
