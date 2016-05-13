package com.corestal.wpos.bank.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.corestal.wpos.bank.R;
import com.corestal.wpos.bank.biz.entity.Order;
import com.corestal.wpos.bank.utils.DateUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by cgz on 16-5-13.
 * 订单列表适配器
 */
public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    /**
     * 订单列表数据
     */
    private List<Order> orderList;

    public OrderListAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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


        viewHolder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 目前完成订单只是将元素删掉
                orderList.remove(position);
                OrderListAdapter.this.notifyDataSetChanged();
            }
        });

        viewHolder.cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 目前完成订单只是将元素删掉
                orderList.remove(position);
                OrderListAdapter.this.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        @ViewInject(R.id.tv_order_base_info_order_no)
        public TextView orderNoTextView;

        @ViewInject(R.id.tv_order_base_info_order_time)
        public TextView orderTimeTextView;

        @ViewInject(R.id.btn_oper_done)
        public Button doneButton;

        @ViewInject(R.id.btn_oper_cancle)
        public Button cancleButton;

    }
}
