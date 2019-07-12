package com.zx.haijixing.util;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zx.haijixing.driver.entry.BannerEntry;

import java.util.ArrayList;
import java.util.List;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/12 11:35
 *@描述 轮播图
 */
public final class BannerUtil {

    //初始化轮播图
    public static void initBannerScroll(Banner banner, List<BannerEntry.BannerData> bannerData, String bannerStr,OnBannerListener bannerListener) {

        List<String> img = new ArrayList<>();
        List<String> title = new ArrayList<>();
        for (BannerEntry.BannerData ba : bannerData){
            img.add(bannerStr+ba.getUrl());
            title.add(ba.getBannerId());
        }

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(ScrollLoaders.getInstance());
        //设置图片网址或地址的集合
        banner.setImages(img);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(title);
        //设置轮播间隔时间
        banner.setDelayTime(6000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(bannerListener)
                //必须最后调用的方法，启动轮播图。
                .start();
    }
}
