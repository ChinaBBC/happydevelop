package com.zx.haijixing.driver.entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 13:35
 *@描述 新闻资讯
 */
@Entity(primaryKeys = "id")
public class NewsEntry {
    @ColumnInfo(name = "id")
    long id;
    String title;//标题
    String simple;//简介
    String content;//内容
    String time;//时间
    String img;//图片

    @Override
    public String toString() {
        return "NewsEntry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", simple='" + simple + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSimple() {
        return simple;
    }

    public void setSimple(String simple) {
        this.simple = simple;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
