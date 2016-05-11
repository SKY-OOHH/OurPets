package cn.edu.hust.maokelong.ourpets;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/10.
 */
public class Settingabout extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_about);
        ListView list = (ListView) findViewById(R.id.listView1);

        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();


            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "开发人员一号");
            map.put("ItemText", "猫科龙");
            mylist.add(map);

        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("ItemTitle", "开发人员二号");
        map1.put("ItemText", "张阳");
        mylist.add(map1);
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("ItemTitle", "开发人员三号");
        map2.put("ItemText", "金盼盼");
        mylist.add(map2);
        HashMap<String, String> map3 = new HashMap<String, String>();
        map3.put("ItemTitle", "开发人员四号");
        map3.put("ItemText", "张丽");
        mylist.add(map3);
        HashMap<String, String> map4 = new HashMap<String, String>();
        map4.put("ItemTitle", "开发人员五号");
        map4.put("ItemText", "渣橙");
        mylist.add(map4);
        HashMap<String, String> map5 = new HashMap<String, String>();
        map5.put("ItemTitle", "版本号");
        map5.put("ItemText", "1.0");
        mylist.add(map5);

        //生成适配器，数组===》ListItem
        SimpleAdapter mSchedule = new SimpleAdapter(this,
                mylist,//数据来源
                R.layout.my_listitem,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[] {"ItemTitle", "ItemText"},

                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.ItemTitle,R.id.ItemText});
        //添加并且显示
        list.setAdapter(mSchedule);
    }
}
