package com.example.user.stackoverflow.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getJSONClient(String baseUrl, OkHttpClient httpClient) {
        if (retrofit == null) {
            // if retrofit client passed null check
            if (httpClient != null) {
                // if there is a passed OkHttpClient
                // build retrofit with added OkHttpClient
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient)
                        .build();
            } else {
                // else,
                // build retrofit without added OkHttpClient
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }
}
