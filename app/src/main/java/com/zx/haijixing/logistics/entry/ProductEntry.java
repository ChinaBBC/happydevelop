package com.zx.haijixing.logistics.entry;

public class ProductEntry {
    /** 字典编码 */
    private String dictCode;


    /** 字典键值 */
    private String dictValue;

    /** 备注 */
    private String remark;

    /** 描述 */
    private String contentDesc;

    /** 封面图 */
    private String coverImage;


    @Override
    public String toString() {
        return "ProductEntry{" +
                "dictCode='" + dictCode + '\'' +
                ", dictValue='" + dictValue + '\'' +
                ", remark='" + remark + '\'' +
                ", contentDesc='" + contentDesc + '\'' +
                ", coverImage='" + coverImage + '\'' +
                '}';
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
