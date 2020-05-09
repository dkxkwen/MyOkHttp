package com.vikily.okhttp.net.rx;

import android.content.Context;
import android.graphics.Color;

import com.vikily.okhttp.R;
import com.vikily.okhttp.net.exception.ApiErrorHelper;
import com.vikily.okhttp.util.VLog;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import rx.Subscriber;


public abstract class RxSubscriber<T> extends Subscriber<T> {
    private ZLoadingDialog mProgressDialog;
    private Context mContext;
    private boolean mIsShow;

    public RxSubscriber(Context context) {
        mIsShow = true;
        mContext = context;
        mProgressDialog = createLoadingDialog(mContext, context.getString(R.string.pull_to_refresh_footer_refreshing_label));
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public RxSubscriber() {
        mIsShow = false;
    }

    private static ZLoadingDialog createLoadingDialog(Context context, String msg) {
/*        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);

        ImageView imageView = (ImageView) v.findViewById(R.id.img_loading);
        TextView tipTextView = (TextView) v.findViewById(R.id.tv_text);

        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate);

        imageView.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);

        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));*/

        ZLoadingDialog dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(Z_TYPE.CIRCLE)//设置类型
                .setLoadingColor(context.getResources().getColor(R.color.consult_content))//颜色
                .setHintText(msg)
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY);  // 设置字体颜色
        return dialog;
    }


    @Override
    public void onStart() {
        if (mIsShow) {
            mProgressDialog.show();
        }
        VLog.d("onStart");
    /*    if (!OSUtil.hasInternet()) {
            this.onError(new ApiException(ApiError.ERROR_NET_CONNECTION.getCode(), "ERROR_NET_CONNECTION"));
            return;
        }*/

    }

    @Override
    public void onError(Throwable e) {
        ApiErrorHelper.handleCommonError(mContext, e);
        if (mIsShow) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onNext(T t) {
        VLog.d("onNext");
        if (mIsShow) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onCompleted() {
        VLog.d("onCompleted");
//        mProgressDialog.dismiss();
    }

}
