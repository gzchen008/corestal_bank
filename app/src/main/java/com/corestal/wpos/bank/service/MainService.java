package com.corestal.wpos.bank.service;

import com.corestal.wpos.bank.biz.constant.BizConstants;
import com.corestal.wpos.bank.biz.entity.FunctionMenu;
import com.corestal.wpos.bank.common.CSApplicationHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgz on 16-5-11.
 * MainActivityService
 * 主要服务
 */
public class MainService {
    public void initApplication(){
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
        functionMenu.setId(1);
        functionMenu.setName("对公业务");
        functionMenu.setStatus(BizConstants.FUNCTION_MENU_STATUS_USE);
        functionMenuList.add(functionMenu);

        functionMenu = new FunctionMenu();
        functionMenu.setCode("C");
        functionMenu.setId(1);
        functionMenu.setName("综合业务");
        functionMenu.setStatus(BizConstants.FUNCTION_MENU_STATUS_USE);
        functionMenuList.add(functionMenu);

        CSApplicationHolder.setFunctionMenuList(functionMenuList);
    }
}
