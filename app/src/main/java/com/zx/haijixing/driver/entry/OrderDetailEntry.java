package com.zx.haijixing.driver.entry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/18 20:27
 *@描述 订单详情
 */
public class OrderDetailEntry {
    /** 运单id */
    private String waybillId;
    /** 运单编号 */
    private String waybillNo;
    /** 运单状态 */
    private String status;
    /** 下单时间 */
    private String createTime;
    /**  接单倒计时  */
    private String timeEnd;
    //收发件人信息
    /** 发件人姓名 */
    private String senderName;
    /** 发件人电话 */
    private String senderPhone;
    /** 发件人地址关联接纬度表 */
    private String senderAddress;
    //货物信息
    /** 运输品类（关联数据字典） */
    private String category;
    /** 起步价 */
    private String startPrice;
    /** 合计件数 */
    private Integer totalNum;
    /** 运单物流价格 */
    private String price;
    /** 付款方式( 1:寄付  2：到付) */
    private String type;
    /**  物流产品*/
    private String productName;
    /**  运输路线*/
    private String linkName;
    /**  运输路线  起点名称*/
    private String lineStartName;
    /**  运输路线  终点名称*/
    private String lineEndName;
    /**  打单状态  0未打单  1已打单  */
    private String dadanFlag;
    /** 货物重量（/Kg） */
    private String weight;
    /** 收件人姓名 */
    private String incomeName;
    /** 收件人电话 */
    private String incomePhone;
    /** 收件人地址关联接纬度表 */
    private String incomeAddress;
    /**  接单时间  */
    private String receiptTime;
    /** 车牌号 */
    private String idcard;
    /** 司机姓名 */
    private String driverName;
    /** 司机电话 */
    private String driverPhone;
    /**  物流公司 */
    private String lgsName;
    /**  线路开始时间 */
    private String lineStartTime;
    /**  线路结束时间 */
    private String lineEndTime;
    /** 备注 */
    private String remark;
    /**  增值服务 */
    private String addedServices;
    //客户评价
    /** 货物完整性评分 */
    private String completeGrade;
    /** 配送及时性评分 */
    private String timelyGrade;
    /** 服务态度评分 */
    private String attitudeGrade;
    /** 总评分（平均） */
    private String allGrade;
    /** 文字评论内容 */
    private String content;

    /**  货物数量详情 */
    private List<AppWaybillGoodsVo> goods;

    /**  优惠券抵扣金额  */
    private String couponMoney;
    /**  积分抵扣金额   */
    private String integralMoney;
    //出发时间
    private String goTime;
    //完成时间
    private String doneTime;

    public String getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(String doneTime) {
        this.doneTime = doneTime;
    }

    public String getGoTime() {
        return goTime;
    }

