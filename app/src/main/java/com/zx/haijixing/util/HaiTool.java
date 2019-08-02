package com.zx.haijixing.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.PixelCopy;
import android.view.View;
import android.widget.TextView;

import com.allen.library.utils.MD5;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.PrintAdapter;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxStringUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/8 9:24
 *@描述 工具类
 */
public final class HaiTool {
    /**
     * 获取应用版本名称
     * @param context
     * @return
     */
    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
    /**
     * 获取应用版本名称
     * @param context
     * @return
     */
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String md5Method(String word){
        String resultString = null;
        try {
            resultString = new String(word);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes("utf-8")));

        } catch (Exception exception) {
        }
        return resultString;
    }
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    //字节数组转字符串
    public static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }
    //字节转字符
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    private static final String TEL_REGEX = "^[1][3-9][0-9]{9}$";
    private static final String IDENTIFY_CARD = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    private static final String BANK_CARD = "^([1-9]{1})(\\d{14}|\\d{18})$";
    public static boolean checkPhone(String phone){
        return phone.matches(TEL_REGEX);
    }
    public static boolean checkIdentify(String identify){
        return identify.matches(IDENTIFY_CARD);
    }
    public static boolean checkBank(String bank){
        return bank.matches(BANK_CARD);
    }
    /**
     * 将dp转换成px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources ().getDisplayMetrics ().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String calculateTime(long timeStamp){
        long day = timeStamp / (1000 * 60 * 60 * 24);
        long hour = (timeStamp / (1000 * 60 * 60) - day * 24);
        long min = ((timeStamp / (60 * 1000)) - day * 24 * 60 - hour * 60);
        String timeStr = "("+day + "天" + hour + "时" + min + "分)";
        return  timeStr;
    }

    /**
     * recycleview截图
     * @param view
     * @return
     */
    public static Bitmap shotRecyclerView(RecyclerView view,int i) {
        RecyclerView.Adapter printAdapter = view.getAdapter();
        PrintAdapter adapter = (PrintAdapter) printAdapter;
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;

            RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
            adapter.setPrint(true);
            adapter.onBindViewHolder(holder, i);
            holder.itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                    holder.itemView.getMeasuredHeight());

            holder.itemView.setDrawingCacheEnabled(true);
            holder.itemView.buildDrawingCache();
            Bitmap drawingCache = holder.itemView.getDrawingCache();

            height = holder.itemView.getMeasuredHeight();
            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawBitmap(drawingCache, 0f, iHeight, paint);

        }
        return bigBitmap;
    }


    public static TimePickerView initTimePickers(Context context, TextView view1, TextView view2) {
        //选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        if (view1 != null)
            view1.setText(getTime(curDate));
        if (view2 != null)
            view2.setText(getTime(curDate));

        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);


        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);

        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);


        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year_int+10, mouth_int - 1, day_int);

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, (date, v) -> view1.setText(getTime(date)))
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(context.getResources().getColor(R.color.color_6666))
                .setTextColorCenter(context.getResources().getColor(R.color.color_3333))//设置选中项的颜色
                .setTextColorOut(context.getResources().getColor(R.color.color_9999))//设置没有被选中项的颜色
                .setDate(selectedDate)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(-10, 0,10, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

        return pvTime;

    }

    public static TimePickerView initTimeHourMin(Context context, TextView view1, TextView view2) {
        //选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        if (view1 != null)
            view1.setText(getHourMin(curDate));
        if (view2 != null)
            view2.setText(getHourMin(curDate));


        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(0, 0, 0,0,0);
        Calendar endDate = Calendar.getInstance();
        endDate.set(0, 0, 0,24,59);

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, (date, v) -> view1.setText(getHourMin(date)))
                .setType(new boolean[]{false, false, false, true, true, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("", "", "", "点", "分", "")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(context.getResources().getColor(R.color.color_6666))
                .setTextColorCenter(context.getResources().getColor(R.color.color_3333))//设置选中项的颜色
                .setTextColorOut(context.getResources().getColor(R.color.color_9999))//设置没有被选中项的颜色
                .setDate(selectedDate)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(-10, 0,10, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

        return pvTime;

    }
    private static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private static String getHourMin(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    /**
     * 强制帮用户打开GPS
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;
    //禁止连续点击
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) <= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
