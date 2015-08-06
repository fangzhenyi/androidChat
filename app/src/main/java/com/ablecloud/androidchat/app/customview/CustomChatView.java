package com.ablecloud.androidchat.app.customview;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;
import com.ablecloud.androidchat.app.R;

/**
 * Created by fangzhenyi on 2015/7/25 0025.
 */
public class CustomChatView extends View implements View.OnTouchListener, GestureDetector.OnGestureListener {


    //X方向间距
    private int intervalX = getdippx(40);
    //y方向间距
    private int intervaleY = getdippx(22);
    //上一次距离
    private int lastx = 0;
    //图表上一次marginleft值
    private int lastmargin = 0;
    //图表平行于x轴的数目
    private int xCount = 8;
    //图表平行于y轴的数目
    private int yCount = 26;
    //屏幕宽度
    private int screenWidth;
    //数据
    private int data[];
    //每日时刻表

    private ChatListenner chatListenner;

    private String time[] = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};

    private boolean isHum = true;

    private GestureDetector mygesturer;

    public void setHum(boolean a) {
        if (a) {
            xCount = 6;
        } else {
            xCount = 8;
            isHum = false;
        }

    }

    public void setChatListenner(ChatListenner chatListenner) {
        this.chatListenner = chatListenner;

    }

    public CustomChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        mygesturer = new GestureDetector(this);

        DisplayMetrics dm = new DisplayMetrics();
        screenWidth = dm.widthPixels;
    }

    public void setData(int[] data) {
        this.data = data;
        invalidate();//刷新控件
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawColor(getResources().getColor(R.color.cavas_chat_bg));
        //绘制虚线
        Paint paint = new Paint();
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        paint.setPathEffect(effects);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(getsppx(1));

        //绘制水平平行于x轴的虚线
        Path path = new Path();

        for (int i = 0; i < xCount; i++) {
            path.moveTo(0, intervaleY * i);
            path.lineTo(yCount * intervalX, intervaleY * i);
            canvas.drawPath(path, paint);
        }
        //绘制水平平行于y轴的虚线
        for (int i = 0; i < yCount; i++) {
            path.moveTo(intervalX * i, 0);
            path.lineTo(intervalX * i, intervaleY * (xCount - 1));
            canvas.drawPath(path, paint);
        }
        //大圆
        Paint bigcirclePaint = new Paint();
        bigcirclePaint.setColor(getResources().getColor(R.color.white));
        bigcirclePaint.setAntiAlias(true);
        bigcirclePaint.setStyle(Paint.Style.FILL);
        bigcirclePaint.setAlpha(100);
        //小圆
        Paint smallcirclePaint = new Paint();
        smallcirclePaint.setColor(getResources().getColor(R.color.white));
        smallcirclePaint.setAntiAlias(true);
        smallcirclePaint.setStyle(Paint.Style.FILL);

        //绘制折线图
        Paint linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.white));
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(getdippx(1));

        Path linePath = new Path();

        Paint paintText = new Paint();
        paintText.setColor(getResources().getColor(R.color.white));
        paintText.setTextSize(getsppx(10));
        paintText.setAntiAlias(true);
        paintText.setStrokeWidth(getdippx(1));
        paintText.setTextAlign(Paint.Align.CENTER);

        Path pathbg = new Path();
        pathbg.moveTo(0, (xCount - 1) * intervaleY);
        pathbg.lineTo(0 + yCount * intervalX, (xCount - 1) * intervaleY);
        pathbg.lineTo(0 + yCount * intervalX, (xCount - 1) * intervaleY + getdippx(50));
        pathbg.lineTo(0, (xCount - 1) * intervaleY + getdippx(50));

        Paint paintbg = new Paint();
        paintbg.setColor(getResources().getColor(R.color.canvas_bg));
        paintbg.setStyle(Paint.Style.FILL);


        canvas.drawPath(pathbg, paintbg);
        //绘制时间
        for (int i = 1; i < yCount - 1; i++) {

            canvas.drawText(time[i - 1], intervalX * i, intervaleY * (xCount - 1) + getdippx(15), paintText);

        }


        if (isHum) {

            for (int i = 1; i < data.length; i++) {
                float c = (float) data[i - 1] / 100;
                int b = (int) ((1 - c) * (xCount - 1) * intervaleY);

                if (i == 1) {
                    linePath.moveTo(intervalX, b);
                }
                linePath.lineTo(i * intervalX, b);
                canvas.drawPath(linePath, linePaint);
                canvas.drawCircle(i * intervalX, b, getdippx(7), bigcirclePaint);
                canvas.drawCircle(i * intervalX, b, getdippx(3), smallcirclePaint);
            }


        } else {

            for (int i = 1; i < data.length; i++) {
                int b = 0;
                if (data[i - 1] > 0) {
                    b = data[i - 1] + 20;
                    float c = (float) b / 70;
                    b = (int) ((1 - c) * (xCount - 1) * intervaleY);
                    android.util.Log.i("FANG", "" + (float) b / 70);
                } else {
                    b = 20 + data[i - 1];
                    float c = (float) b / 70;
                    b = (int) ((1 - c) * (xCount - 1) * intervaleY);
                    android.util.Log.i("FANG", "" + (float) b / 70);
                }
                if (i == 1) {
                    linePath.moveTo(intervalX, b);
                }
                linePath.lineTo(i * intervalX, b);
                canvas.drawPath(linePath, linePaint);
                canvas.drawCircle(i * intervalX, b, getdippx(7), bigcirclePaint);
                canvas.drawCircle(i * intervalX, b, getdippx(3), smallcirclePaint);
            }


        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isHum) {
            setMeasuredDimension(yCount * intervalX + getdippx(10), (xCount - 1) * intervaleY + getdippx(50));
        } else {
            setMeasuredDimension(yCount * intervalX + getdippx(10), (xCount - 1) * intervaleY + getdippx(50));
        }


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        mygesturer.onTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v
                        .getLayoutParams();
                lastmargin = lParams.leftMargin;
                android.util.Log.i("FANG", "上一次的margin_left" + lastmargin);
                lastx = (int) event.getRawX();
                android.util.Log.i("FANG", "上一次的lastx值" + lastx);
                break;

            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v
                        .getLayoutParams();
                android.util.Log.i("FANG", "event.getRawx" + event.getRawX() + "lastx:" + lastx);
                int xx = (int) event.getRawX() - lastx;
                int temxx = lastmargin + xx;

                if (temxx > getdippx(0)) {
                    temxx = getdippx(0);
                }
                if (temxx < -((yCount - 1) * intervalX - screenWidth - getdippx(330))) {
                    temxx = -((yCount - 1) * intervalX - screenWidth - getdippx(330));
                }
                layoutParams.leftMargin = temxx;
                android.util.Log.i("FANG", "event.getRawx" + event.getRawX());
                android.util.Log.i("FANG", "移动距离" + xx);
                //  v.setLayoutParams(layoutParams);
                scrollTo(-temxx, 0);

                invalidate();
                break;
        }
        return true;
    }

    private int getdippx(int dpValue) {

        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }

    private int getsppx(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        return super.onFilterTouchEventForSecurity(event);
    }

    public CustomChatView(Context context) {
        super(context);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("Y轴移动距离", "" + (e1.getY() - e2.getY()));

        if ((e2.getY() - e1.getY() > getdippx(30)) && (Math.abs(velocityY) > getdippx(25))) {
             chatListenner.moveActivity();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }


}

