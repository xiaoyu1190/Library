package com.wbxm.lib.timer;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TimerChange {

    private int mCount = 60;
    private int mTemp;

    private Handler vHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            mTemp--;
            if (mTemp <= 0) {
                mTemp = mCount;
                if (listener != null) {
                    listener.onChange();
                }
            }
            startTime();
            return false;
        }
    });

    private onChangeListener listener;

    public TimerChange(int count, onChangeListener listener) {
        this.mCount = count;
        this.mTemp = count;
        this.listener = listener;
        startTime();
    }

    public void startTime() {
        vHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vHandler.sendEmptyMessage(0);
            }
        }, 1000);
    }

    public void stopTime() {
        if (vHandler != null) {
            vHandler.removeMessages(0);
        }
    }

    public void resetTime(){
        mTemp = mCount;
    }


    public interface onChangeListener {
        void onChange();
    }

}
