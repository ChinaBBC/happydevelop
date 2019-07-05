package com.zx.haijixing.driver.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.PagerAdapter;
import com.zx.haijixing.driver.fragment.CancelFragment;
import com.zx.haijixing.driver.fragment.CompleteFragment;
import com.zx.haijixing.driver.fragment.ReceivedFragment;
import com.zx.haijixing.driver.fragment.SendFragment;
import com.zx.haijixing.driver.fragment.SendingFragment;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/5 10:15
 * @描述 订单中心
 */
@Route(path = RoutePathConstant.DRIVER_ORDER)
public class OrderActivity extends BaseActivity {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.order_center_tab)
    TabLayout tab;
    @BindView(R.id.order_center_pager)
    ViewPager pager;

    //fragment列表
    private List<Fragment> fragmentList = new ArrayList<>();
    //TabLayout标签
    private List<String> titles = new ArrayList<>();
    private PagerAdapter pagerAdapter;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.order_center));

        titles.add(getHaiString(R.string.wait_receive));
        titles.add(getHaiString(R.string.wait_send));
        titles.add(getHaiString(R.string.sending));
        titles.add(getHaiString(R.string.complete));
        titles.add(getHaiString(R.string.has_cancel));

        //设置tablayout为平分
        tab.setTabMode(TabLayout.MODE_FIXED);
        //设置tablayout的标题
        for (String title : titles){
            tab.addTab(tab.newTab().setTag(title));
        }
        //tab.setOnTabSelectedListener(this);
        //添加fragment
        fragmentList.add(new ReceivedFragment());
        fragmentList.add(new SendFragment());
        fragmentList.add(new SendingFragment());
        fragmentList.add(new CompleteFragment());
        fragmentList.add(new CancelFragment());
        //初始化适配器
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),fragmentList,titles);
        //绑定适配器
        pager.setAdapter(pagerAdapter);
        //绑定viewpager
        tab.setupWithViewPager(pager);

        pager.setOffscreenPageLimit(2);
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }
}
