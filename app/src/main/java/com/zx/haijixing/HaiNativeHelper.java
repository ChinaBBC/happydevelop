package com.zx.haijixing;
/**
 *
 *@作者 zx
 *@创建日期 2019/6/14 11:59
 *@描述 native
 */
public final class HaiNativeHelper {
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native String baseUrl();
}
