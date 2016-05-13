package com.corestal.wpos.bank.service;

import com.corestal.wpos.bank.biz.constant.BizConstants;
import com.corestal.wpos.bank.biz.entity.FunctionMenu;
import com.corestal.wpos.bank.biz.entity.Order;
import com.corestal.wpos.bank.common.CSApplicationHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgz on 16-5-11.
 * MainActivityService
 * 主要服务
 */
public interface MainService {
    /**
     * 初始化数据
     */
    public void initApplication();

    /**
     * 取号操作
     * @return
     */
    public Order takeNo(Integer functionMenuId);

    /**
     * 打印小票
     * @param order
     */
    public void print(Order order);

    /**
     * 办理
     * @param order
     */
    public void done(Order order);

    /**
     *
     * 过号，取消排号
     * @param order
     */
    public void cancle(Order order);
}
