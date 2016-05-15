package cn.edu.hust.maokelong.ourpets;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * Created by lenovo on 2016/5/15.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    private static SettingActivity settingActivity;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(settingActivity.start_flag == 1) {
            Intent i = new Intent(context, FloatWindowService.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(i);
        }
    }
}
