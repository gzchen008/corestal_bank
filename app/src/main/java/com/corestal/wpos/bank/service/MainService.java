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
    public String takeNo(FunctionMenu functionMenu);

    public void print(Order order);

    public void done(Order order);
}
