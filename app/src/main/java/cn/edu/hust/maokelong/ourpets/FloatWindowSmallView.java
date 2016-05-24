package cn.edu.hust.maokelong.ourpets;

import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Random;

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
    public static WindowManager.LayoutParams mParams;
    private static FloatWindowMassageView MassageWindow;

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

    /**
     * 记录手指点击的次数
     */
    private int clickTimes;

    /**
     * 震动控制对象
     */
    private static Vibrator vibrator;

    /**
     * 震动记录对象
     */
    private static boolean vibrator_working;

    /**
     * 闹钟提示toast
     */
    private static Toast toast;


    Handler handler = new Handler();

    private GifImageView gif;
    private Pet mypet;

//    final Runnable runnable_vibrator = new Runnable() {
//        @Override
//        public void run() {
//            //启动震动，并持续指定的时间
//            vibrator.vibrate(3500);
//            vibrator.
//        }
//    };

    /**
     * 产生随机数，用于选择不同宠物的动态效果
     */
    public int Random(Pet.PetTheme theme) {
        int Rand = 0;
        Random random = new Random();
        switch (theme) {
            case Beaver:
                Rand = random.nextInt(5);
                break;
            case Bear:
                Rand = random.nextInt(6) + 5;
                break;
            case BlueSky:
                Rand = random.nextInt(8) + 11;
                break;
        }
        return Rand;
    }

    /**
     * runnable-静态图
     * runnable1-固定时间动态效果，用于正常切换
     * runnable2-固定时间动态效果，用于拖拽显示，动完关闭
     */
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Pet.PetTheme petTheme1 = mypet.getPetTheme();
            gif.setImageResource(mypet.getStillImageSource(petTheme1));
        }
    };
    final Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            Pet.PetAction petAction1;
            Pet.PetTheme petTheme1;
            //mypet = new Pet();
            petTheme1 = mypet.getPetTheme();
            petAction1 = mypet.getPetAction(Random(petTheme1));
            gif.setImageResource(mypet.getActionImageSource(petTheme1, petAction1));
            handler.postDelayed(runnable, 6000);      //动6秒
            handler.postDelayed(this, 24000);         //静24秒后继续动
        }
    };
    final Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            Pet.PetAction petAction1;
            Pet.PetTheme petTheme1;
            //mypet = new Pet();
            petTheme1 = mypet.getPetTheme();
            petAction1 = mypet.getPetAction(Random(petTheme1));
            gif.setImageResource(mypet.getActionImageSource(petTheme1, petAction1));
            handler.postDelayed(runnable, 6000);      //动6秒
            handler.postDelayed(this, 24000);         //静24秒后继续动
        }
    };

    public FloatWindowSmallView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
        View view = findViewById(R.id.small_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        gif = (GifImageView) findViewById(R.id.petGif);
        mypet = new Pet(context);
        vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        /*Pet.PetAction petAction1;
        Pet.PetTheme petTheme1;
        petTheme1 = mypet.getPetTheme();
        gif.setImageResource(mypet.getStillImageSource(petTheme1));
        int a=Random(petTheme1);
        petAction1 = mypet.getPetAction(a);
        gif.setImageResource(mypet.getActionImageSource(petTheme1,petAction1));
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
        };*/
        handler.postDelayed(runnable1, 30000);           //30秒后开始动
        Pet.PetTheme petTheme1;
        petTheme1 = mypet.getPetTheme();
        gif.setImageResource(mypet.getStillImageSource(petTheme1));
        /*gif.setImageResource(R.mipmap.beaver);
       if(flag.count >= 10)
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
                clickTime = System.currentTimeMillis();
                /**
                 * 按下的时候，宠物开始动
                 */
                Pet.PetTheme petTheme1;
                petTheme1 = mypet.getPetTheme();
                gif.setImageResource(mypet.getMoveImageSource(petTheme1));
                break;
            case MotionEvent.ACTION_MOVE:
                //按下0.2秒后视为希望移动
                if (System.currentTimeMillis() - clickTime >= 200) {
                    xInScreen = event.getRawX();
                    yInScreen = event.getRawY() - getStatusBarHeight();
                    // 手指移动的时候更新小悬浮窗的位置
                    updateViewPosition();

                }
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    if (MyWindowManager.flag1 == 0)
                        openBigWindow();
                    else
                        openMassageWindow();
                } else {
                    //手指离开屏幕，并判断是拖拽事件
                    Pet.PetTheme petTheme2;                                     //停止动态效果
                    petTheme2 = mypet.getPetTheme();
                    gif.setImageResource(mypet.getStillImageSource(petTheme2));
                    handler.postDelayed(runnable2, 60000);                   //60秒后开始随机动
                    handler.removeCallbacks(runnable2);                     //关闭此次随机动，继续runnable1
                    //移动距离大于200，且正在震动则停止震动
                    if (vibrator_working &&
                            Math.sqrt(Math.pow(xDownInScreen - xInScreen, 2) + Math.pow(yDownInScreen - yInScreen, 2)) > 200) {
                        alarmFinish();
                    }
                    //宠物贴边
                    int screenWidth = this.getResources().getDisplayMetrics().widthPixels;
                    if (xInScreen < screenWidth / 2) {
                        //当x坐标位置于屏幕中线左半边时
                        xInScreen = xInView;
                    } else {
                        xInScreen = screenWidth;
                    }
                    updateViewPosition();
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

    public static void alarmBegin(Context context) {
        if (vibrator.hasVibrator()) {
            vibrator_working = true;
            //循环震动
            long[] pattern = {1000, 200, 200, 200};
            vibrator.vibrate(pattern, 0);
        }
        //开启取消震动提示
        toast=Toast.makeText(context, "滑动宠物以关闭闹钟",Toast.LENGTH_LONG);
        toast.show();
    }

    public static void alarmFinish() {
        vibrator.cancel();
        toast.cancel();
    }

}