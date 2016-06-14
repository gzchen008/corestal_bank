package com.corestal.wpos.bank.utils;

import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.corestal.wpos.bank.R;

/**
 * Created by cgz on 16-6-10.
 * 动画工具类
 * 包含：点击效果
 */
public class AnimtionUtils {


    /**
     * 给试图添加点击效果,让背景变深
     */
    public static void addTouchDrak(View view, boolean isClick) {
        view.setOnTouchListener(VIEW_TOUCH_DARK);

        if (!isClick) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    public static void addClickAnimotionForView(View view) {
        view.setOnTouchListener(VIEW_CLICK_LIGHT);
    }

    /**
     * 给试图添加点击效果,让背景变暗
     */
    public static void addTouchLight(View view, boolean isClick) {
        view.setOnTouchListener(VIEW_TOUCH_LIGHT);

        if (!isClick) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                }
            });
        }
    }


    /**
     * 让控件点击时，颜色变深
     */
    public static final View.OnTouchListener VIEW_TOUCH_DARK = new View.OnTouchListener() {

        public final float[] BT_SELECTED = new float[]{1, 0, 0, 0, -50, 0, 1,
                0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0};
        public final float[] BT_NOT_SELECTED = new float[]{1, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(
                            new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            }
            return false;
        }
    };

    /**
     * 让控件点击时，颜色变暗
     */
    public static final View.OnTouchListener VIEW_TOUCH_LIGHT = new View.OnTouchListener() {

        public final float[] BT_SELECTED = new float[]{1, 0, 0, 0, 50, 0, 1,
                0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0};
        public final float[] BT_NOT_SELECTED = new float[]{1, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setDrawingCacheEnabled(true);
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                } else {
                    if (v.getBackground() != null) {
                        v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                        v.setBackground(v.getBackground());
                    }
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(
                            new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackground(v.getBackground());
                }
            }
            return false;
        }
    };

    private static final View.OnTouchListener VIEW_CLICK_LIGHT = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundResource(R.color.click_bg_color);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackground(null);
            }
            return false;
        }
    };
}
