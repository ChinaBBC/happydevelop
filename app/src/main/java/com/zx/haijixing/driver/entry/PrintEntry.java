package com.zx.haijixing.driver.entry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 11:37
 *@描述 打单
 */
public class PrintEntry {
    /**  运单号   */
    private String waybillNo;
    /** 下单时间 */
    private String createTime;
    /** 发件人姓名 */
    private String senderName;
    /** 发件人电话 */
    private String senderPhone;
    /** 收件人姓名 */
    private String incomeName;
    /** 收件人电话 */
    private String incomePhone;
    /** 运输品类（关联数据字典） */
    private String category;
    /**  线路起点*/
    private String lineStartName;
    /**  线路起点*/
    private String lineEndName;
    /** 车牌号 */
    private String idcard;
    /** 付款方式( 1:寄付  2：到付) */
    private String type;
    /** 货物重量（/Kg） */
    private String weight;
    /** 货物件数 */
    private String goodsNum;
    /**  二维码地址 */
    private String qrCodeUrl;
    /**  货物件数详情  */
    private List<String> goodsList;
    /**  货物总件数 */
    private String totalNum;
    //收款状态（0未收，已收）
    private String makepriceFlag;
    //收件地址
    private String incomeAddress;
    //发件地址
    private String senderAddress;

    @Override
    public String toString() {
        return "PrintEntry{" +
                "waybillNo='" + waybillNo + '\'' +
                ", createTime='" + createTime + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderPhone='" + senderPhone + '\'' +
                ", incomeName='" + incomeName + '\'' +
                ", incomePhone='" + incomePhone + '\'' +
                ", category='" + category + '\'' +
                ", lineStartName='" + lineStartName + '\'' +
                ", lineEndName='" + lineEndName + '\'' +
                ", idcard='" + idcard + '\'' +
                ", type='" + type + '\'' +
                ", weight='" + weight + '\'' +
                ", goodsNum='" + goodsNum + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                ", goodsList=" + goodsList +
                ", totalNum='" + totalNum + '\'' +
                ", makepriceFlag='" + makepriceFlag + '\'' +
                ", incomeAddress='" + incomeAddress + '\'' +
                ", senderAddress='" + senderAddress + '\'' +
                '}';
    }

    public String getMakepriceFlag() {
        return makepriceFlag;
    }

    public void setMakepriceFlag(String makepriceFlag) {
        this.makepriceFlag = makepriceFlag;
    }

    public String getIncomeAddress() {
        return incomeAddress;
    }

    public void setIncomeAddress(String incomeAddress) {
        this.incomeAddress = incomeAddress;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public String getIncomePhone() {
        return incomePhone;
    }

    public void setIncomePhone(String incomePhone) {
        this.incomePhone = incomePhone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public List<String> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<String> goodsList) {
        this.goodsList = goodsList;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }
}
