package com.example.user.stackoverflow.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * RetrofitClient.java
 * Class used to return a retrofit Client Instance;
 *
 * @author khoavo(03 / 10 / 2019)
 */

public class RetrofitClientInstance {
    private static final String BASE_URL = "https://api.stackexchange.com/2.2/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
