package com.zx.haijixing.share.pub.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/11 8:44
 *@描述 版本控制
 */
public class VersionEntry {
      /*"appId": "d8fd0ed1-60b1-44ba-b9c3-58c97d4a14e9",
              "versionNo": "1.0.2",
              "versionNum": 2,
              "remark": "dc22222",
              "downloadUrl": "http://192.168.5.180:80/profile/image/b9ca8d631767d8172f209597dc7c281f.apk",
              "status": 1,
              "createTime": "2019-07-09 10:42:39"*/
      String appId;
      String versionNo;
      int versionNum;
      String remark;
      String downloadUrl;
      int status;
      String createTime;

    @Override
    public String toString() {
        return "VersionEntry{" +
                "appId='" + appId + '\'' +
                ", versionNo='" + versionNo + '\'' +
                ", versionNum=" + versionNum +
                ", remark='" + remark + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
