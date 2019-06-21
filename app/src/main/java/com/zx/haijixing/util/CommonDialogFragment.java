package com.zx.haijixing.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


public class CommonDialogFragment extends DialogFragment {

    /**
     * 监听弹出窗是否被取消
     */
    private OnDialogCancelListener mCancelListener;

    /**
     * 回调获得需要显示的 dialog
     */
    private OnCallDialog mOnCallDialog;
    /**
     * 是否显示在底部
     */
    private boolean isBottom;

    public void setmCancelListener(OnDialogCancelListener mCancelListener) {
        this.mCancelListener = mCancelListener;
    }

    public void setmOnCallDialog(OnCallDialog mOnCallDialog) {
        this.mOnCallDialog = mOnCallDialog;
    }

    public void setBottom(boolean bottom) {
        isBottom = bottom;
    }

    public static CommonDialogFragment newInstance(OnCallDialog callDialog, boolean cancelable) {
        return newInstance(callDialog, cancelable, null);
    }

    public static CommonDialogFragment newInstance(OnCallDialog callDialog, boolean cancelable, OnDialogCancelListener cancelListener) {
        CommonDialogFragment instance = new CommonDialogFragment();
        instance.setCancelable(cancelable);
        instance.mCancelListener = cancelListener;
        instance.mOnCallDialog = callDialog;
        return instance;
    }
    public static CommonDialogFragment newInstance(OnCallDialog callDialog, boolean cancelable,boolean isBottom, OnDialogCancelListener cancelListener) {
        CommonDialogFragment instance = new CommonDialogFragment();
        instance.setCancelable(cancelable);
        instance.isBottom = isBottom;
        instance.mCancelListener = cancelListener;
        instance.mOnCallDialog = callDialog;
        return instance;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (null == mOnCallDialog) {
            super.onCreate(savedInstanceState);
        }
        if (getActivity() == null)
            return null;
        return mOnCallDialog.getDialog(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            // 在 5.0 以下的版本会出现白色背景边框，若在 5.0 以上设置则会造成文字部分的背景也变成透明
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                // 目前只有这两个 dialog 会出现边框
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            if (isBottom){
                Window window = getDialog().getWindow();
                window.setGravity(Gravity.BOTTOM);
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(params);
            }else {
                Window window = getDialog().getWindow();
                window.getDecorView().setPadding(0,0,0,0);
                WindowManager.LayoutParams windowParams = window.getAttributes();
                windowParams.dimAmount = 0.6f;
                window.setAttributes(windowParams);
            }

        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCancelListener != null) {
            mCancelListener.onCancel();
        }
    }

    @Override
    public void dismiss() {
        if(isShowing()){
            super.dismiss();
        }
    }

    @Override
    public void dismissAllowingStateLoss() {
        if(isShowing()){
            super.dismissAllowingStateLoss();
        }
    }

    /**
     * 判断弹窗是否显示
     * @return
     */
    public boolean isShowing(){
        return getDialog() != null && getDialog().isShowing();
    }


    /**
     * 取消监听接口
     */
    public interface OnDialogCancelListener {
        void onCancel();
    }

    /**
     * 回调接口
     */
    public interface OnCallDialog {
        Dialog getDialog(Context context);
    }

}

