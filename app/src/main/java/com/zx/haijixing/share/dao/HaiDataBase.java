package com.zx.haijixing.share.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zx.haijixing.driver.DriverInfo;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:29
 *@描述 创建数据库
 */
@Database(entities = {DriverInfo.class},version = 3,exportSchema = false)
public abstract class HaiDataBase extends RoomDatabase {
    private static final String DB_NAME = "HaiJiXin.db";
    private static volatile HaiDataBase instance;
    public static synchronized HaiDataBase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static HaiDataBase create(final Context context){
        return Room.databaseBuilder(context,HaiDataBase.class,DB_NAME).build();
    }

    public static HaiDataBase updateDataBase(final Context context){
        //return Room.databaseBuilder(context,AppDataBase.class,DB_NAME).addMigrations(MIGRATION_1_2).build();
        return Room.databaseBuilder(context,HaiDataBase.class,DB_NAME).fallbackToDestructiveMigration().build();
    }
    public abstract HaiDao getDao();

    public static void onDestroy(){
        instance = null;
    }
    //数据库变动添加Migration
    public static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //database.
            //database.execSQL("ALTER TABLE mybean ADD COLUMN my_age INTEGER NOT NULL DEFAULT 18");
        }
    };
}
