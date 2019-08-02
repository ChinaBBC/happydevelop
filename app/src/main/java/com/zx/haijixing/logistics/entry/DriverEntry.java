package com.zx.haijixing.logistics.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/2 8:51
 *@描述 司机信息
 */
public class DriverEntry {
    String driverId;
    String driverName;

    @Override
    public String toString() {
        return "DriverEntry{" +
                "driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                '}';
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
