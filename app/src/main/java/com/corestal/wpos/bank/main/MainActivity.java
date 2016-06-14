package com.corestal.wpos.bank.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.DeviceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corestal.wpos.bank.R;
import com.corestal.wpos.bank.biz.constant.BizConstants;
import com.corestal.wpos.bank.biz.entity.FunctionMenu;
import com.corestal.wpos.bank.biz.entity.Order;
import com.corestal.wpos.bank.broadcast.HomeWatcherReceiver;
import com.corestal.wpos.bank.common.CSApplicationHolder;
import com.corestal.wpos.bank.service.MainService;
import com.corestal.wpos.bank.service.impl.MainServiceImpl;
import com.corestal.wpos.bank.utils.AnimtionUtils;
import com.corestal.wpos.bank.utils.DateUtils;
import com.corestal.wpos.bank.utils.FileUtils;
import com.corestal.wpos.bank.view.adapter.FunctionMenuListAdapter;
import com.corestal.wpos.bank.view.adapter.OrderListAdapter;
import com.corestal.wpos.bank.view.component.MenuFunctionDialog;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 主Activity
 * create by cgz on 16-05-10
 */
public class MainActivity extends AppCompatActivity {
    /**
     * 打印消息
     */
    private final static String PRNT_ACTION = "android.prnt.message";

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
     * 左上角图标按钮
     */
    @ViewInject(R.id.iv_menu_btn)
    private ImageView ivMenu;

    /**
     * 设置侧滑菜单
     */
    private TextView tvSettings;

    /**
     * 开发者侧滑菜单
     */
    private TextView tvDeveloper;

    /**
     * 清空缓存侧滑菜单
     */
    private View tvCleanCache;

    /**
     * 侧滑菜单
     */
    private SlidingMenu slidingMenu;


    /**
     * order List Adapter
     */
    private OrderListAdapter orderListAdapter;

    /**
     * 功能菜单适配器
     */
    private FunctionMenuListAdapter functionMenuAdapter;

    /**
     * 业务类型弹出层
     */
    private MenuFunctionDialog menuDialog;

    /**
     * 主要业务服务
     */
    private MainService mainService;

    /**
     * 订单数据
     */
    private List<Order> orderList;

    /**
     * 当前排号
     * KEY为functionMenu的id字符串形式，Value为当前排号
     */
    private HashMap<String, Integer> currentNumMap;

    /**
     * 菜单点击事件监听
     */
    private AdapterView.OnItemClickListener functionMenuItemListener;

    /**
     * 打印机广播接收器
     */
    private BroadcastReceiver mPrtReceiver;

    /**
     * 设备管理器
     * SDK中类
     */
    private DeviceManager deviceManager;

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

