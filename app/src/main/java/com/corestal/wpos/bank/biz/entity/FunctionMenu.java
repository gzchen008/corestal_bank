package com.corestal.wpos.bank.biz.entity;

import com.corestal.wpos.bank.biz.constant.BizConstants;

import java.io.Serializable;

/**
 * Created by cgz on 16-5-10.
 * 功能菜单
 */
public class FunctionMenu implements Serializable{

    private static final long serialVersionUID = -316102412618444934L;

    /**
     * 功能编号
     */
    private Integer id;

    /**
     * 功能名称
     */
    private String name;

    /**
     * 菜单状态
     */
    private Byte status = BizConstants.FUNCTION_MENU_STATUS_USE;

    /**
     * 图标名
     */
    private String icon;

    /**
     * 资源中的图片
     */
    private int iconRes;

    /**
     * 功能编码
     */
    private String code;

    /**
     * 当前排号
     */
    private Integer currentNo = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCurrentNo() {
        return currentNo;
    }

    public void setCurrentNo(Integer currentNo) {
        this.currentNo = currentNo;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
