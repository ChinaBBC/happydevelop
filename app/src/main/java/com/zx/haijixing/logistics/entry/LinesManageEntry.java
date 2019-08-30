package com.zx.haijixing.logistics.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 15:02
 *@描述 线路管理
 */
public class LinesManageEntry {
    /**  线路主键 */
    private String lineId;
    /**  物流产品名称 */
    private String productName;
    /**  起点名称 */
    private String startName;
    /**  终点名称 */
    private String endName;
    /**  发货类型 寄付，状态（0：支持  1：不支持 ） */
    private int deposit;
    /**  发货类型到付，状态（0：支持  1：不支持 ） */
    private int toPay;
    /**  起点地址 */
    private String startAddress;
    /**  终点地址 */
    private String endAddress;
    /**  备注  */
    private String remark;


    @Override
    public String toString() {
        return "LinesManageEntry{" +
                "lineId='" + lineId + '\'' +
                ", productName='" + productName + '\'' +
                ", startName='" + startName + '\'' +
                ", endName='" + endName + '\'' +
                ", deposit=" + deposit +
                ", toPay=" + toPay +
                ", startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
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

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getToPay() {
        return toPay;
    }

    public void setToPay(int toPay) {
        this.toPay = toPay;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }
}