        initAnimtion();

    }

    private void initAnimtion() {
        AnimtionUtils.addClickAnimotionForView(tvSettings);
        AnimtionUtils.addClickAnimotionForView(tvCleanCache);
        AnimtionUtils.addClickAnimotionForView(tvDeveloper);

        AnimtionUtils.addTouchDrak(takeNoBtn, true);
    }


    private void init() {
        // 初始化数据
        mainService = new MainServiceImpl();
        mainService.initApplication();

        // 从文件中读取数据
        orderList = FileUtils.getObject(BizConstants.DATA_ORDER_LIST_IN_FILE, this);

        // 初始化当前排号
        currentNumMap = new HashMap<String, Integer>();


        if (orderList == null || orderList.size() == 0) {
            orderList = new ArrayList<Order>();
        } else { //加载各个菜单数据
            // TODO 当天数据判断，如果不是当前，就清空
            Order lastOrder = orderList.get(orderList.size() - 1);
            Date orderTime = lastOrder.getOrderTime();
            if (DateUtils.isToday(orderTime)) { // 是当天
                currentNumMap = FileUtils.getObject(BizConstants.DATA_CURRENT_NUM_MAP_IN_FILE, this);
                if (currentNumMap != null && currentNumMap.size() != 0) {
                    for (FunctionMenu fm : CSApplicationHolder.getFunctionMenuList()) {
                        try {
                            Integer currentNo = currentNumMap.get(String.valueOf(fm.getId()));
                            if (currentNo != null)
                                fm.setCurrentNo(currentNo);
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtils.d("没有对应的菜单ID");
                        } finally {
                            continue;
                        }
                    }
                }
            } else {
                cleanCache();
                LogUtils.d("clean:缓存数据非今天，清空");
            }

        }
        orderListAdapter = new OrderListAdapter(this, orderList);
        orderListView.setAdapter(orderListAdapter);

        // 初始化功能菜单
        functionMenuAdapter = new FunctionMenuListAdapter(this);

        // 初始化侧滑菜单
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindWidthRes(R.dimen.slidingmenu_width);
        View menuView = LayoutInflater.from(this).inflate(R.layout.slidingmenu, null);
        tvSettings = (TextView) menuView.findViewById(R.id.tv_settings);
        tvDeveloper = (TextView) menuView.findViewById(R.id.tv_developer);
        tvCleanCache = menuView.findViewById(R.id.tv_clean_cache);
        slidingMenu.setMenu(menuView);
        //slidingMenu.setMenu(R.layout.slidingmenu);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);


        mPrtReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int ret = intent.getIntExtra("ret", 0);
                if (ret == -1)
                    Toast.makeText(MainActivity.this, "打印机缺纸！", Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(PRNT_ACTION);
        registerReceiver(mPrtReceiver, filter);
        deviceManager = new DeviceManager();
        deviceManager.enableHomeKey(false);
    }

    /**
     * 清空缓存方法
     */
    private void cleanCache() {
        mainService.initApplication();
        currentNumMap.clear();
        orderList.clear();
        orderListAdapter.notifyDataSetChanged();
        // 清空数据
        FileUtils.deleteObject(BizConstants.DATA_CURRENT_NUM_MAP_IN_FILE, this);
        FileUtils.deleteObject(BizConstants.DATA_ORDER_LIST_IN_FILE, this);
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

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle(true);
            }
        });

        functionMenuItemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FunctionMenu fm = CSApplicationHolder.getFunctionMenuList().get(position);
                Integer fmId = fm.getId();
                Order order = mainService.takeNo(fmId);
                orderList.add(order);
                orderListAdapter.notifyDataSetChanged();
                currentNumMap.put(String.valueOf(fmId), fm.getCurrentNo());

                try {
                    boolean result = FileUtils.saveObject(BizConstants.DATA_ORDER_LIST_IN_FILE, (ArrayList) orderList, MainActivity.this);
                    boolean result2 = FileUtils.saveObject(BizConstants.DATA_CURRENT_NUM_MAP_IN_FILE, currentNumMap, MainActivity.this);
                    if (!(result && result2))
                        LogUtils.d("更新订单出错");
                } finally {
                    menuDialog.dismiss();
                }

            }
        };

        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "调用系统设置", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent();
                mIntent.setAction("android.settings.SETTINGS");
                startActivity(mIntent);

            }
        });

        tvCleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanCache();
            }
        });

        tvDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 这里添加开发者密码作为控制
                Intent mIntent = new Intent(Intent.ACTION_MAIN);
                mIntent.addCategory(Intent.CATEGORY_HOME);
                try {
                    startActivity(mIntent);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "没有找到相应的启动器", Toast.LENGTH_SHORT).show();
                }
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
        showFunctionMenuDialogFragment();

    }

    private void showFunctionMenuDialogFragment() {
        if (menuDialog == null)
            menuDialog = new MenuFunctionDialog();
        menuDialog.setAdapter(functionMenuAdapter);
        menuDialog.setItemClickListener(functionMenuItemListener);
        menuDialog.show(getFragmentManager(), "menuDialog");
    }

    /**
     * 显示类型弹出层
     * 由于业务改变，此方法已过时，
     */
    @Deprecated
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
    public void onBackPressed() {
        // 禁用back键
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(MainActivity.this, "main activity destroy", Toast.LENGTH_SHORT).show();

        //Intent mIntent = new Intent();
        //mIntent.setClass(this, MainActivity.class);
        //startActivity(mIntent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PRNT_ACTION);
        registerReceiver(mPrtReceiver, filter);
        deviceManager.enableHomeKey(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mPrtReceiver);
        deviceManager.enableHomeKey(true);
    }

}
