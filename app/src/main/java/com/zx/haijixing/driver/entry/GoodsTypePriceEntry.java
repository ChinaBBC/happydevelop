package com.zx.haijixing.driver.entry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 20:21
 *@描述 运单的品类及价格
 */
public class GoodsTypePriceEntry {
     /*"msg": "操作成功",
  "dyPrice": "5",
  "code": 0,
   "xyPrice": "20",
  "staticPrice": "3.5"
  */
     String msg;
     String dyPrice;//大于标准时的折扣
     int code;
     String xyPrice;//小于标准时的折扣
     String staticPrice;//标准价格
     List<GoodType> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDyPrice() {
        return dyPrice;
    }

    public void setDyPrice(String dyPrice) {
        this.dyPrice = dyPrice;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getXyPrice() {
        return xyPrice;
    }

    public void setXyPrice(String xyPrice) {
        this.xyPrice = xyPrice;
    }

    public String getStaticPrice() {
        return staticPrice;
    }

    public void setStaticPrice(String staticPrice) {
        this.staticPrice = staticPrice;
    }

    public List<GoodType> getData() {
        return data;
    }

    public void setData(List<GoodType> data) {
        this.data = data;
    }

    public class GoodType{
         //线路品类id
         private String priceId;
         //品类名称
         private String className;
         //品类对应起步价
         private Integer startPrice;
         //货物规格信息
         private List<LinePriceGtVo> linePriceGtVos;

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Integer getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(Integer startPrice) {
            this.startPrice = startPrice;
        }

        public List<LinePriceGtVo> getLinePriceGtVos() {
            return linePriceGtVos;
        }

        public void setLinePriceGtVos(List<LinePriceGtVo> linePriceGtVos) {
            this.linePriceGtVos = linePriceGtVos;
        }

        public class LinePriceGtVo{
             //品类规格id
             private String gtId;
             //品类对应单价
             private String dwMoney;
             //货物规格名称
             private String dwName;
             //规格对应重量计算值
             private String dwAvg;
             //货物规格唯一标识
             private String dictCode ;

            public String getGtId() {
                return gtId;
            }

            public void setGtId(String gtId) {
                this.gtId = gtId;
            }

            public String getDwMoney() {
                return dwMoney;
            }

            public void setDwMoney(String dwMoney) {
                this.dwMoney = dwMoney;
            }

            public String getDwName() {
                return dwName;
            }

            public void setDwName(String dwName) {
                this.dwName = dwName;
            }

            public String getDwAvg() {
                return dwAvg;
            }

            public void setDwAvg(String dwAvg) {
                this.dwAvg = dwAvg;
            }

            public String getDictCode() {
                return dictCode;
            }

            public void setDictCode(String dictCode) {
                this.dictCode = dictCode;
            }
        }
     }


}
