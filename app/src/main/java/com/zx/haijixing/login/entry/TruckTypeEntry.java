package com.zx.haijixing.login.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/15 11:52
 *@描述 卡车类型
 */
public class TruckTypeEntry {
     /*"typeId": "f824775a-ae91-4cdc-9b26-969bb2582e2e",
             "name": "皮卡",
             "load": 3*/
     String typeId;
     String name;
     int load;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    @Override
    public String toString() {
        return "TruckTypeEntry{" +
                "typeId='" + typeId + '\'' +
                ", name='" + name + '\'' +
                ", load=" + load +
                '}';
    }
}
