package com.ablecloud.androidchat.app.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.ablecloud.androidchat.app.R;

/**
 * Created by fangzhenyi on 2015/7/27 0027.
 */
public class CustomTemperatureView extends RelativeLayout{
    private int istem;

    private CustomleftView customleftView;

    private CustomChatView customChatView;

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

    public void setChatlisten(ChatListenner chatlisten){

        customChatView.setChatListenner(chatlisten);

    }


    public void setData(int[]data){
        customChatView.setData(data);

    }

    public CustomTemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.custom_temperature_view,this,true);
        customleftView=(CustomleftView)this.findViewById(R.id.customleftView);
        customChatView=(CustomChatView)this.findViewById(R.id.customChatview);
        customleftView.setHum(false);
        customChatView.setHum(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(yCount*intervalX+getdippx(10), (xCount-1) * intervaleY+getdippx(20));
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
