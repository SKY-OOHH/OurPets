package cn.edu.hust.maokelong.ourpets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by maokelong on 2016/4/17.
 */
public class FloatWindowMassageView extends LinearLayout {

    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;
    public static FloatWindowService floatwindowservice;

    public static String content=null;
    public static int flag=1;
    public static int count=0;
    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;

    public FloatWindowMassageView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_massage, this);
        final View view = findViewById(R.id.Massage_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        TextView Massge_View = (TextView) findViewById(R.id.Massge_View);
        Massge_View.getBackground().setAlpha(240);//0~255透明度值
        Massge_View.setText(MyWindowManager.getMassage());

       /* Button weixin = (Button) findViewById(R.id.button);
        weixin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                MyWindowManager.removeMassageWindow(getContext());

            }
        });*/

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                MyWindowManager.removeMassageWindow(getContext());
                flag=1;
                break;

            default:
                break;
        }
        return true;
    }


}

