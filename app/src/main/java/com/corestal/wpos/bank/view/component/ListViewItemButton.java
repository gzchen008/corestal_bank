package com.corestal.wpos.bank.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.corestal.wpos.bank.biz.entity.Order;

/**
 * Created by cgz on 16-5-14.
 * 在ListView中的Button
 */
public class ListViewItemButton extends ImageButton {
    private int index;
    private Order order;

    public ListViewItemButton(Context context) {
        super(context);
    }

    public ListViewItemButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewItemButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


}
