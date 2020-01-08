package com.jxxc.jingxijishi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class OrderListEntity implements Serializable {
    public String address;
    public String appointmentTime;
    public String price;
    public String orderTopic;
    public String remark;
    public String carNum;
    public String brandType;
    public String phonenumber;
    public String createTime;
    public String endTime;
    public String statusName;
    public String canCompleteTime;
    public String orderId;
    public int status;
    public double lat;
    public double lng;
    public List<OrderList> products;
    public class OrderList{
        public String productId;
        public String productName;
        public String imgUrl;
    }

    public String getCanCompleteTime() {
        return canCompleteTime;
    }

    public void setCanCompleteTime(String canCompleteTime) {
        this.canCompleteTime = canCompleteTime;
    }
}
