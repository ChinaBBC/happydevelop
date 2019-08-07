package com.zx.haijixing.logistics.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 19:52
 *@描述 司机评价
 */
public class DriverWordEntry {
    /*"headImg": "group1/M00/00/02/wKgBZ11BSpOAEweTAClqNyaTk3s20.JPEG",
            "driverName": "大乔",
            "totalScore": "9.1",
            "logScore": "9.5",
            "serviceScore": "9.21",
            "goodsScore": "8.9"*/

    String headImg;
    String driverName;
    String totalScore;
    String logScore;
    String serviceScore;
    String goodsScore;

    @Override
    public String toString() {
        return "DriverWordEntry{" +
                "headImg='" + headImg + '\'' +
                ", driverName='" + driverName + '\'' +
                ", totalScore='" + totalScore + '\'' +
                ", logScore='" + logScore + '\'' +
                ", serviceScore='" + serviceScore + '\'' +
                ", goodsScore='" + goodsScore + '\'' +
                '}';
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getLogScore() {
        return logScore;
    }

    public void setLogScore(String logScore) {
        this.logScore = logScore;
    }

    public String getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(String serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(String goodsScore) {
        this.goodsScore = goodsScore;
    }
}
