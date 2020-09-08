package com.example.search;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class logo extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_layout);

        new CountDownTimer(2000,5000) {

            @Override
            public void onTick(long millisUntilFinished) {
                iv=(ImageView) findViewById(R.id.logo);
                iv.setAlpha(0f);
                iv.setVisibility(View.VISIBLE);
                iv.animate().scaleX(1.3f).scaleY(1.3f).setDuration(2000).alpha(1f).setListener(null);
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(logo.this,loginActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}





    /*private void init() {
        //利用timer让此界面延迟3秒后跳转，timer有一个线程，该线程不断执行task
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                iv=(ImageView)findViewById(R.id.logo);
                iv.setAlpha(0f);
                iv.setVisibility(View.VISIBLE);
                iv.animate()
                        .alpha(1f)
                        .setDuration(5000);
                //发送intent实现页面跳转，第一个参数为当前页面的context，第二个参数为要跳转的主页
                Intent intent = new Intent(logo.this, MainActivity.class);
                startActivity(intent);
                //跳转后关闭当前欢迎页面
                logo.this.finish();

            }
        };
        timer.schedule(timerTask,8000);
    }

     */






