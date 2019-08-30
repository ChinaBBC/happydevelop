package com.zx.haijixing.logistics.entry;

import java.io.Serializable;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 15:48
 *@描述 班次维护
 */
public class ClassManageEntry implements Serializable {
    /**  班次主键  */
    private String bakkiId;
    /**  开始时间  */
    private String startTime;
    /**  结束时间  */
    private String endTime;
    /**  备注  */
    private String remark;
    /**  车牌号  */
    private String idcard;
    /**  司机姓名  */
    private String driverName;
    /**  司机电话  */
    private String driverPhone;
    /**  物流产品名称  */
    private String productName;
    /**  时间差  */
    private String differTime;

    @Override
    public String toString() {
        return "ClassManageEntry{" +
                "bakkiId='" + bakkiId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", remark='" + remark + '\'' +
                ", idcard='" + idcard + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", productName='" + productName + '\'' +
                ", differTime='" + differTime + '\'' +
                '}';
    }

    public String getBakkiId() {
        return bakkiId;
    }

    public void setBakkiId(String bakkiId) {
        this.bakkiId = bakkiId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDifferTime() {
        return differTime;
    }

    public void setDifferTime(String differTime) {
        this.differTime = differTime;
    }
}