    public void setGoTime(String goTime) {
        this.goTime = goTime;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getIntegralMoney() {
        return integralMoney;
    }

    public void setIntegralMoney(String integralMoney) {
        this.integralMoney = integralMoney;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
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

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
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

    public String getDadanFlag() {
        return dadanFlag;
    }

    public void setDadanFlag(String dadanFlag) {
        this.dadanFlag = dadanFlag;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public String getIncomeAddress() {
        return incomeAddress;
    }

    public void setIncomeAddress(String incomeAddress) {
        this.incomeAddress = incomeAddress;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
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

    public String getLgsName() {
        return lgsName;
    }

    public void setLgsName(String lgsName) {
        this.lgsName = lgsName;
    }

    public String getLineStartTime() {
        return lineStartTime;
    }

    public void setLineStartTime(String lineStartTime) {
        this.lineStartTime = lineStartTime;
    }

    public String getLineEndTime() {
        return lineEndTime;
    }

    public void setLineEndTime(String lineEndTime) {
        this.lineEndTime = lineEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddedServices() {
        return addedServices;
    }

    public void setAddedServices(String addedServices) {
        this.addedServices = addedServices;
    }

    public List<AppWaybillGoodsVo> getGoods() {
        return goods;
    }

    public void setGoods(List<AppWaybillGoodsVo> goods) {
        this.goods = goods;
    }

    public String getCompleteGrade() {
        return completeGrade;
    }

    public void setCompleteGrade(String completeGrade) {
        this.completeGrade = completeGrade;
    }

    public String getTimelyGrade() {
        return timelyGrade;
    }

    public void setTimelyGrade(String timelyGrade) {
        this.timelyGrade = timelyGrade;
    }

    public String getAttitudeGrade() {
        return attitudeGrade;
    }

    public void setAttitudeGrade(String attitudeGrade) {
        this.attitudeGrade = attitudeGrade;
    }

    public String getAllGrade() {
        return allGrade;
    }

    public void setAllGrade(String allGrade) {
        this.allGrade = allGrade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "OrderDetailEntry{" +
                "waybillId='" + waybillId + '\'' +
                ", waybillNo='" + waybillNo + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderPhone='" + senderPhone + '\'' +
                ", senderAddress='" + senderAddress + '\'' +
                ", category='" + category + '\'' +
                ", startPrice='" + startPrice + '\'' +
                ", totalNum=" + totalNum +
                ", price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", productName='" + productName + '\'' +
                ", linkName='" + linkName + '\'' +
                ", lineStartName='" + lineStartName + '\'' +
                ", lineEndName='" + lineEndName + '\'' +
                ", dadanFlag='" + dadanFlag + '\'' +
                ", weight='" + weight + '\'' +
                ", incomeName='" + incomeName + '\'' +
                ", incomePhone='" + incomePhone + '\'' +
                ", incomeAddress='" + incomeAddress + '\'' +
                ", receiptTime='" + receiptTime + '\'' +
                ", idcard='" + idcard + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", lgsName='" + lgsName + '\'' +
                ", lineStartTime='" + lineStartTime + '\'' +
                ", lineEndTime='" + lineEndTime + '\'' +
                ", remark='" + remark + '\'' +
                ", addedServices='" + addedServices + '\'' +
                ", completeGrade='" + completeGrade + '\'' +
                ", timelyGrade='" + timelyGrade + '\'' +
                ", attitudeGrade='" + attitudeGrade + '\'' +
                ", allGrade='" + allGrade + '\'' +
                ", content='" + content + '\'' +
                ", goods=" + goods +
                ", couponMoney='" + couponMoney + '\'' +
                ", integralMoney='" + integralMoney + '\'' +
                '}';
    }

    public class AppWaybillGoodsVo{
        /**  主键 */
        private String goodsId;
        /**  件数 */
        private String dwValue;
        //货物品类id
        private String gtId;
        //品类对应单价
        private String dwMoney;
        //货物规格名称
        private String dwName;
        //规格对应重量计算值
        private String dwAvg;
        //货物规格唯一标识
        private String dictCode ;

        public String getDictCode() {
            return dictCode;
        }

        public void setDictCode(String dictCode) {
            this.dictCode = dictCode;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getDwValue() {
            return dwValue;
        }

        public void setDwValue(String dwValue) {
            this.dwValue = dwValue;
        }

        public String getGtId() {
            return gtId;
        }

        public void setGtId(String gtId) {
            this.gtId = gtId;
        }

        public String getDwMoney() {
            return dwMoney;
        }

        public void setDwMoney(String dwMoney) {
            this.dwMoney = dwMoney;
        }

        public String getDwName() {
            return dwName;
        }

        public void setDwName(String dwName) {
            this.dwName = dwName;
        }

        public String getDwAvg() {
            return dwAvg;
        }

        public void setDwAvg(String dwAvg) {
            this.dwAvg = dwAvg;
        }

        @Override
        public String toString() {
            return "AppWaybillGoodsVo{" +
                    "goodsId='" + goodsId + '\'' +
                    ", dwValue='" + dwValue + '\'' +
                    ", gtId='" + gtId + '\'' +
                    ", dwMoney='" + dwMoney + '\'' +
                    ", dwName='" + dwName + '\'' +
                    ", dwAvg='" + dwAvg + '\'' +
                    '}';
        }
    }
}
