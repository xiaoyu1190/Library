package com.wbxm.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbxm.lib.R;
import com.wbxm.lib.utils.StringUtil;

/**
 * Created by Administrator on 2018/2/3 0003.
 */

public class LoadingDialog extends Dialog {

    private ImageView loadingImg;
    private TextView textView;
    private RotateAnimation rotateAnimation;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.transparentDialogStyle);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        loadingImg = (ImageView) contentView.findViewById(R.id.img_loading);
        textView = (TextView) contentView.findViewById(R.id.tv_loading_content);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loadingImg.clearAnimation();
            }
        });
        setContentView(contentView);
        rotateAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1200);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatMode(Animation.INFINITE);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        loadingImg.setAnimation(rotateAnimation);
    }

    public void setContent(String content){
        textView.setText(content);
    }

    @Override
    public void show() {
        super.show();
        if(StringUtil.isEmpty(textView.getText().toString().trim()))
            textView.setVisibility(View.GONE);
        loadingImg.clearAnimation();
        loadingImg.setAnimation(rotateAnimation);
        rotateAnimation.start();
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                rotateAnimation.cancel();
                loadingImg.clearAnimation();
            }
        });
    }

}
