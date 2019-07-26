package com.zx.haijixing.driver.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/24 17:16
 *@描述 总评分
 */
public class TotalEvaluateEntry {
    /** 评分主键 */
    private String scoreId;
    /** 司机主键 */
    private String driverId;
    /** 平均总评分 */
    private String avgScore;
    /** 物流评价总分 */
    private String logScore;
    /** 服务评价总分 */
    private String serverScore;
    /** 货物完整性总评分 */
    private String goodsScore;
    /** 创建时间 */
    private String createTime;
    /** 最后更新时间 */
    private String updateTime;
    //评论个数
    private Integer commentCount ;

    @Override
    public String toString() {
        return "TotalEvaluateEntry{" +
                "scoreId='" + scoreId + '\'' +
                ", driverId='" + driverId + '\'' +
                ", avgScore='" + avgScore + '\'' +
                ", logScore='" + logScore + '\'' +
                ", serverScore='" + serverScore + '\'' +
                ", goodsScore='" + goodsScore + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", commentCount=" + commentCount +
                '}';
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public String getLogScore() {
        return logScore;
    }

    public void setLogScore(String logScore) {
        this.logScore = logScore;
    }

    public String getServerScore() {
        return serverScore;
    }

    public void setServerScore(String serverScore) {
        this.serverScore = serverScore;
    }

    public String getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(String goodsScore) {
        this.goodsScore = goodsScore;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
