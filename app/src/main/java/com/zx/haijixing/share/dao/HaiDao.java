package com.zx.haijixing.share.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.zx.haijixing.driver.DriverInfo;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:28
 *@描述 数据库操作
 */
@Dao
public interface HaiDao {
    @Query("SELECT * FROM DRIVER_INFO")
    DataSource.Factory<Integer,DriverInfo> getAllDriver();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDrivers(List<DriverInfo> drivers);

}
