package com.corestal.wpos.bank.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.corestal.wpos.bank.R;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_startup);

        ImageView splashImg = (ImageView) findViewById(R.id.iv_banner);
        splashImg.postDelayed(new Runnable() {//这里利用了View的postDelayed

            public void run() {
                Intent intent = new Intent();
                intent.setClass(StartupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


}
