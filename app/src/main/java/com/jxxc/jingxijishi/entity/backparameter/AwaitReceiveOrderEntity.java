package com.jxxc.jingxijishi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class AwaitReceiveOrderEntity implements Serializable {
    public String address;
    public String appointmentTime;
    public String distance;
    public String price;
    public String orderTopic;
    public String remark;
    public String carNum;
    public String brandType;
    public String phonenumber;
    public String createTime;
    public String statusName;
    public List<Products> products;

    public class Products{
        public String productId;
        public String productName;
        public String imgUrl;

    }
}
