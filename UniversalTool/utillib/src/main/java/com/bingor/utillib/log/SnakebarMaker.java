package com.bingor.utillib.log;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.bingor.utillib.R;


/**
 * Created by Bingor on 2018/3/25.
 */

public class SnakebarMaker {
    private static Snackbar snackbar;
    public static int color = Color.parseColor("#3DC1C7");

    public static void makeSnake(View view, String text, int duration, String ok, View.OnClickListener onOkClickListener) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
//        if (snackbar == null) {
        snackbar = Snackbar.make(view, text, duration);
//        } else {
//            snackbar.setText(text);
//        }
        snackbar.setAction(ok, onOkClickListener);
        snackbar.setActionTextColor(color);
        snackbar.show();
    }

    public static void makeSnake(View view, String text) {
        makeSnake(view, text, Snackbar.LENGTH_SHORT, null, null);
    }

    public static void makeSnake(View view, String text, String ok) {
        makeSnake(view, text, Snackbar.LENGTH_SHORT, ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void makeSnake(View view, String text, String ok, View.OnClickListener onOkListener) {
        makeSnake(view, text, Snackbar.LENGTH_SHORT, ok, onOkListener);
    }

    public static void makeSnake(View view, int textId) {
        makeSnake(view, view.getContext().getResources().getString(textId), Snackbar.LENGTH_SHORT, null, null);
    }

    public static void makeSnake(View view, int textId, String ok) {
        makeSnake(view, view.getContext().getResources().getString(textId), Snackbar.LENGTH_SHORT, ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void makeSnake(View view, int textId, int okTextId, View.OnClickListener onOkListener) {
        makeSnake(view, view.getContext().getResources().getString(textId), Snackbar.LENGTH_SHORT, view.getContext().getResources().getString(okTextId), onOkListener);
    }


    public static void makeSnakeLong(View view, String text) {
        makeSnake(view, text, Snackbar.LENGTH_LONG, null, null);
    }

    public static void makeSnakeLong(View view, String text, String ok) {
        makeSnake(view, text, Snackbar.LENGTH_LONG, ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void makeSnakeLong(View view, String text, String ok, View.OnClickListener onOkListener) {
        makeSnake(view, text, Snackbar.LENGTH_LONG, ok, onOkListener);
    }

    public static void makeSnakeLong(View view, int textId) {
        makeSnake(view, view.getContext().getResources().getString(textId), Snackbar.LENGTH_LONG, null, null);
    }

    public static void makeSnakeLong(View view, int textId, String ok) {
        makeSnake(view, view.getContext().getResources().getString(textId), Snackbar.LENGTH_LONG, ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void makeSnakeLong(View view, int textId, int okTextId, View.OnClickListener onOkListener) {
        makeSnake(view, view.getContext().getResources().getString(textId), Snackbar.LENGTH_LONG, view.getContext().getResources().getString(okTextId), onOkListener);
    }


    public static void makeSnakeIndefinite(View view, String text, String ok) {
        makeSnake(view, text, Snackbar.LENGTH_INDEFINITE, ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void makeSnakeIndefinite(View view, String text, String ok, View.OnClickListener onOkListener) {
        makeSnake(view, text, Snackbar.LENGTH_INDEFINITE, ok, onOkListener);
    }

    public static void makeSnakeIndefinite(View view, int textId, int okTextId) {
        makeSnake(view, view.getContext().getResources().getString(textId), Snackbar.LENGTH_INDEFINITE, view.getContext().getResources().getString(okTextId), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void makeSnakeIndefinite(View view, int textId, int okTextId, View.OnClickListener onOkListener) {
        makeSnake(view, view.getContext().getResources().getString(textId), Snackbar.LENGTH_INDEFINITE, view.getContext().getResources().getString(okTextId), onOkListener);
    }

}

