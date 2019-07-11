package com.zx.haijixing.driver.entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.zx.haijixing.share.base.HaiBaseData;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 13:35
 *@描述 新闻资讯
 */
public class NewsEntry{

    String msg;
    int code;
    String fileHttpWW;
    List<NewsData> data;

    @Override
    public String toString() {
        return "NewsEntry{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", fileHttpWW='" + fileHttpWW + '\'' +
                ", data=" + data +
                '}';
    }

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

    public List<NewsData> getData() {
        return data;
    }

    public void setData(List<NewsData> data) {
        this.data = data;
    }

    @Entity(tableName = "news")
    public class NewsData{
        @ColumnInfo(name = "nid")
        @PrimaryKey()
        @NonNull
        String newId;//id
        String title;//标题
        String ftitle;//简介
        String content;//内容
        String createTime;//时间
        String coverImg;//图片


        @NonNull
        public String getNewId() {
            return newId;
        }

        public void setNewId(@NonNull String newId) {
            this.newId = newId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFtitle() {
            return ftitle;
        }

        public void setFtitle(String ftitle) {
            this.ftitle = ftitle;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        @Override
        public String toString() {
            return "NewsData{" +
                    "newId='" + newId + '\'' +
                    ", title='" + title + '\'' +
                    ", ftitle='" + ftitle + '\'' +
                    ", content='" + content + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", coverImg='" + coverImg + '\'' +
                    '}';
        }
    }
}


