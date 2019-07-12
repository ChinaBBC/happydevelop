package com.zx.haijixing.login.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/12 10:03
 *@描述 登录
 */
public class LoginEntry {
    /*"userId":"5266c28f-19ef-4e31-b468-6d5513ef361d",
            "deptId":"selfAndroid",
            "userName":"策",
            "phonenumber":"12333333333",
            "avatar":"http://192.168.5.180:80/profile/null",
            "sex":"2",
            "loginDate":"2019-07-12 08:54:48",
            "token":"7e7fbc0af63e494587e3d5269c004f51"*/
    String userId;
    String deptId;
    String userName;
    String phonenumber;
    String avatar;
    int sex;
    String loginDate;
    String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginEntry{" +
                "userId='" + userId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", userName='" + userName + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", loginDate='" + loginDate + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
