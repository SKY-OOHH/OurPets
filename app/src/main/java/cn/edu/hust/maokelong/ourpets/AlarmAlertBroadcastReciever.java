package cn.edu.hust.maokelong.ourpets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.edu.hust.maokelong.ourpets.alarm.Alarm;
import cn.edu.hust.maokelong.ourpets.alarm.alert.StaticWakeLock;
import cn.edu.hust.maokelong.ourpets.alarm.service.AlarmServiceBroadcastReciever;

/**
 * Created by maokelong on 2016/4/29.
 * <p>
 * 1、转发来自"AlarmService"的广播给"AlarmServiceBroadcastReciever"
 * <p>
 * 2、打开 "AlarmAlertActivity"
 * key：     "alarm"
 * value:    广播中的内容
 * falg:     使用新的任务栈
 */

public class AlarmAlertBroadcastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mathAlarmServiceIntent = new Intent(
                context,
                AlarmServiceBroadcastReciever.class);
        context.sendBroadcast(mathAlarmServiceIntent, null);

        StaticWakeLock.lockOn(context);
        Bundle bundle = intent.getExtras();
        final Alarm alarm = (Alarm) bundle.getSerializable("alarm");

//        Intent mathAlarmAlertActivityIntent;
//
//        mathAlarmAlertActivityIntent = new Intent(context, AlarmAlertActivity.class);
//
//        mathAlarmAlertActivityIntent.putExtra("alarm", alarm);
//
//        mathAlarmAlertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        context.startActivity(mathAlarmAlertActivityIntent);
        //移除二级窗口，设置消息窗口的内容为闹钟标签，打开一级窗口、消息窗口，激活smallView闹钟（等待解锁、震动etc）
        MyWindowManager.removeBigWindow(context);
        MyWindowManager.createSmallWindow(context);
        //TODO 显示闹钟消息 消息内容为alarm.getAlarmName()
        FloatWindowMassageView.content = alarm.getAlarmName();
        MyWindowManager.createMassageWindow(context);
        FloatWindowSmallView.alarmBegin(context);
    }

}

