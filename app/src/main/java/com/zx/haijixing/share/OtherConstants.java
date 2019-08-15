package com.zx.haijixing.share;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/17 16:27
 *@描述 其他常量
 */
public final class OtherConstants {
    public final static String LOGIN_DRIVER = "4";//司机
    public final static String LOGIN_LOGISTICS = "3";//物流
    public final static String LOGIN_MANAGER = "2";//管理员
    public final static String LOGIN_OUT = "exit";//退出
    public final static String CANCEL_REQUEST = "cancel";//取消请求
    public final static String WEATHER_API = "https://www.tianqiapi.com/";//取消请求
    public final static String UPLOAD_PATH = "open/upload/files/uploadImages";//取消请求

    public final static String PAGE = "num";//页数
    public final static String SIZE = "size";//条数


    public final static int LOAD_ALLOT = 1;//派单
    public final static int LOAD_MOVE = 0;//物流动态\

    public final static int DETAIL_WAIT_ALLOT = 0;//待派单
    public final static int DETAIL_WAIT_RECEIVE = 1;//待接单
    public final static int DETAIL_WAIT_SEND = 2;//待出发
    public final static int DETAIL_SENDING = 3;//配送中
    public final static int DETAIL_COMPLETE = 4;//已完成
    public final static int DETAIL_CANCEL = 5;//已取消

    public final static int SURE_RECEIVE = 6;//确认收款
    public final static int CHANGE_ORDER = 7;//修改订单


    public final static int EVENT_RECEIVE = 1001;//接单
    public final static int EVENT_PRINT = 1002;//打单
    public final static int EVENT_LOGISTICS = 1000;//打单

    public final static int UPLOAD_DRIVER_A = 1003;//驾驶证正面
    public final static int UPLOAD_DRIVER_B = 1004;//驾驶证副页
    public final static int UPLOAD_DRIVING_A = 1005;//行驶证正面
    public final static int UPLOAD_DRIVING_B = 1006;//行驶证副页
    public final static int UPLOAD_CURE = 1007;//保单
    public final static int UPLOAD_TRUCK_A = 1008;//车正面
    public final static int UPLOAD_TRUCK_B = 1009;//车左面
    public final static int UPLOAD_TRUCK_C = 1010;//车右面
    public final static int UPLOAD_OTHER = 1011;//其他
    public final static int UPLOAD_HEAD = 1012;//头像

    public final static int CHANGE_DRIVER_ID = 1013;//修改驾驶证
    public final static int CHANGE_TRUCK_ID = 1014;//修改行驶证
    public final static int CHANGE_CURE_ID = 1015;//修改保单
    public final static int CHANGE_TRUCK_IMG = 1016;//修改车辆照片
    public final static int CHANGE_OTHER = 1017;//其他

    public final static int SET_ALIAS = 1018;//设置标签
    public final static int PERMISSION_REQUEST = 1019;//权限请求
    public final static int WEATHER_ENTRY = 1020;//event 天气
    public final static int READ_QR = 1021;//识别二维码

    public final static int RED_BOT = 1022;//红点更新
    public final static int ALLOT_REQUEST = 1023;//派单


    public final static int SELECT_DRIVER = 0;//选择司机
    public final static int SELECT_TRUCK = 1;//选择车辆

    public final static int CLASS_DELETE = 2;//删除班次

    public final static int REQUEST_ADD_CLASS = 1022;//新增的onActivityResult回调



    public final static long TIME_ONE_DAY = 24*60*60*1000;//24小时的毫秒数



}
