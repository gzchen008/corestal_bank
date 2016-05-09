package com.corestal.wpos.bank.biz.entity;

/**
 * Created by cgz on 16-5-10.
 * 功能菜单
 */
public class FunctionMenu {
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
    private Byte status;

    /**
     * 图标名
     */
    private String icon;

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
}
