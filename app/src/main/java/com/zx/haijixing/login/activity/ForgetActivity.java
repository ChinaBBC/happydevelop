package com.zx.haijixing.login.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.IForgetActivityContract;
import com.zx.haijixing.login.presenter.ForgetActivityImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;
@Route(path = RoutePathConstant.ROUTE_FORGET)
public class ForgetActivity extends BaseActivity<ForgetActivityImp> implements IForgetActivityContract.ForgetView {

    @Override
    protected void initView() {

    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    public void findSuccess() {

    }
}
