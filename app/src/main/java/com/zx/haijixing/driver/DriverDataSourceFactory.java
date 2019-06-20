package com.zx.haijixing.driver;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;


public class DriverDataSourceFactory extends DataSource.Factory<Integer,DriverInfo> {
    private MutableLiveData<DriverDataSource> dataSourceMutableLiveData = new MutableLiveData<>();
    @Override
    public DataSource<Integer, DriverInfo> create() {
        DriverDataSource driverDataSource = new DriverDataSource();
        dataSourceMutableLiveData.postValue(driverDataSource);
        return driverDataSource;
    }
}
