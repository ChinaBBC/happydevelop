package com.zx.haijixing.login.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.IProtocolContract;
import com.zx.haijixing.login.presenter.ProtocolImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
@Route(path = PathConstant.PROTOCOL)
public class ProtocolActivity extends BaseActivity<ProtocolImp> implements IProtocolContract.ProtocolView {

    @BindView(R.id.protocol_content)
    WebView protocolContent;

    @Autowired(name = "type")
    public int type;
    @Override
    protected void initView() {
        //protocolContent.loadUrl(BuildConfig.homeUrl+OtherConstants.PROTOCOL_PATH);
        if (type == 1){
            mPresenter.protocolMethod();
        }else {
            mPresenter.privateMethod();
        }
    }

    @Override
    protected void initInjector() {
        mPresenter = new ProtocolImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_protocol;
    }

    @OnClick({R.id.protocol_back, R.id.protocol_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.protocol_back:
                finish();
                break;
            case R.id.protocol_agree:
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        protocolContent.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        protocolContent.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (protocolContent != null){
            protocolContent.removeAllViews();
            protocolContent.destroy();
        }
    }

    @Override
    public void protocolSuccess(String data) {
        protocolContent.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }

    @Override
    public void privateSuccess(String data) {
        protocolContent.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }
}
