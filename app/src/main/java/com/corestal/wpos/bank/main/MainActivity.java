package com.corestal.wpos.bank.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.corestal.wpos.bank.R;
import com.corestal.wpos.bank.biz.entity.FunctionMenu;
import com.corestal.wpos.bank.biz.entity.Order;
import com.corestal.wpos.bank.broadcast.HomeWatcherReceiver;
import com.corestal.wpos.bank.common.CSApplicationHolder;
import com.corestal.wpos.bank.service.MainService;
import com.corestal.wpos.bank.service.impl.MainServiceImpl;
import com.corestal.wpos.bank.utils.HomeKeyLocker;
import com.corestal.wpos.bank.view.adapter.OrderListAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.weipass.pos.sdk.Weipos;
import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * 主Activity
 * create by cgz on 16-05-10
 */
public class MainActivity extends AppCompatActivity {
    /**
     * 取号按钮
     */
    @ViewInject(R.id.btn_take_no)
    private Button takeNoBtn;

    /**
     * 订单列表
     */
    @ViewInject(R.id.order_list_view)
    private ListView orderListView;

    /**
     * 侧滑菜单
     */
    private SlidingMenu slidingMenu;


    /**
     * order List Adapter
     */
    private OrderListAdapter orderListAdapter;

    /**
     * 主要业务服务
     */
    private MainService mainService;

    /**
     * 订单数据
     */
    private List<Order> orderList;

    /**
     * Home事件监听器
     */
    private HomeWatcherReceiver homeWatcherReceiver;

    /**
     * 锁home
     */
    private HomeKeyLocker homeKeyLocker;

    /**
     * 就用标题
     */
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        title = (String) getTitle();

        // Xutils view inject
        ViewUtils.inject(this);

        init();

        setListener();

        homeWatcherReceiver = new HomeWatcherReceiver();

        // 禁用home键
        //registerReceiver(homeWatcherReceiver, new IntentFilter(
        //      Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        //homeKeyLocker = new HomeKeyLocker();
        //homeKeyLocker.lock(this);

    }

    // TODO 此方法只会存在于开发阶段
    @Deprecated
    private void initTestData() {
        orderList = new ArrayList<Order>();
        Order order = null;
        for (int i = 0; i < 5; i++) {
            order = new Order();
            order.setOrderTime(new Date());
            order.setFunctionMenu(CSApplicationHolder.getFunctionMenu(3));
            order.setOrderNum("A" + i);
            orderList.add(order);
        }
    }

    private void init() {
        // 初始化数据
        mainService = new MainServiceImpl();
        mainService.initApplication();
        orderList = new ArrayList<Order>();
        // TODO 初始化时从数据库中取出数据
        orderListAdapter = new OrderListAdapter(this, orderList);
        orderListView.setAdapter(orderListAdapter);

        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setMenu(R.layout.slidingmenumain);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        // 初始化wang pos
        WeiposImpl.as().init(this, new Weipos.OnInitListener() {
            @Override
            public void onInitOk() {
                WeiposImpl.as().speech("排队系统就绪");
            }

            @Override
            public void onError(String s) {
                Toast.makeText(MainActivity.this, "SDK初始化失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 为View 设置事件监听
     */
    private void setListener() {
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    /**
     * 取号按钮事件
     *
     * @param view
     */
    @OnClick(R.id.btn_take_no)
    public void takeNoBtnClick(View view) {
        showFunctionMenuDialog(view);
    }

    /**
     * 显示类型弹出层
     */
    private void showFunctionMenuDialog(View view) {
        final String[] fmNames = CSApplicationHolder.getFunctionMenuNames();
        // 自定义选择列表
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this).setTitle(R.string.function_menu_name).setItems(fmNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FunctionMenu functionMenu = CSApplicationHolder.getFunctionMenuList().get(which);
                Order order = mainService.takeNo(functionMenu.getId());
                orderList.add(order);
                orderListAdapter.notifyDataSetChanged();
                try {
                    DbUtils.create(MainActivity.this).save(order);
                } catch (DbException e) {
                    LogUtils.d("保存订单出错");
                    e.printStackTrace();
                }
            }
        });
        Dialog dialog = dialogBuilder.create();
        // 半透明
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.alpha = 0.9f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        slidingMenu.toggle(true);
        return super.onKeyDown(keyCode, event);

    }


}
