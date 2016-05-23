package com.corestal.wpos.bank.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by cgz on 16-5-23.
 * Home键广播监听
 */
public class HomeWatcherReceiver extends BroadcastReceiver {
    private static String SYSTEM_REASON = "reason";
    private static String SYSTEM_HOME_KEY = "homekey";
    private static String SYSTEM_HOME_KEY_LONG = "recentapps";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_REASON);
            if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                //表示按了home键,程序到了后台
                Toast.makeText(context,"点击home键",Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
                //表示长按home键,显示最近使用的程序列表
            }
        }
    }
}
