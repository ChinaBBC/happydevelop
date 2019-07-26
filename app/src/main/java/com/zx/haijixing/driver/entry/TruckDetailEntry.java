package com.zx.haijixing.driver.entry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 17:02
 *@描述 车辆详情
 */
public class TruckDetailEntry {
    String msg;
    int code;
    String fileHttpWW;

    TruckInfoBean data;
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

    public TruckInfoBean getData() {
        return data;
    }

    public void setData(TruckInfoBean data) {
        this.data = data;
    }

    public class TruckInfoBean{
        /** 车辆主键 */
        private String carId;
        /** 车辆类型（关联数据字典） */
        private String caeType;
        /** 车主姓名 */
        private String name;
        /** 车主电话 */
        private String phone;
        /** 保险到期时间 */
        private String safeEnd;
        /** 保险单图片 */
        private String safeImage;
        /** 车牌号 */
        private String idcard;
        /** 车辆载重(/K歌) */
        private Double carLoad;
        /** 车辆正面照片 */
        private String carImageFront;
        /** 车辆左侧照片 */
        private String carImageLeft;
        /** 车辆右侧照片 */
        private String carImageRight;
        /** 车辆附件（其他） */
        private String annex;
        /** 行驶证正面 */
        private String drivingFront;
        /** 行驶证反面 */
        private String drivingBack;

        @Override
        public String toString() {
            return "TruckInfoBean{" +
                    "carId='" + carId + '\'' +
                    ", caeType='" + caeType + '\'' +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", safeEnd='" + safeEnd + '\'' +
                    ", safeImage='" + safeImage + '\'' +
                    ", idcard='" + idcard + '\'' +
                    ", carLoad=" + carLoad +
                    ", carImageFront='" + carImageFront + '\'' +
                    ", carImageLeft='" + carImageLeft + '\'' +
                    ", carImageRight='" + carImageRight + '\'' +
                    ", annex='" + annex + '\'' +
                    ", drivingFront='" + drivingFront + '\'' +
                    ", drivingBack='" + drivingBack + '\'' +
                    '}';
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getCaeType() {
            return caeType;
        }

        public void setCaeType(String caeType) {
            this.caeType = caeType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSafeEnd() {
            return safeEnd;
        }

        public void setSafeEnd(String safeEnd) {
            this.safeEnd = safeEnd;
        }

        public String getSafeImage() {
            return safeImage;
        }

        public void setSafeImage(String safeImage) {
            this.safeImage = safeImage;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public Double getCarLoad() {
            return carLoad;
        }

        public void setCarLoad(Double carLoad) {
            this.carLoad = carLoad;
        }

        public String getCarImageFront() {
            return carImageFront;
        }

        public void setCarImageFront(String carImageFront) {
            this.carImageFront = carImageFront;
        }

        public String getCarImageLeft() {
            return carImageLeft;
        }

        public void setCarImageLeft(String carImageLeft) {
            this.carImageLeft = carImageLeft;
        }

        public String getCarImageRight() {
            return carImageRight;
        }

        public void setCarImageRight(String carImageRight) {
            this.carImageRight = carImageRight;
        }

        public String getAnnex() {
            return annex;
        }

        public void setAnnex(String annex) {
            this.annex = annex;
        }

        public String getDrivingFront() {
            return drivingFront;
        }

        public void setDrivingFront(String drivingFront) {
            this.drivingFront = drivingFront;
        }

        public String getDrivingBack() {
            return drivingBack;
        }

        public void setDrivingBack(String drivingBack) {
            this.drivingBack = drivingBack;
        }
    }
}
