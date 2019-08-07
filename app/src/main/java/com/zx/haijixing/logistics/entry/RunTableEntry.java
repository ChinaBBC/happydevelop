package com.zx.haijixing.logistics.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 16:52
 *@描述 运营总表
 */
public class RunTableEntry {
     /*"waybillCount": "1",
             "sumrealPrice": "200.5",
             "commission": "0",
             "productName": "省内干线"*/
     String waybillCount;//
     String sumrealPrice;//
     String commission;//
     String productName;//专线名称

    @Override
    public String toString() {
        return "RunTableEntry{" +
                "waybillCount='" + waybillCount + '\'' +
                ", sumrealPrice='" + sumrealPrice + '\'' +
                ", commission='" + commission + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }

    public String getWaybillCount() {
        return waybillCount;
    }

    public void setWaybillCount(String waybillCount) {
        this.waybillCount = waybillCount;
    }

    public String getSumrealPrice() {
        return sumrealPrice;
    }

    public void setSumrealPrice(String sumrealPrice) {
        this.sumrealPrice = sumrealPrice;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
