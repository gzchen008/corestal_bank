package com.corestal.wpos.bank.view.adapter;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.device.PrinterManager;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.corestal.wpos.bank.R;
import com.corestal.wpos.bank.android.service.PrintBillService;
import com.corestal.wpos.bank.android.service.TextToSpeechService;
import com.corestal.wpos.bank.biz.constant.BizConstants;
import com.corestal.wpos.bank.biz.entity.Order;
import com.corestal.wpos.bank.common.CSApplicationHolder;
import com.corestal.wpos.bank.utils.AnimtionUtils;
import com.corestal.wpos.bank.utils.DateUtils;
import com.corestal.wpos.bank.utils.FileUtils;
import com.corestal.wpos.bank.view.component.ListViewItemButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cgz on 16-5-13.
 * 订单列表适配器
 */
public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private OrderItemButtonOnClickListener orderItemButtonOnClickListener;
    private TextToSpeechService speechService;
    private PrinterManager printer;

    /**
     * 订单列表数据
     */
    private List<Order> orderList;

    public OrderListAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        this.layoutInflater = LayoutInflater.from(context);
        this.speechService = new TextToSpeechService(context);
        this.printer = new PrinterManager();
    }


    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (orderList.size() == 0)
            return null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.order_list_item, null);
            viewHolder = new ViewHolder();

            ViewUtils.inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String orderNum = orderList.get(position).getOrderNum() + "(" + orderList.get(position).getFunctionMenu().getName() + ")";
        viewHolder.orderNoTextView.setText(orderNum);
        viewHolder.orderTimeTextView.setText(DateUtils.getDate(orderList.get(position).getOrderTime(), DateUtils.MMDDHHMM));

        // 设置listview的索引
        viewHolder.palyButton.setIndex(position);
        viewHolder.cancleButton.setIndex(position);
        viewHolder.printButton.setIndex(position);
        viewHolder.doneButton.setIndex(position);

        viewHolder.palyButton.setOrder(orderList.get(position));
        viewHolder.cancleButton.setOrder(orderList.get(position));
        viewHolder.printButton.setOrder(orderList.get(position));
        viewHolder.doneButton.setOrder(orderList.get(position));


        // 事件监听
        if (orderItemButtonOnClickListener == null) {
            orderItemButtonOnClickListener = new OrderItemButtonOnClickListener();
        }
        viewHolder.palyButton.setOnClickListener(orderItemButtonOnClickListener);
        viewHolder.cancleButton.setOnClickListener(orderItemButtonOnClickListener);
        viewHolder.printButton.setOnClickListener(orderItemButtonOnClickListener);
        viewHolder.doneButton.setOnClickListener(orderItemButtonOnClickListener);

        AnimtionUtils.addTouchDrak(viewHolder.palyButton, true);
        AnimtionUtils.addTouchDrak(viewHolder.cancleButton, true);
        AnimtionUtils.addTouchDrak(viewHolder.printButton, true);
        AnimtionUtils.addTouchDrak(viewHolder.doneButton, true);
        return convertView;
    }

    public static class ViewHolder {

        @ViewInject(R.id.tv_order_base_info_order_no)
        public TextView orderNoTextView;

        @ViewInject(R.id.tv_order_base_info_order_time)
        public TextView orderTimeTextView;

        @ViewInject(R.id.btn_oper_done)
        public ListViewItemButton doneButton;

        @ViewInject(R.id.btn_oper_cancle)
        public ListViewItemButton cancleButton;

        @ViewInject(R.id.btn_oper_play)
        public ListViewItemButton palyButton;

        @ViewInject(R.id.btn_oper_print)
        public ListViewItemButton printButton;

    }

    private class OrderItemButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ListViewItemButton button = (ListViewItemButton) v;
            switch (v.getId()) {
                case R.id.btn_oper_play:
                    LogUtils.d("play");
                    btnPalyClick(button.getOrder(), v.getContext());
                    break;
                case R.id.btn_oper_done:
                    LogUtils.d("done");
                    btnDoneClick(button.getIndex());
                    break;
                case R.id.btn_oper_cancle:
                    LogUtils.d("cancle");
                    btnCancleClick(button.getIndex());
                    break;
                case R.id.btn_oper_print:
                    LogUtils.d("print");
                    btnPrintClick(button.getIndex());
                    break;

            }

        }

        private void btnPrintClick(int index) {
            Toast.makeText(context, "打印", Toast.LENGTH_SHORT).show();

            Order order = orderList.get(index);

            doPrintWork(order);
        }
    }


    private void btnCancleClick(int index) {
        // TODO 目前完成订单只是将元素删掉
        orderList.remove(index);
        updateInFile();
    }

    private void btnDoneClick(int position) {
        // TODO 目前完成订单只是将元素删掉
        orderList.remove(position);
        updateInFile();
    }

    private void btnPalyClick(final Order order, final Context context) {
        int stationCount = CSApplicationHolder.getStationCount();
        LogUtils.d("窗口数：" + stationCount);
        final String[] stationNames = new String[stationCount];
        for (int i = 0; i < stationCount; i++) {
            stationNames[i] = "第" + (i + 1) + "号窗口";
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context).setTitle(R.string.station_name).setItems(stationNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                order.setStationNum(which + 1);
                // TODO
                String voice = "请" + order.getOrderNum() + "到" + stationNames[which] + "办理业务";
                Toast.makeText(context, "播放音频:" + voice, Toast.LENGTH_SHORT).show();
                speechService.notifyNewMessage(voice);
            }
        });
        dialogBuilder.create().show();
    }


    private void doPrintWork(Order order) {
        if (order == null) return;

        printer.setupPage(384, -1);
        int ret = printer.drawTextEx(order.getOrderNum() + "\n", 130, 0, 300, -1, "arial", 50, 0, 0, 0);
        String printStr =
                "\n取号时间:" + DateUtils.getDate(order.getOrderTime(), DateUtils.YYYYMMDDHHMM) +
                        "\n业务类型:" + order.getFunctionMenu().getName() + "\n"
                        + "前面有" + CSApplicationHolder.getWaitCount(order, orderList) + "人等侯\n";

        ret = printer.drawTextEx(printStr, 5, 50, 384, -1, "arial", 30, 0, 0, 0);
        ret = printer.drawTextEx("\n------------------------------------------\n请耐心等待，我们会尽快安排！\n\n\n\n\n\n", 5, 180, 384, -1, "arial", 24, 0, 0, 0);


        android.util.Log.i("debug", "ret:" + ret);
        //ret += printer.drawTextEx(context, 5, ret,300,-1, "arial", 25, 0, 0x0001, 1);
        //ret += printer.drawTextEx(context, 5, ret,-1,-1, "arial", 25, 0, 0x0008, 0);
        //ret +=printer.drawTextEx(context, 300, ret,-1,-1, "arial", 25, 1, 0, 0);
        //ret +=printer.drawTextEx(context, 0, ret,-1,-1, "/system/fonts/DroidSans-Bold.ttf", 25, 0, 0, 0);
        //ret +=printer.drawTextEx(context, 0, ret,-1,-1, "/system/fonts/kaishu.ttf", 25, 0, 0x0001, 0);
        android.util.Log.i("debug", "ret:" + ret);
        //printer.drawTextEx(context, 5, 60,160,-1, "arial", 25, 0, 0x0001 |0x0008, 0);
        //printer.drawTextEx(context, 180, 0,160,-1, "arial", 25, 1, 0x0008, 0);
        //printer.drawTextEx(context, 300, 30,160,-1, "arial", 25, 2, 0x0008, 0);
        //printer.drawTextEx(context, 300, 160,160,-1, "arial", 25, 3, 0x0008, 0);
        //printer.drawTextEx(context, 0, 0,160,-1, "arial", 25, 1, 0x0008, 0);
        //printer.drawTextEx(context, 160, 30,200,-1, "arial", 28, 0, 2,1);
        //printer.drawTextEx(context, 0, 180,-1,-1, "arial", 28, 0, 2,1);
        ret = printer.printPage(0);

        Intent i = new Intent("android.prnt.message");
        i.putExtra("ret", ret);
        context.sendBroadcast(i);
    }

    private void updateInFile() {
        OrderListAdapter.this.notifyDataSetChanged();
        boolean result1 = FileUtils.saveObject(BizConstants.DATA_ORDER_LIST_IN_FILE, (ArrayList) orderList, context);
        if (!result1)
            LogUtils.d("更新订单出错");
    }

}
