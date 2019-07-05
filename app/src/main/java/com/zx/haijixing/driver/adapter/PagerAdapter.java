package com.zx.haijixing.driver.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/5 11:19
 *@描述 订单中心
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> titles;
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerAdapter(FragmentManager fm, List<Fragment> list,  List<String> titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
