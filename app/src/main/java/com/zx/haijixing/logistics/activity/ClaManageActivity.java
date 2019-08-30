package com.zx.haijixing.logistics.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.ClaManageAdapter;
import com.zx.haijixing.logistics.contract.ClaManageContract;
import com.zx.haijixing.logistics.entry.ClassManageEntry;
import com.zx.haijixing.logistics.entry.LinesManageEntry;
import com.zx.haijixing.logistics.presenter.ClassManageImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/16 16:32
 * @描述 班次管理
 */
@Route(path = PathConstant.CLASSES_MANAGE)
public class ClaManageActivity extends BaseActivity<ClassManageImp> implements ClaManageContract.ClaManageView,ClaManageAdapter.ClaManageResultListener {

    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.common_title_add)
    ImageView add;
    @BindView(R.id.cla_data)
    RecyclerView claData;

    @Autowired(name = "linesId")
    public String linesId;

    private ClaManageAdapter claManageAdapter;
    private List<ClassManageEntry> classManageEntries = new ArrayList<>();
    private String token;
    private CommonDialogFragment showDelete;
    private String bkId;
    private Map<String,String> params = new HashMap<>();

    @Override
    protected void initView() {

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token", "null");

        title.setText(getHaiString(R.string.classes_manage));
        add.setVisibility(View.VISIBLE);

        claData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        claManageAdapter = new ClaManageAdapter(classManageEntries,linesId);
        claManageAdapter.setActivity(this);
        claManageAdapter.setClaManageResultListener(this::claManageResult);
        claData.setAdapter(claManageAdapter);

        params.put("token",token);
        params.put("lineId",linesId);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.claManageMethod(params);
    }

    @Override
    protected void initInjector() {
        mPresenter = new ClassManageImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cla_manage;
    }

    @OnClick({R.id.common_title_back,R.id.common_title_add})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.common_title_back:
                finish();
                break;
            case R.id.common_title_add:
                ARouter.getInstance().build(PathConstant.ADD_CLASS)
                        .withString("linesId",linesId)
                        .withInt("from",OtherConstants.SKIP_ADD)
                        .withSerializable("editor",null)
                        .navigation(this,OtherConstants.REQUEST_ADD_CLASS);
                break;
            case R.id.dialog_update_yes:
                showDelete.dismissAllowingStateLoss();
                Map<String,String> param = new HashMap<>();
                param.put("token",token);
                param.put("bakkiId",bkId);
                param.put("timestamp",System.currentTimeMillis()+"");
                param.put("sign","");
                param.put("sign",HaiTool.sign(param));
                mPresenter.deleteClassMethod(param);
                break;
            case R.id.dialog_update_no:
                showDelete.dismissAllowingStateLoss();
                break;

        }

    }
    @Override
    public void claManageSuccess(List<ClassManageEntry> classManageEntries) {
        this.classManageEntries.addAll(classManageEntries);
        claManageAdapter.notifyDataSetChanged();
        if (this.classManageEntries.size() == 0)
            ZxToastUtil.centerToast("暂无可管理线路");
    }

    @Override
    public void deleteClassSuccess(String msg) {
        classManageEntries.clear();
        claManageAdapter.notifyDataSetChanged();
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.claManageMethod(params);
    }

    @Override
    public void claManageResult(String bkId,int tag) {
        this.bkId = bkId;
        showDelete = HaiDialogUtil.showUpdate(getSupportFragmentManager(), "是否确认删除？", this::onViewClicked);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*&& requestCode == OtherConstants.REQUEST_ADD_CLASS*/
        if (resultCode == RESULT_OK && requestCode == OtherConstants.REQUEST_ADD_CLASS){
            classManageEntries.clear();
            claManageAdapter.notifyDataSetChanged();
            params.put("timestamp",System.currentTimeMillis()+"");
            params.put("sign","");
            params.put("sign",HaiTool.sign(params));
            mPresenter.claManageMethod(params);
        }
    }
}
