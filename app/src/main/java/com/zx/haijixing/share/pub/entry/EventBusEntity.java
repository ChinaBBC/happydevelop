package com.zx.haijixing.share.pub.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/22 17:10
 *@描述 eventbus
 */
public class EventBusEntity {
    private int mMsg;
    private String message;

    public EventBusEntity(int msg) {
        mMsg = msg;
    }

    public int getMsg() {
        return mMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
