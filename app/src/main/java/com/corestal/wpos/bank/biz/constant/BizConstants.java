package com.corestal.wpos.bank.biz.constant;

/**
 * Created by cgz on 16-5-10.
 * 业务常量
 */
public class BizConstants {


    ////////////////////
    /**
     *  功能菜单状态：使用
     *
     */

    public static Byte FUNCTION_MENU_STATUS_USE  = 0;

    /**
     *  功能菜单状态：停用
     *
     */

    public static Byte FUNCTION_MENU_STATUS_DISABLE  = -1;


    ////////////////////
    /**
     * 等待处理
     */
    public static Byte ORDER_STATUS_WAITING = -1;

    /**
     * 处理中
     */
    public static Byte ORDER_STATUS_WORKING = 0 ;

    /**
     * 完成
     */
    public static Byte ORDER_STATUS_FINISHED = 1 ;
}
