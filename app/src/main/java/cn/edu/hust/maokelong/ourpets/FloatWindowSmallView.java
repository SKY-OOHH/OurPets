package cn.edu.hust.maokelong.ourpets;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.Random;
import java.util.logging.LogRecord;

import pl.droidsonroids.gif.GifImageView;


/**
 * Created by maokelong on 2016/4/17.
 */
public class FloatWindowSmallView extends LinearLayout {

    /**
     * 记录小悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录小悬浮窗的高度
     */
    public static int viewHeight;

    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager windowManager;


    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;
    private static FloatWindowMassageView MassageWindow;
    private static FloatWindowService flag;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    /**
     * 记录手指按下的时间(ms)
     */
    long clickTime;
    Handler handler = new Handler();

    private GifImageView gif;
    private Pet mypet;
    /*随机产生0-4*/
    public int Random(Pet.PetTheme theme)
    {
        int Rand=0;
        Random random=new Random();
        switch(theme){
            case Beaver:
                Rand = random.nextInt(5);
                break;
            case Bear:
                Rand = random.nextInt(6)+5;
                break;
            case BlueSky:
                Rand = random.nextInt(8)+11;
                break;
        }
        return Rand;
    }
    public FloatWindowSmallView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
        View view=findViewById(R.id.small_window_layout);
        viewWidth=view.getLayoutParams().width;
        viewHeight=view.getLayoutParams().height;
        gif=(GifImageView) findViewById(R.id.petGif);
        mypet = new Pet();
        /*Pet.PetAction petAction1;
        Pet.PetTheme petTheme1;
        petTheme1 = mypet.getPetTheme();
        gif.setImageResource(mypet.getStillImageSource(petTheme1));
        /*int a=Random(petTheme1);
        petAction1 = mypet.getPetAction(a);
        gif.setImageResource(mypet.getActionImageSource(petTheme1,petAction1));*/
        final Runnable runnable1= new Runnable() {
            @Override
            public void run() {
                Pet.PetTheme petTheme1 = mypet.getPetTheme();
                gif.setImageResource(mypet.getStillImageSource(petTheme1));
            }
        };
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Pet.PetAction petAction1;
                Pet.PetTheme petTheme1;
                //mypet = new Pet();
                petTheme1 = mypet.getPetTheme();
                petAction1 = mypet.getPetAction(Random(petTheme1));
                gif.setImageResource(mypet.getActionImageSource(petTheme1,petAction1));
                handler.postDelayed(runnable1, 60000);      //动60秒
                handler.postDelayed(this, 600000);        //静600秒
            }
        };
        handler.postDelayed(runnable, 60000);
        Pet.PetTheme petTheme1;
        petTheme1 = mypet.getPetTheme();
        gif.setImageResource(mypet.getStillImageSource(petTheme1));
        //gif.setImageResource(R.mipmap.beaver);
      /*  if(flag.count >= 10)
       {
           mypet = new Pet();
           petTheme1 = mypet.getPetTheme();
           int a=Random(petTheme1);
           petAction1 = mypet.getPetAction(a);
           gif.setImageResource(mypet.getActionImageSource(petTheme1,petAction1));
           flag.count = 0;
        }
        else
            gif.setImageResource(R.mipmap.beaver);*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                //记录按下时间
                clickTime=System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                //按下0.2秒后视为希望移动
                if(System.currentTimeMillis()-clickTime>=200)
                {
                    xInScreen = event.getRawX();
                    yInScreen = event.getRawY() - getStatusBarHeight();
                    // 手指移动的时候更新小悬浮窗的位置
                    updateViewPosition();
                }
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    if (MyWindowManager.flag1==0)
                        openBigWindow();
                    else
                        openMassageWindow();
                }
                break;
            default:
                break;
        }
        return true;
    }


        /**
         * 打开大悬浮窗，同时关闭小悬浮窗。
         */
    private void openMassageWindow() {
        MyWindowManager.createMassageWindow(getContext());
        MyWindowManager.getMassage(getContext());

    }
    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 打开大悬浮窗，同时关闭小悬浮窗。
     */
    private void openBigWindow() {
        MyWindowManager.createBigWindow(getContext());
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}