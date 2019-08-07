package com.zx.haijixing.driver.entry;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 11:15
 *@描述 统计点位
 */
public class MyPoint implements Parcelable {
    public float x;
    public float y;

    public MyPoint() {
    }

    public MyPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    protected MyPoint(Parcel in) {
        x = in.readFloat();
        y = in.readFloat();
    }

    @Override
    public String toString() {
        return "MyPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPoint myPoint = (MyPoint) o;
        return Double.compare(myPoint.x, x) == 0 &&
                Double.compare(myPoint.y, y) == 0;
    }

    @SuppressLint("NewApi")
    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    public static final Creator<MyPoint> CREATOR = new Creator<MyPoint>() {
        @Override
        public MyPoint createFromParcel(Parcel in) {
            return new MyPoint(in);
        }

        @Override
        public MyPoint[] newArray(int size) {
            return new MyPoint[size];
        }
    };

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(x);
        dest.writeFloat(y);
    }
}
