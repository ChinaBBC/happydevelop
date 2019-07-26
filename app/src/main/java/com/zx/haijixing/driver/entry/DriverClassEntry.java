package com.zx.haijixing.driver.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/20 11:30
 *@描述 司机班次
 */
public class DriverClassEntry {
    /**  班次id */
    private String bakkiId;
    /**  开始时间 */
    private String startTime;
    /**  结束时间 */
    private String endTime;
    /**  备注 */
//    private String remarks;
    /**  起点 */
    private String lineStartName;
    /**  终点 */
    private String lineEndName;
    /**  车牌号 */
    private String idcard;
    /**  班次状态(0:等待中 , 1装货中  , 2已发车  , 3已完成  4未发车) */
    private String bkkiStatus;
    /**  物流产品 */
    private String productName;

    @Override
    public String toString() {
        return "DriverClassEntry{" +
                "bakkiId='" + bakkiId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", lineStartName='" + lineStartName + '\'' +
                ", lineEndName='" + lineEndName + '\'' +
                ", idcard='" + idcard + '\'' +
                ", bkkiStatus='" + bkkiStatus + '\'' +
                ", productName='" + productName + '\'' +
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

    public String getLineStartName() {
        return lineStartName;
    }

    public void setLineStartName(String lineStartName) {
        this.lineStartName = lineStartName;
    }

    public String getLineEndName() {
        return lineEndName;
    }

    public void setLineEndName(String lineEndName) {
        this.lineEndName = lineEndName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getBkkiStatus() {
        return bkkiStatus;
    }

    public void setBkkiStatus(String bkkiStatus) {
        this.bkkiStatus = bkkiStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
