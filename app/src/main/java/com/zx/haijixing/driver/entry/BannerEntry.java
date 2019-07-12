package com.zx.haijixing.driver.entry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/12 11:04
 *@描述 轮播图
 */
public class BannerEntry {
    String msg;
    int code;
    String fileHttpWW;
    List<BannerData> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFileHttpWW() {
        return fileHttpWW;
    }

    public void setFileHttpWW(String fileHttpWW) {
        this.fileHttpWW = fileHttpWW;
    }

    public List<BannerData> getData() {
        return data;
    }

    public void setData(List<BannerData> data) {
        this.data = data;
    }

    /*"bannerId": "87282fe3-c555-4afa-98e8-66ccd8e89bbc",
             "url": "faa3efea6b2e017e536369208cf0652c.jpg",
             "link": "qk",
             "createTime": "2019-07-09 18:12:20"*/

     public class BannerData{
         String bannerId;
         String url;
         String link;
         String createTime;

         @Override
         public String toString() {
             return "BannerData{" +
                     "bannerId='" + bannerId + '\'' +
                     ", url='" + url + '\'' +
                     ", link='" + link + '\'' +
                     ", createTime='" + createTime + '\'' +
                     '}';
         }

         public String getBannerId() {
             return bannerId;
         }

         public void setBannerId(String bannerId) {
             this.bannerId = bannerId;
         }

         public String getUrl() {
             return url;
         }

         public void setUrl(String url) {
             this.url = url;
         }

         public String getLink() {
             return link;
         }

         public void setLink(String link) {
             this.link = link;
         }

         public String getCreateTime() {
             return createTime;
         }

         public void setCreateTime(String createTime) {
             this.createTime = createTime;
         }
     }
}
