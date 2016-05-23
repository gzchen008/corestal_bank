package com.corestal.wpos.bank.service.impl;

import com.corestal.wpos.bank.biz.constant.BizConstants;
import com.corestal.wpos.bank.biz.entity.FunctionMenu;
import com.corestal.wpos.bank.biz.entity.Order;
import com.corestal.wpos.bank.common.CSApplicationHolder;
import com.corestal.wpos.bank.service.MainService;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by CGZ on 2016/5/11.
 */
public class MainServiceImpl implements MainService {
    @Override
    public void initApplication() {
        // TODO 这里初始化应该是从属性文件中取出数据
        // TODO 此处为了测试直接赋值
        CSApplicationHolder.setStartNo(0);
        CSApplicationHolder.setStationCount(6);

        List<FunctionMenu> functionMenuList = new ArrayList<FunctionMenu>();
        FunctionMenu functionMenu;

        functionMenu = new FunctionMenu();
        functionMenu.setCode("A");
        functionMenu.setId(1);
        functionMenu.setName("个人业务");
        functionMenu.setStatus(BizConstants.FUNCTION_MENU_STATUS_USE);
        functionMenuList.add(functionMenu);

        functionMenu = new FunctionMenu();
        functionMenu.setCode("B");
        functionMenu.setId(2);
        functionMenu.setName("对公业务");
        functionMenu.setStatus(BizConstants.FUNCTION_MENU_STATUS_USE);
        functionMenuList.add(functionMenu);

        functionMenu = new FunctionMenu();
        functionMenu.setCode("C");
        functionMenu.setId(3);
        functionMenu.setName("综合业务");
        functionMenu.setStatus(BizConstants.FUNCTION_MENU_STATUS_USE);
        functionMenuList.add(functionMenu);

        functionMenu = new FunctionMenu();
        functionMenu.setCode("V");
        functionMenu.setId(4);
        functionMenu.setName("贵宾业务");
        functionMenu.setStatus(BizConstants.FUNCTION_MENU_STATUS_USE);
        functionMenuList.add(functionMenu);


        CSApplicationHolder.setFunctionMenuList(functionMenuList);
        List<String> nameList = new ArrayList<String>();
        for(FunctionMenu fm : functionMenuList){
            nameList.add(fm.getName());
        }
        CSApplicationHolder.setFunctionMenuNames(nameList.toArray(new String[nameList.size()]));
    }

    @Override
    public Order takeNo(Integer functionMenuId) {

        FunctionMenu functionMenu = null;
        try {
            functionMenu = CSApplicationHolder.getFunctionMenu(functionMenuId);
        } catch (Exception e) {
            LogUtils.d(e.getMessage());
            return null;
        }

        Order order = new Order();
        order.setFunctionMenu(functionMenu);
        // code 和排队号的组合
        order.setOrderNum(functionMenu.getCode() + CSApplicationHolder.getNextNoStr());
        order.setOrderTime(new Date());
        order.setWorkStatus(BizConstants.ORDER_STATUS_WAITING);

        return order;
    }

    @Override
    public void print(Order order) {

    }

    @Override
    public void done(Order order) {

    }

    @Override
    public void cancle(Order order) {

    }
}
