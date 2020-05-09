package com.vikily.okhttp.net.client;


import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by jiang on 2017/2/23.
 */

public class ClientFactory {
    private OkHttpClient.Builder mBuilder;
    private static ClientFactory mClientFactory;
    private static final long CONNECTION_TIMEOUT = 10;
    private static final long READ_TIMEOUT = 10;



    private ClientFactory(Context context,String cachePath) {
        mBuilder = new OkHttpClient.Builder();
        mBuilder.addInterceptor(ClientHelper.getLoggingInterceptor());
        Cache cache = new Cache(new File(cachePath), 10 * 1024 * 1024);
        mBuilder.retryOnConnectionFailure(true)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .addNetworkInterceptor(ClientHelper.getCacheInterceptor(context))
                .addInterceptor(ClientHelper.getCacheInterceptor(context));
    }


    public static ClientFactory getInstance(Context context,String cachePath) {
        if (mClientFactory == null) {
            synchronized (ClientFactory.class) {
                if (mClientFactory == null) {
                    mClientFactory = new ClientFactory(context,cachePath);
                }
            }
        }
        return mClientFactory;
    }

    public OkHttpClient getOkHttpClient() {
        return mBuilder.build();
    }

}
