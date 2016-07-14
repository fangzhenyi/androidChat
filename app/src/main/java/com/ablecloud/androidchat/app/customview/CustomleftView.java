package com.ablecloud.androidchat.app.customview;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.ablecloud.androidchat.app.R;

/**
 * Created by fangzhenyi on 2015/7/27 0027.
 */
public class CustomleftView extends View {
    //X方向间距
    private int intervalX = getdippx(40);
    //y方向间距
    private int intervaleY = getdippx(22);
    private int xCount = 8;

    private String temperRature[] = {"60°", "40°", "30°", "20°", "10°", "0°", "-10°", "-20°"};

    private String humidity[] = {"100%", "80%", "60%", "40%", "20%", "0%"};

    public CustomleftView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private String la[] = {};

    public void setHum(boolean a) {
        if (a) {
            la = humidity;
            xCount = 6;
        } else {
            la = temperRature;
            xCount = 8;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.canvas_bg));

        Paint paint = new Paint();
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        paint.setPathEffect(effects);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4);

        Paint paintText = new Paint();
        paintText.setColor(getResources().getColor(R.color.white));
        paintText.setStyle(Paint.Style.STROKE);
        paintText.setAntiAlias(true);
        paintText.setTextSize(getsppx(14));
        paintText.setTextAlign(Paint.Align.RIGHT);
        Path path = new Path();
        for (int i = 0; i < xCount; i++) {
            path.moveTo(0, intervaleY * i);
            path.lineTo(getdippx(50), intervaleY * i);
            canvas.drawPath(path, paint);
            canvas.drawText(la[i], getdippx(40), intervaleY * i - getdippx(5), paintText);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getdippx(50), (xCount - 1) * intervaleY + getdippx(50));
    }

    private int getdippx(int dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getsppx(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
