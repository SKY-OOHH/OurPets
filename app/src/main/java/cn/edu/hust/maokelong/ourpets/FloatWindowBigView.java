package cn.edu.hust.maokelong.ourpets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import cn.edu.hust.maokelong.ourpets.alarm.AlarmActivity;

/**
 * Created by maokelong on 2016/4/17.
 */
public class FloatWindowBigView extends LinearLayout {
    private ImageButton button_setting;
    private ImageButton button_alarm;
    private ImageButton button_remove;
    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;

    public FloatWindowBigView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
        final View view = findViewById(R.id.big_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

        button_setting =(ImageButton) findViewById(R.id.button_setting)   ;
        button_setting.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                MyWindowManager.removeBigWindow(getContext());

            }

        });
        button_setting.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    v.setBackgroundResource(R.drawable.ic_setting1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.ic_setting_pressed);
                }
                return false;
            }
        });

        button_alarm =(ImageButton) findViewById(R.id.button_alarm)   ;
        button_alarm.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AlarmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                MyWindowManager.removeBigWindow(getContext());
            }
        });
        button_alarm.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    v.setBackgroundResource(R.drawable.ic_alarm1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.ic_alarm_pressed);
                }
                return false;
            }
        });
        button_remove =(ImageButton) findViewById(R.id.button_remove);
        button_remove.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //.startActivity(intent);
                //  MyWindowManager.removeBigWindow(getContext());

            }
        });
        button_remove.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    v.setBackgroundResource(R.drawable.ic_close1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.ic_close_pressed);
                }
                return false;
            }
        });
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 点击窗口外部区域可消除
                // 这点的实现主要将悬浮窗设置为全屏大小，外层有个透明背景，中间一部分视为内容区域
                // 所以点击内容区域外部视为点击悬浮窗外部
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = new Rect();
                View work_space = findViewById(R.id.big_window_layout_work_space);
                work_space.getGlobalVisibleRect(rect);
                if (!rect.contains(x, y)) {
                    //打开小悬浮窗，同时关闭大悬浮窗
                    MyWindowManager.createSmallWindow(getContext());
                    MyWindowManager.removeBigWindow(getContext());
                }

                return false;
            }
        });
    }


}