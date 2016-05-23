package cn.edu.hust.maokelong.ourpets;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import cn.edu.hust.maokelong.ourpets.alarm.AlarmActivity;

public class SettingActivity extends Activity  {
    public MyReceiver receiver = null;
    public static int massage_flag = 0;
    public static int start_flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
        //注册广播接收器
        startService(new Intent(SettingActivity.this, NotimassageService.class));
        receiver = new  MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("cn.edu.hust.maokelong.ourpets.NotimassageService");
         registerReceiver(receiver, filter);
    }//使用perferenceFragment
    public  static   class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {
        private Preference about;
        private Preference clock;
        private Preference blueteeth;
        private Preference petset;
        private SwitchPreference switch_work;
        private SwitchPreference switch_boot_on;
        private SwitchPreference switch_messager;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

//switch_work
            switch_work = (SwitchPreference) findPreference("work_on");
            if (switch_work.isChecked()) {  //用于第一次使用默认值信息，此处可能会出现bug
                //勾选则开启服务
                Intent intent = new Intent(getActivity(),
                        FloatWindowService.class);
                getActivity().startService(intent);

            }
            //监听
            switch_work.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (switch_work.isChecked()==false) {  //此处的 ischecked信息，为监控发生改变之前的信息，即若进行“打开”行为，则进行判定的ischecked是为“未打开”状态。下同
                        //勾选则开启服务
                        Intent intent = new Intent(getActivity(),
                                FloatWindowService.class);
                        getActivity().startService(intent);

                    } else {
                        //非选中时移除所有悬浮窗，并停止Service
                        Context context = getActivity().getBaseContext();
                        MyWindowManager.removeBigWindow(context);
                        MyWindowManager.removeSmallWindow(context);
                        Intent intent = new Intent(context, FloatWindowService.class);
                        context.stopService(intent);
                    }

                  return true;
               }

            });

//switch_boot_on
            switch_boot_on = (SwitchPreference) findPreference("boot_auto");
            if (switch_boot_on.isChecked()) {//用于第一次使用默认值信息
                //选中work_boot_auto
                start_flag = 1;

            }
            //监听
            switch_boot_on.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (switch_boot_on.isChecked()==false) {
                        //选中work_boot_auto
                        start_flag = 1;

                    } else {
                        //未选中work_boot_auto
                        start_flag = 0;
                    }

                    return true;
                }

            });


//switch_messager
            switch_messager = (SwitchPreference) findPreference("weixin");

            if (switch_messager.isChecked()) {//用于第一次使用默认值信息
                //选中work_boot_auto
                massage_flag = 1;

            }
            else{//用于第一次使用默认值信息
                //选中work_boot_auto
                massage_flag = 0;

            }

            switch_messager.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (switch_messager.isChecked()==false) {
                        //选中work_boot_auto
                        massage_flag = 1;

                    } else {
                        //未选中work_boot_auto
                        massage_flag = 0;
                    }

                    return true;
                }

            });










//Blueteeth
            blueteeth = findPreference("Blueteeth");
            blueteeth.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference about) {

                    //蓝牙操作
                    return true;
                }
            });
//alarm
            clock = findPreference("alarm");
            clock.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference clock) {
                    Intent intent = new Intent(getActivity(), AlarmActivity.class);
                    startActivity(intent);
                    return true;
                }
            });
//about
            about = findPreference("about");
            about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference about) {
                    startActivity(new Intent(getActivity(), Settingabout.class));
                    return true;
                }
            });
//petset
            petset = findPreference("petset");
            petset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference about) {
                    startActivity(new Intent(getActivity(), SettingPet.class));
                    return true;
                }
            });



        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            return true;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            return false;
        }

    }
    @Override
    protected void onDestroy() {
        //结束服务
        stopService(new Intent(SettingActivity.this, NotimassageService.class));
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * 获取广播数据
     *
     * @author jiqinlin
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String content = bundle.getString("count");
        }
    }



}

