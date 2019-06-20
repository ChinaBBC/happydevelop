package com.zx.haijixing.driver;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "driver_info",primaryKeys = "id")
public class DriverInfo implements Serializable {
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
