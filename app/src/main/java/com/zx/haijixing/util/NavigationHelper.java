package com.zx.haijixing.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 *
 *@作者 zx
 *@创建日期 2019/5/6 13:33
 *@描述 跳转
 */
public final class NavigationHelper {
    public static final String PARAM_1 = "param_1";
    public static final String PARAM_2 = "param_2";
    public static final String PARAM_3 = "param_3";
    public static final String OBJ_1 = "obj_1";
    public static final String OBJ_2 = "obj_2";
    public static final int REQUEST_CODE_1 = 0x22;
    public static final int REQUEST_CODE_2 = 0x33;

    private static final String[] PARAMS = {PARAM_1, PARAM_2, PARAM_3};
    private static final String[] OBJECTS = {OBJ_1, OBJ_2};

    public static void navigate(@NonNull Activity activity, Class toActivity) {
        activity.startActivity(new Intent(activity, toActivity));
    }

    public static void navigate(@NonNull Activity activity, Class toActivity, @NonNull Object... params) {
        Intent intent = new Intent(activity, toActivity);
        activity.startActivity(assembleIntentWithParam(intent, params));
    }

    public static void navigateForResult(int requestCode, @NonNull Activity activity, Class toActivity) {
        Intent intent = new Intent(activity, toActivity);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void navigateForResult(int requestCode, @NonNull Activity activity, Class toActivity, @NonNull Object... params) {
        Intent intent = new Intent(activity, toActivity);
        activity.startActivityForResult(assembleIntentWithParam(intent, params), requestCode);
    }

    private static Intent assembleIntentWithParam(Intent intent, @NonNull Object... params) {
        int p_i = 0;
        int o_i = 0;

        for (Object obj : params) {
            if (obj instanceof Integer) {
                intent.putExtra(PARAMS[p_i], (int) obj);
            } else if (obj instanceof Boolean) {
                intent.putExtra(PARAMS[p_i], (boolean) obj);
            } else if (obj instanceof Float) {
                intent.putExtra(PARAMS[p_i], (float) obj);
            } else if (obj instanceof Double) {
                intent.putExtra(PARAMS[p_i], (double) obj);
            } else if (obj instanceof String) {
                intent.putExtra(PARAMS[p_i], (String) obj);
            } else if (obj instanceof Long) {
                intent.putExtra(PARAMS[p_i], (long) obj);
            } else if (obj instanceof Parcelable) {
                intent.putExtra(OBJECTS[o_i], (Parcelable) obj);
            } else if (obj instanceof Serializable) {
                intent.putExtra(OBJECTS[o_i], (Serializable) obj);
            }

            if (obj instanceof Integer || obj instanceof Boolean || obj instanceof Float
                    || obj instanceof Double || obj instanceof String || obj instanceof Long) {
                p_i++;
            } else if (obj instanceof Parcelable || obj instanceof Serializable) {
                o_i++;
            }
        }

        return intent;
    }

    public static Bundle assembleFragmentArgs(Bundle bundle, @NonNull Object... params) {
        int p_i = 0;
        int o_i = 0;

        for (Object obj : params) {
            if (obj instanceof Integer) {
                bundle.putInt(PARAMS[p_i], (int) obj);
            } else if (obj instanceof Boolean) {
                bundle.putBoolean(PARAMS[p_i], (boolean) obj);
            } else if (obj instanceof Float) {
                bundle.putFloat(PARAMS[p_i], (float) obj);
            } else if (obj instanceof Double) {
                bundle.putDouble(PARAMS[p_i], (double) obj);
            } else if (obj instanceof String) {
                bundle.putString(PARAMS[p_i], (String) obj);
            } else if (obj instanceof Long) {
                bundle.putLong(PARAMS[p_i], (long) obj);
            } else if (obj instanceof Parcelable) {
                bundle.putParcelable(OBJECTS[o_i], (Parcelable) obj);
            } else if (obj instanceof Serializable) {
                bundle.putSerializable(OBJECTS[o_i], (Serializable) obj);
            }

            if (obj instanceof Integer || obj instanceof Boolean || obj instanceof Float
                    || obj instanceof Double || obj instanceof String || obj instanceof Long) {
                p_i++;
            } else if (obj instanceof Parcelable || obj instanceof Serializable) {
                o_i++;
            }
        }

        return bundle;
    }
}
