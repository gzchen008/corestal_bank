package com.corestal.wpos.bank.biz.entity;

import com.lidroid.xutils.db.annotation.Id;

import java.util.Date;

/**
 * Created by cgz on 16-5-10.
 * 排队订单
 */
public class Order {

    @Id
    private Integer id;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 工作状态
     */
    private Byte workStatus;

    /**
     * 窗口号
     */
    private Integer stationNum;

    /**
     * 取号时间
     */
    private Date orderTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 业务类型
     */
    private FunctionMenu functionMenu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getStationNum() {
        return stationNum;
    }

    public void setStationNum(Integer stationNum) {
        this.stationNum = stationNum;
    }

    public Byte getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Byte workStatus) {
        this.workStatus = workStatus;
    }

    public FunctionMenu getFunctionMenu() {
        return functionMenu;
    }

    public void setFunctionMenu(FunctionMenu functionMenu) {
        this.functionMenu = functionMenu;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
