package com.vikily.okhttp.net.exception;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.vikily.okhttp.R;
import com.vikily.okhttp.util.toast.MyToast;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class ApiErrorHelper {
    public static void handleCommonError(Context context, Throwable e) {
        if (e instanceof HttpException || e instanceof IOException) {
//            VLog.d("服务器错误,请稍后重试");
            if (NetworkUtils.isNetworkAvailable(context)){
                MyToast.errorL(context.getString(R.string.server_error_please_wait_retry));
//                Toast.makeText(context, "网络异常,请检查网络", Toast.LENGTH_SHORT).show();
            } else {
                MyToast.errorL(context.getString(R.string.view_network_error));
//                Toast.makeText(context, "服务器错误,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        } else {
            handleApiError(context, e);
        }

    }

    private static void handleApiError(Context context, Throwable e) {
        try {
            ApiException apiException = (ApiException) e;
            String errorCode = apiException.getCode();
            Toast.makeText(context,ApiError.getMsg(errorCode),Toast.LENGTH_SHORT).show();
        }catch (Exception es) {
            es.printStackTrace();
        }

    }

    private boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}

