package com.vikily.okhttp.util;

import android.widget.Toast;

import com.vikily.okhttp.util.toast.MyToast;


public class ToastUtils {

	private static Toast mToast;

	public static void showLong(String text) {
		MyToast.getInstance().error(text);
	}

	public static void showShort(String text) {
		MyToast.getInstance().error(text);
	}


}
