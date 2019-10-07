package com.example.user.stackoverflow.util;

import android.util.Log;

import com.example.user.stackoverflow.network.RetrofitClientInstance;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * RetrofitClient.java
 * Class used to return a retrofit Client Instance;
 *
 * @author khoavo(03 / 10 / 2019)
 */
public class StackApiUtil {
    /**
     * Use for Server Test
     *
     * Note: 172.16.0.189 is IPV4 of this computer build Server Test
     */

    public static StackService getStackService() {
        return RetrofitClientInstance.getRetrofitInstance().create(StackService.class);
    }
}
