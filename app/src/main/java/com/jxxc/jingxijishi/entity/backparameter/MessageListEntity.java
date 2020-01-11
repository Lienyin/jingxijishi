package com.jxxc.jingxijishi.entity.backparameter;

import java.io.Serializable;

public class MessageListEntity implements Serializable {

    public String messageTopic;
    public int isRead;
    public String messageId;
    public String content;
    public String sendTime;
}
