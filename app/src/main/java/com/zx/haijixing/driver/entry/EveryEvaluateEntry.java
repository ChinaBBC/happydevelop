package com.zx.haijixing.driver.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/24 17:17
 *@描述 单个评价
 */
public class EveryEvaluateEntry {
    private String waybillNo ;//运单号

    private String startName ;//起点

    private String endName ;//终点

    private String serviceScore ;//服务评分

    private String logScore ;//物流评分

    private String goodsScore ;//货物完整评分

    private String priceName ;//品类名称

    private String goodsNum ;//件数

    private String remark ;//备注

    @Override
    public String toString() {
        return "EveryEvaluateEntry{" +
                "waybillNo='" + waybillNo + '\'' +
                ", startName='" + startName + '\'' +
                ", endName='" + endName + '\'' +
                ", serviceScore='" + serviceScore + '\'' +
                ", logScore='" + logScore + '\'' +
                ", goodsScore='" + goodsScore + '\'' +
                ", priceName='" + priceName + '\'' +
                ", goodsNum='" + goodsNum + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
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

    public String getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(String serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getLogScore() {
        return logScore;
    }

    public void setLogScore(String logScore) {
        this.logScore = logScore;
    }

    public String getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(String goodsScore) {
        this.goodsScore = goodsScore;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
