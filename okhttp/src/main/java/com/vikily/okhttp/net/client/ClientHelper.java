package com.vikily.okhttp.net.client;


import android.content.Context;

import com.vikily.okhttp.BuildConfig;
import com.vikily.okhttp.util.OSUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by jiang on 2017/2/23.
 */

public class ClientHelper {
    public static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return httpLoggingInterceptor;
    }

    public static Interceptor getCacheInterceptor(final Context context) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!OSUtil.hasInternet(context)) {
                    request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if (OSUtil.hasInternet(context)) {
                    String cacheControl = request.cacheControl().toString();
                    if (cacheControl != null) {
                        return response.newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache_Control", cacheControl).build();
                    } else {
                        int max_age = 36000;
                        return response.newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache_Control", "public,max-age=" + max_age).build();
                    }

                } else {
                    int max_stale = 360000;
                    return response.newBuilder().removeHeader("Pragma")
                            .header("Cache_Control", "public,only-if-cached,max-stale=" + max_stale)
                            .build();
                }
            }
        };
        return interceptor;
    }

}
