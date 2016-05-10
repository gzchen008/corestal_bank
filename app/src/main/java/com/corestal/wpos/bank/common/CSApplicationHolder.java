package com.corestal.wpos.bank.common;

import com.corestal.wpos.bank.biz.entity.FunctionMenu;

import java.util.List;

/**
 * Created by cgz on 16-5-10.
 * 全局缓存信息
 */
public class CSApplicationHolder {
    /**
     * 起始排号
     * 默认为0
     */
    private static Integer startNo = 0;

    /**
     * 当前排号
     */
    private static Integer currNo;

    /**
     * 功能列表
     */
    private static List<FunctionMenu> functionMenuList;

    /**
     * 窗口数量
     */
    private static Integer stationCount;


    /**
     * 取得起始排号
     *
     * @return
     */
    public static Integer getStartNo() {
        return startNo;
    }

    /**
     * 设置起始排号
     *
     * @param startNo
     */
    public synchronized static void setStartNo(Integer startNo) {
        CSApplicationHolder.startNo = startNo;
        // 重置当前排号
        CSApplicationHolder.currNo = startNo;
    }

    public static Integer getCurrNo() {
        return currNo;
    }


    /**
     * 获取下一个排号
     *
     * @return 下一个排号，字符吕形式
     */
    public static String getNextNoStr() {
        if (currNo == null)
            currNo = startNo;

        synchronized (currNo) {
            return String.format("%07d", ++currNo);
        }
    }

    public static List<FunctionMenu> getFunctionMenuList() {
        return functionMenuList;
    }

    public static void setFunctionMenuList(List<FunctionMenu> functionMenuList) {
        CSApplicationHolder.functionMenuList = functionMenuList;
    }

    public static Integer getStationCount() {
        return stationCount;
    }

    public static void setStationCount(Integer stationCount) {
        CSApplicationHolder.stationCount = stationCount;
    }
}
