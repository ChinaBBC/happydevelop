package com.zx.haijixing.driver.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 16:34
 *@描述 车辆信息
 */
public class TruckEntry {
    String CARID;
    String IDCARD;

    @Override
    public String toString() {
        return "TruckEntry{" +
                "CARID='" + CARID + '\'' +
                ", IDCARD='" + IDCARD + '\'' +
                '}';
    }

    public String getCARID() {
        return CARID;
    }

    public void setCARID(String CARID) {
        this.CARID = CARID;
    }

    public String getIDCARD() {
        return IDCARD;
    }

    public void setIDCARD(String IDCARD) {
        this.IDCARD = IDCARD;
    }
}
