package com.jxxc.jingxijishi.ui.accomplishorder;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxc.jingxijishi.ConfigApplication;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AccomplishOrderActivity extends MVPBaseActivity<AccomplishOrderContract.View, AccomplishOrderPresenter> implements AccomplishOrderContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_list_images)
    RecyclerView rvListImages;
    @BindView(R.id.rv_ic_add)
    RecyclerView rvIcAdd;
    private ImagesAdapter mImagesAdapter;
    private AddAdapter addAdapter;
    private List<String> uriList = new ArrayList<>();
    private ArrayList<String> addList = new ArrayList<>();
    private static final int REQUEST_CODE_CHOOSE = 110;
    @Override
    protected int layoutId() {
        return R.layout.accomplish_order_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("确认完成");
        mPresenter.initImageSelecter();
        initAdapter();
    }

    private void initAdapter() {
        //图片适配器
        rvListImages.setLayoutManager(new GridLayoutManager(this, 4));
        mImagesAdapter = new ImagesAdapter(R.layout.item_malfunction_repair_image, uriList);
        rvListImages.setAdapter(mImagesAdapter);
        //加号
        if (mImagesAdapter.getData().size()<3) {
            addList.clear();
            addList.addAll(mImagesAdapter.getData());
            addList.add("");
        }
        rvIcAdd.setLayoutManager(new GridLayoutManager(this, 4));
        addAdapter = new AddAdapter(R.layout.item_malfunction_repair_add, addList);
        rvIcAdd.setAdapter(addAdapter);
        addAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_item) {
                    mPresenter.gotoImageSelect(AccomplishOrderActivity.this, REQUEST_CODE_CHOOSE);
                }else if (view.getId() == R.id.fl_delete) {
                    mImagesAdapter.remove(position);
                    if (mImagesAdapter.getData().size()<=3) {
                        addList.clear();
                        addList.addAll(mImagesAdapter.getData());
                        addList.add("");
                    }
                    addAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            final List<String> pathList = data.getStringArrayListExtra("result");
            final ArrayList<String> path2 = new ArrayList<>();
            Luban.with(this)
                    .load(pathList)
                    .ignoreBy(50)
                    .setTargetDir(ConfigApplication.CACHA_URL)
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
//                            StyledDialog.buildLoading("正在压缩图片").show();
                        }

                        @Override
                        public void onSuccess(File file) {
                            String absolutePath = file.getAbsolutePath();
                            path2.add(absolutePath);
                            mImagesAdapter.setNewData(path2);
                            if (mImagesAdapter.getData().size()<=3) {
                                addList.clear();
                                addList.addAll(mImagesAdapter.getData());
                                addList.add("");
                            }
                            addAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mImagesAdapter.setNewData(pathList);
                            if (mImagesAdapter.getData().size()<=3) {
                                addList.clear();
                                addList.addAll(mImagesAdapter.getData());
                                addList.add("");
                            }
                            addAdapter.notifyDataSetChanged();
                        }
                    }).launch();    //启动压缩

        }
    }
}
