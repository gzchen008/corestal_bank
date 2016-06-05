package com.corestal.wpos.bank.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.corestal.wpos.bank.R;
import com.corestal.wpos.bank.biz.entity.FunctionMenu;
import com.corestal.wpos.bank.biz.entity.Order;
import com.corestal.wpos.bank.common.CSApplicationHolder;
import com.corestal.wpos.bank.view.component.ListViewItemButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by cgz on 16-5-28.
 */
public class FunctionMenuListAdapter extends BaseAdapter {

    private List<FunctionMenu> datas;
    private Context context;
    private LayoutInflater layoutInflater;

    public FunctionMenuListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = CSApplicationHolder.getFunctionMenuList();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.function_menu_list_item, null);
            viewHolder = new ViewHolder();

            ViewUtils.inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.functionMenuTv.setText(datas.get(position).getName());
        viewHolder.functionMenuIv.setImageResource(datas.get(position).getIconRes());
        return convertView;
    }

    public static class ViewHolder {

        @ViewInject(R.id.iv_function_menu)
        public ImageView functionMenuIv;

        @ViewInject(R.id.tv_function_menu_title)
        public TextView functionMenuTv;

    }
}
