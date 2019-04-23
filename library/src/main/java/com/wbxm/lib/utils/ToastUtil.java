package com.wbxm.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wbxm.lib.R;


/**
 * Created by maa2 on 2016/2/26.
 */
public class ToastUtil {

    private static Toast mToast;
    private static TextView toast_Text;

    Handler handlerToast = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public static void initToast(Context context) {
        //mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_text, null);
        mToast = new Toast(context);
        mToast.setView(toastRoot);
        toast_Text = (TextView) toastRoot.findViewById(R.id.toast_notice);
    }

    public static void showToast(Context context, String text, int duration) {
        if (mToast != null) {
            toast_Text.setText(text);
        } else {
            initToast(context);
            toast_Text.setText(text);
            // mToast = Toast.makeText(context, text, duration);
        }
        mToast.show();
    }

    public static void ToastMessage(Context context, String msg) {
        try {
            if (msg == null)
                msg = "网络请求失败，请重试!";
            msg = msg.replace("java.lang.Exception:", "").replace("java.lang.NullPointerException", "");
            showToast(context, msg, Toast.LENGTH_LONG);
        } catch (Exception ex) {
            String e = ex.toString();
        }
    }

    public static void ToastToRunOnUiThread(final Activity activity, final String msg) {
        try {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(activity, StringUtil.isEmpty(msg) ? "网络请求失败，请重试!" : msg, Toast.LENGTH_LONG);
                }
            });

        } catch (Exception ex) {
            String e = ex.toString();
        }
    }
}
