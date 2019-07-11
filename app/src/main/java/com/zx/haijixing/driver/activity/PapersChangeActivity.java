package com.zx.haijixing.driver.activity;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 10:47
 *@描述 修改证件
 */
@Route(path = RoutePathConstant.PAPERS_CHANGE)
public class PapersChangeActivity extends BaseActivity {

    @BindView(R.id.papers_change_back)
    ImageView back;
    @BindView(R.id.papers_change_title)
    TextView title;
    @BindView(R.id.papers_change_cancel)
    TextView cancel;
    @BindView(R.id.papers_change_one)
    ImageView one;
    @BindView(R.id.papers_change_con2)
    ConstraintLayout con2;
    @BindView(R.id.papers_change_two)
    ImageView two;
    @BindView(R.id.papers_change_con3)
    ConstraintLayout con3;
    @BindView(R.id.papers_change_three)
    ImageView three;
    @BindView(R.id.papers_change_con4)
    ConstraintLayout con4;
    @BindView(R.id.papers_change_save)
    Button save;

    @Override
    protected void initView() {
        title.setText("行驶证");
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_papers_change;
    }

    @OnClick({R.id.papers_change_back, R.id.papers_change_cancel, R.id.papers_change_con2, R.id.papers_change_con3, R.id.papers_change_con4, R.id.papers_change_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.papers_change_back:
                finish();
                break;
            case R.id.papers_change_cancel:
                finish();
                break;
            case R.id.papers_change_con2:
                break;
            case R.id.papers_change_con3:
                break;
            case R.id.papers_change_con4:
                break;
            case R.id.papers_change_save:
                break;
        }
    }
}
