package com.jxxc.jingxijishi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class AwaitReceiveOrderEntity implements Serializable {
    public String address;
    public String appointmentTime;
    public int distance;
    public String price;
    public String orderTopic;
    public String remark;
    public String carNum;
    public String brandType;
    public String phonenumber;
    public String createTime;
    public String startTime;//开始服务时间
    public String endTime;
    public String orderId;
    public String canCompleteTime;
    public String statusName;
    public String duration;
    public int status;
    public int serviceType;
    public float starLevel;
    public String commentContent;
    public String customerCommentTime;
    public List<Products> products;

    public class Products{
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
