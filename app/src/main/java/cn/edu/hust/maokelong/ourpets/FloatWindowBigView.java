package cn.edu.hust.maokelong.ourpets;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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

    /**
     * 通知栏组件
     */

    public FloatWindowBigView(final Context context) {
        super(context);
        //view绑定
        LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
        final View view = findViewById(R.id.big_window_layout);

        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

        button_setting = (ImageButton) findViewById(R.id.button_setting);
        button_setting.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    v.setBackgroundResource(R.drawable.ic_setting1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.ic_setting_pressed);
                    //打开设置界面并关闭二级窗口
                    Intent intent = new Intent(context, SettingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    MyWindowManager.removeBigWindow(getContext());
                }
                return false;
            }
        });

        button_alarm = (ImageButton) findViewById(R.id.button_alarm);
        button_alarm.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    v.setBackgroundResource(R.drawable.ic_alarm1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.ic_alarm_pressed);
                    //打开闹钟界面并关闭二级窗口
                    Intent intent = new Intent(context, AlarmActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    MyWindowManager.removeBigWindow(getContext());
                }
                return false;
            }
        });
        button_remove = (ImageButton) findViewById(R.id.button_remove);
        button_remove.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    v.setBackgroundResource(R.drawable.ic_close1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.ic_close_pressed);
                    //通知栏设置
                    //      创建一个NotificationManager的引用
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                    //      定义Notification的各种属性
                    Notification notification = new Notification(R.drawable.bear
                            , "桌面宠物在这里哦~！", System.currentTimeMillis());
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    notification.flags |= Notification.FLAG_ONGOING_EVENT;
                    notification.flags |= Notification.FLAG_NO_CLEAR;

                    //      设置通知的事件消息
                    CharSequence contentTitle = "桌面大宠物"; // 通知栏标题
                    CharSequence contentText = "点我召唤桌面宠物哦~！"; // 通知栏内容

                    Intent clickIntent = new Intent(context, broadcastPetShow.class); //点击通知之后要发送的广播
                    int id = 100;
                    PendingIntent contentIntent = PendingIntent.getBroadcast(context.getApplicationContext(), id, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    builder.setContentTitle(contentTitle)
                            .setContentText(contentText)
                            .setContentIntent(contentIntent)
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.bear)
                            .setLargeIcon(Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bear)))
                            .setOngoing(true)
                    ;
                    notificationManager.notify(id, builder.build());

                    //隐藏宠物
                    MyWindowManager.removeBigWindow(getContext());
                    MyWindowManager.removeSmallWindow(getContext());
                    context.stopService(new Intent(context, FloatWindowService.class));

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

    public static class broadcastPetShow extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context, FloatWindowService.class));
            MyWindowManager.createSmallWindow(context);
        }

    }
}
