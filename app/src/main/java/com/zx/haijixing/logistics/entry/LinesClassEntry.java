package com.zx.haijixing.logistics.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 9:58
 *@描述 线路班次
 */
public class LinesClassEntry {
    /**  班次id */
    private String bakkiId = "";
    /**  司机姓名  */
    private String driverName = "";
    /**  车牌号 */
    private String idcard = "";
    /**  已装载重量 */
    private String weight = "0";
    /**  总件数 */
    private String totalNum = "0";
    /**  汽车载重 */
    private String load = "0";
    /**  物流产品 */
    private String productName = "";
    /**  起点 */
    private String startName = "";
    /**  终点 */
    private String endName = "";
    /**  班次开始日期 */
    private String startTime = "";
    /**  班次结束日期 */
    private String endTime = "";
    /**  班次相差时间 */
    private String differTime = "";
    /**  货物装载信息 */
    private String goodsStr = "";
    /**  班次状态(0:等待中 , 1装货中  , 2已发车  , 3已完成  4未发车 */
    private String bkkiStatus;

    @Override
    public String toString() {
        return "LinesClassEntry{" +
                "bakkiId='" + bakkiId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", idcard='" + idcard + '\'' +
                ", weight='" + weight + '\'' +
                ", totalNum='" + totalNum + '\'' +
                ", load='" + load + '\'' +
                ", productName='" + productName + '\'' +
                ", startName='" + startName + '\'' +
                ", endName='" + endName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", differTime='" + differTime + '\'' +
                ", goodsStr='" + goodsStr + '\'' +
                ", bkkiStatus='" + bkkiStatus + '\'' +
                '}';
    }

    public String getBkkiStatus() {
        return bkkiStatus;
    }

    public void setBkkiStatus(String bkkiStatus) {
        this.bkkiStatus = bkkiStatus;
    }

    public String getBakkiId() {
        return bakkiId;
    }

    public void setBakkiId(String bakkiId) {
        this.bakkiId = bakkiId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
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

    public String getDifferTime() {
        return differTime;
    }

    public void setDifferTime(String differTime) {
        this.differTime = differTime;
    }

    public String getGoodsStr() {
        return goodsStr;
    }

    public void setGoodsStr(String goodsStr) {
        this.goodsStr = goodsStr;
    }
}
