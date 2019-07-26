package com.zx.haijixing.share.pub.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/22 15:51
 *@描述 消息中心
 *
 */
public class NotifyEntry {
    /**  消息主键  */
    private String noticeId;
    /**  标题  */
    private String title;
    /**  消息内容 */
    private String content;
    /** 创建时间  */
    private String createTime;
    /** 消息类型（0：审核通知，1：接单提醒..）  */
    private String msgType;
    /** 类型对应的关联字段  */
    private String msgValue;

    @Override
    public String toString() {
        return "NotifyEntry{" +
                "noticeId='" + noticeId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgValue='" + msgValue + '\'' +
                '}';
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgValue() {
        return msgValue;
    }

    public void setMsgValue(String msgValue) {
        this.msgValue = msgValue;
    }
}
