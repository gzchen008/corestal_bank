package com.corestal.wpos.bank.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.corestal.wpos.bank.R;
import com.corestal.wpos.bank.biz.entity.Order;
import com.corestal.wpos.bank.common.CSApplicationHolder;
import com.corestal.wpos.bank.utils.DateUtils;
import com.corestal.wpos.bank.view.component.ListViewItemButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.weipass.pos.sdk.IPrint;
import cn.weipass.pos.sdk.Printer;
import cn.weipass.pos.sdk.Weipos;
import cn.weipass.pos.sdk.impl.WeiposImpl;


/**
 * Created by cgz on 16-5-13.
 * 订单列表适配器
 */
public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private OrderItemButtonOnClickListener orderItemButtonOnClickListener;
    /**
     * 订单列表数据
     */
    private List<Order> orderList;

    public OrderListAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        this.layoutInflater = LayoutInflater.from(context);
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
        viewHolder.orderTimeTextView.setText("时间:" + DateUtils.getDate(orderList.get(position).getOrderTime(), DateUtils.HHMMSS));

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

            Weipos weiposImpl = WeiposImpl.as();
            if (weiposImpl == null) {
                LogUtils.d("weipos sdk出错");
            } else {
                Printer printer = weiposImpl.openPrinter();
                if (printer != null) { // 在非wangpos下会是null
                    printer.printText(
                            "欢迎光临\n" +
                                    "---------------------------\n" +
                                    "排队单号：" + order.getOrderNum() + "\n取票时间:" + DateUtils.getDate(order.getOrderTime(),DateUtils.YYYYMMDDHHMM) +
                                    "\n业务类型:" + order.getFunctionMenu().getName() + "\n请耐心等待，我们会尽快安排！" +
                                    "---------------------------\n" +"\n\n\n\n",
                            Printer.FontFamily.SONG, Printer.FontSize.MEDIUM,
                            Printer.FontStyle.NORMAL, Printer.Gravity.CENTER);
                }
            }
        }

        private void btnCancleClick(int index) {
            // TODO 目前完成订单只是将元素删掉
            orderList.remove(index);
            OrderListAdapter.this.notifyDataSetChanged();
        }

        private void btnDoneClick(int position) {
            // TODO 目前完成订单只是将元素删掉
            orderList.remove(position);
            OrderListAdapter.this.notifyDataSetChanged();
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
                    WeiposImpl.as().speech(voice);
                }
            });
            dialogBuilder.create().show();
        }
    }

}
