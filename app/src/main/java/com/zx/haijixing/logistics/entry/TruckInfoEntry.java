package com.zx.haijixing.logistics.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/2 8:56
 *@描述 车辆信息
 */
public class TruckInfoEntry {
    String carId;
    String idcard;

    @Override
    public String toString() {
        return "TruckInfoEntry{" +
                "carId='" + carId + '\'' +
                ", idcard='" + idcard + '\'' +
                '}';
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
