package com.zx.haijixing.logistics.entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 15:45
 *@描述 物流公司信息
 */
public class CompanyEntry implements Parcelable {
    /* "lgsId": "b96a0e97-2523-4c1d-8155-5f3b9603f6a7",
             "deptId": "8b459fc5-81ed-4d68-9e7c-8857d35dfbe8",
             "cName": "顺风 物流",*/
    String lgsId;
    String deptId;
    String cName;


    public CompanyEntry() {
    }

    public CompanyEntry(String lgsId, String deptId, String cName) {
        this.lgsId = lgsId;
        this.deptId = deptId;
        this.cName = cName;
    }

    protected CompanyEntry(Parcel in) {
        lgsId = in.readString();
        deptId = in.readString();
        cName = in.readString();
    }

    public static final Creator<CompanyEntry> CREATOR = new Creator<CompanyEntry>() {
        @Override
        public CompanyEntry createFromParcel(Parcel in) {
            return new CompanyEntry(in);
        }

        @Override
        public CompanyEntry[] newArray(int size) {
            return new CompanyEntry[size];
        }
    };

    @Override
    public String toString() {
        return "CompanyEntry{" +
                "lgsId='" + lgsId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", cName='" + cName + '\'' +
                '}';
    }

    public String getLgsId() {
        return lgsId;
    }

    public void setLgsId(String lgsId) {
        this.lgsId = lgsId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lgsId);
        dest.writeString(deptId);
        dest.writeString(cName);
    }
}
