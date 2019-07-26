package com.zx.haijixing.driver.entry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/18 11:00
 *@描述 订单
 */
public class OrderTotalEntry{
    String total;//总数
    List<OrderEntry> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OrderEntry> getRows() {
        return rows;
    }

    public void setRows(List<OrderEntry> rows) {
        this.rows = rows;
    }

    public class OrderEntry {

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
        /**  运输主键*/
        private String lineId;


        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }


        public String getLineId() {
            return lineId;
        }

        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        @Override
        public String toString() {
            return "OrderEntry{" +
                    "waybillId='" + waybillId + '\'' +
                    ", waybillNo='" + waybillNo + '\'' +
                    ", status='" + status + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", timeEnd='" + timeEnd + '\'' +
                    ", senderName='" + senderName + '\'' +
                    ", senderPhone='" + senderPhone + '\'' +
                    ", senderAddress='" + senderAddress + '\'' +
                    ", category='" + category + '\'' +
                    ", totalNum=" + totalNum +
                    ", price='" + price + '\'' +
                    ", type='" + type + '\'' +
                    ", productName='" + productName + '\'' +
                    ", linkName='" + linkName + '\'' +
                    ", lineStartName='" + lineStartName + '\'' +
                    ", lineEndName='" + lineEndName + '\'' +
                    ", dadanFlag='" + dadanFlag + '\'' +", lineId='" + lineId + '\'' +
                    '}';
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
    }
}


