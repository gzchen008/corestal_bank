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

    /////////////////////////
    /**
     * 文件中的订单列表
     */
    public static String DATA_ORDER_LIST_IN_FILE = "order_list_in_file.data";

    /**
     * 文件中的当前排号
     */
    public static final String DATA_CURRENT_NUM_MAP_IN_FILE = "current_num_map_in_file.data";
}
