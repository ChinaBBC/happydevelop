package com.zx.haijixing.logistics.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
import com.zx.haijixing.driver.fragment.CancelFragment;
import com.zx.haijixing.driver.fragment.CompleteFragment;
import com.zx.haijixing.driver.fragment.ReceivedFragment;
import com.zx.haijixing.driver.fragment.SendFragment;
import com.zx.haijixing.driver.fragment.SendingFragment;
import com.zx.haijixing.share.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @作者 zx
 * @创建日期 2019/7/16 10:18
 * @描述 物流中心
 */
public class LogisticsCenterFragment extends BaseFragment {
    @BindView(R.id.logistics_center_title)
    TextView title;
    @BindView(R.id.logistics_center_bottom)
    CustomBottomBar bottom;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_logistics_center;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        setTitleTopMargin(title,0);

        int titleSize = getResources().getDimensionPixelSize(R.dimen.sp_14);
        int titleMar = getResources().getDimensionPixelSize(R.dimen.dp_6);
        int iconSize = getResources().getDimensionPixelSize(R.dimen.dp_40);
        //初始化底部菜单栏
        bottom.setContainer(R.id.logistics_center_replace)
                .setTitleBeforeAndAfterColor("#666666", "#30703f")
                .setTitleSize(titleSize)
                .setTitleIconMargin(titleMar)
                .setIconHeight(iconSize)
                .setIconWidth(iconSize)
                .addItem(LogisticMoveFragment.class,
                        getHaiString(R.string.logistics_move),
                        R.mipmap.logistics_center_a,
                        R.mipmap.logistics_center_b)
                .addItem(LinesManageFragment.class,
                        getHaiString(R.string.lines_manage),
                        R.mipmap.lines_manage_a,
                        R.mipmap.lines_manage_b)
                .build();
    }
}
