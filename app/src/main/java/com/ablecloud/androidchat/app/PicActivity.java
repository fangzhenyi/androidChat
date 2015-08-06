package com.ablecloud.androidchat.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.ablecloud.androidchat.app.activity.BaseActivity;


public class PicActivity extends BaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private ImageView temperatureImageView;
    private ImageView humiditypoint;
    private TextView temperatureText;
    private TextView humidityText;
    private LinearLayout linearLayout;


    private GestureDetector mygesturer;

    private ImageView upImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        temperatureImageView = (ImageView) findViewById(R.id.temperature);
        humiditypoint = (ImageView) findViewById(R.id.humidpoint_point);
        temperatureText = (TextView) findViewById(R.id.temperature_text);
        humidityText = (TextView) findViewById(R.id.humidity_text);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        upImage = (ImageView) findViewById(R.id.pic_up_image);
        setTemperature(18);
        setHumiditypoint(30);
        setTitle("温度和湿度");
        setNavBtn(R.drawable.back1, 0);

        linearLayout.setOnTouchListener(this);

        mygesturer = new GestureDetector(this);

        upImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mygesturer.onTouchEvent(event);
                return false;
            }
        });

        upImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PicActivity.this, ChatActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.pic_zoom_in, R.anim.pic_zoom_out);
            }
        });


    }

    private void setTemperature(int h) {
        RelativeLayout.LayoutParams laParams = (RelativeLayout.LayoutParams) temperatureImageView.getLayoutParams();
        laParams.height = (int) (h * 5 * getResources().getDisplayMetrics().density / 2);
        temperatureImageView.setLayoutParams(laParams);
        temperatureText.setText(h + "°");
    }

    private void setHumiditypoint(int h) {
        humiditypoint.setRotation(3 * h - 150);
        humidityText.setText("" + h);
    }

    private int getdippx(int dpValue) {

        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }

    @Override
    protected void HandleTitleBarEvent(TitleBar component, View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        mygesturer.onTouchEvent(event);


        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (e2.getY() - e1.getY() < -getdippx(30) && Math.abs(velocityY) > getdippx(25)) {
            Intent intent = new Intent();
            intent.setClass(PicActivity.this, ChatActivity.class);
            startActivity(intent);
            this.finish();
            overridePendingTransition(R.anim.pic_zoom_in, R.anim.pic_zoom_out);


        }
        return false;
    }


}
