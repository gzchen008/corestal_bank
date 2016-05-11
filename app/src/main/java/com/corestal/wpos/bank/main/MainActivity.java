package com.corestal.wpos.bank.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.corestal.wpos.bank.arrang.R;
import com.corestal.wpos.bank.common.CSApplicationHolder;
import com.corestal.wpos.bank.service.MainService;
import com.corestal.wpos.bank.service.impl.MainServiceImpl;

/**
 * 主Activity
 * create by cgz on 16-05-10
 */
public class MainActivity extends AppCompatActivity {
    /**
     * 主要业务服务
     */
    private MainService mainService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化数据
        mainService = new MainServiceImpl();
        mainService.initApplication();
    }
}
