package com.bingor.utillib;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;


/**
 * 计时器
 * ********************************使用方法***********************************
 * 1.初步使用
 * TimeCounter.Builder.createDefaultCounter(View).start()  创建默认计时器，所有参数默认，时长60s
 * 或者
 * TimeCounter.Builder(60, ctv_get_ver_code)
 * .setTextCounting("在秒后重新获取") //计数文字
 * .setCountPosition(1)    //倒计时秒数在计数文字的出现位置，比方这里，就是“在xx秒后重新获取”
 * .setTextDefault("获取验证码")//默认文字
 * .setTextPreCount("正在获取验证码")//倒数前文字
 * .setTextRetry("重新获取验证码")//一般用不着，预留
 * .create()
 * .start();
 * 2.如何暂停计时及恢复计时
 * 暂停：timeCounter!!.onPause()
 * 恢复：TimeCounter.Builder.restart(ctv_get_ver_code)
 * **************************************************************************
 * Created by HXB on 2017-09-15.
 */

public class TimeCounter<V extends TextView> extends CountDownTimer {
    //默认文字，计数时文字， 计数前文字,重试文字
    private String textDefault, textCounting, textPreCount, textRetry, count;
    //数字出现的位置
    private int countPosition;
    private boolean pause, ticking;
    private V view;
    private OnTimeCountListener onTimeCountListener;

    /**
     * @param secondsInFuture 总时长(秒)
     */
    public TimeCounter(long secondsInFuture, V view) {
        super(secondsInFuture * 1000, 1000);
        pause = false;
        this.view = view;
    }

    public void init() {

    }

    @Override
    public void onTick(long millisUntilFinished) {
        ticking = true;
        if (pause) {
            return;
        }
        //计时过程显示
        count = textCounting.substring(0, countPosition) + (millisUntilFinished / 1000) + textCounting.substring(countPosition, textCounting.length());
        view.setClickable(false);
        view.setText(count);

        if (onTimeCountListener != null) {
            onTimeCountListener.onCount(count);
        }
    }

    @Override
    public void onFinish() {
        ticking = false;
        if (pause) {
            return;
        }
        //计时完毕时触发
        showDefault();
        if (onTimeCountListener != null) {
            onTimeCountListener.onFinish(textDefault);
        }
    }

    /**
     * 显示默认文字
     */
    public void showDefault() {
        view.setText(textDefault);
        view.setClickable(true);
    }

    /**
     * 显示倒数前文字
     */
    public void showPreCount() {
        view.setText(textPreCount);
        view.setClickable(false);
    }

    /**
     * 显示重试
     */
    public void showRetry() {
        view.setText(textRetry);
        view.setClickable(true);
    }


    public void showCount() {
        if (!TextUtils.isEmpty(count)) {
            view.setText(count);
            view.setClickable(false);
        }
    }


    public void onPause() {
        pause = true;
    }

    public void restart() {
        pause = false;
    }


    public static class Builder<V extends TextView> {
        private static TimeCounter timeCounter;

        /**
         * @param millisInFuture 总时长
         * @param view
         */
        public Builder(long millisInFuture, V view) {
            this.timeCounter = new TimeCounter(millisInFuture, view);
        }

        public Builder setTextDefault(String textDefault) {
            timeCounter.textDefault = textDefault;
            return this;
        }

        public Builder setTextCounting(String textCounting) {
            timeCounter.textCounting = textCounting;
            return this;
        }

        public Builder setTextPreCount(String textPreCount) {
            timeCounter.textPreCount = textPreCount;
            return this;
        }

        public Builder setTextRetry(String textRetry) {
            timeCounter.textRetry = textRetry;
            return this;
        }

        public Builder setCountPosition(int countPosition) {
            timeCounter.countPosition = countPosition;
            return this;
        }

        public Builder setView(V view) {
            timeCounter.view = view;
            return this;
        }

        public Builder setOnTimeCountListener(OnTimeCountListener onTimeCountListener) {
            timeCounter.onTimeCountListener = onTimeCountListener;
            return this;
        }

        public TimeCounter create() {
            timeCounter.init();
            return timeCounter;
        }


        public static <V extends TextView> TimeCounter createDefaultCounter(V view) {
            return createDefaultCounter(view, null);
        }

        public static <V extends TextView> TimeCounter createDefaultCounter(V view, OnTimeCountListener onTimeCountListener) {
            return new Builder(60, view)
                    .setCountPosition(0)
                    .setTextCounting("秒后重新获取")
                    .setTextDefault("获取验证码")
                    .setTextPreCount("正在获取验证码")
                    .setTextRetry("重新获取验证码")
                    .setOnTimeCountListener(onTimeCountListener)
                    .create();
        }

        public static <V extends TextView> TimeCounter createCounter(long millisInFuture, V view, String textDefault, String textPreCount, String textCounting, String textRetry, int countPosition) {
            return new Builder(millisInFuture, view)
                    .setCountPosition(countPosition)
                    .setTextCounting(textCounting)
                    .setTextDefault(textDefault)
                    .setTextPreCount(textPreCount)
                    .setTextRetry(textRetry)
                    .create();
        }

        public static <V extends TextView> boolean restart(V view) {
            if (timeCounter == null) {
                return false;
            } else {
                timeCounter.view = view;
                timeCounter.restart();
                if (timeCounter.ticking) {
                    timeCounter.showCount();
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public interface OnTimeCountListener {
        public void onCount(String count);

        public void onFinish(String textDefault);
    }
}
