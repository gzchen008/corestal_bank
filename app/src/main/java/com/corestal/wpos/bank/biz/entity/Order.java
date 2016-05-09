package com.corestal.wpos.bank.biz.entity;

/**
 * Created by cgz on 16-5-10.
 * 排队订单
 */
public class Order {
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
}
