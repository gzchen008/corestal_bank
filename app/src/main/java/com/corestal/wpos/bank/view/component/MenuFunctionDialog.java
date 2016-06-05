package com.corestal.wpos.bank.view.component;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.corestal.wpos.bank.R;
import com.corestal.wpos.bank.biz.entity.Order;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

/**
 * Created by cgz on 16-5-28.
 * 弹出菜单
 */
public class MenuFunctionDialog extends DialogFragment {
    private BaseAdapter adapter;
    private AdapterView.OnItemClickListener itemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.function_menu_dialog, container);
        GridView gridView = (GridView) view.findViewById(R.id.gv_function_menu);
        gridView.setAdapter(adapter);
        getDialog().setTitle("业务类型");
        gridView.setOnItemClickListener(itemClickListener);
        return view;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
