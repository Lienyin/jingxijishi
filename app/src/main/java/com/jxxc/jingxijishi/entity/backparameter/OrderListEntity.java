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
    public List<OrderList> products;
    public class OrderList{
        public String productId;
        public String productName;
        public String imgUrl;
    }
}