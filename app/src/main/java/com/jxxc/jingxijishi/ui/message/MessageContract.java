package com.jxxc.jingxijishi.ui.message;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.MessageListEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessageContract {
    interface View extends BaseView {
        void MessageListCallBack(List<MessageListEntity> data);
        void MessageListMoreCallBack(List<MessageListEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void MessageList(int pageNum,int pageSize);
        void MessageListMore(int pageNum,int pageSize);
    }
}
