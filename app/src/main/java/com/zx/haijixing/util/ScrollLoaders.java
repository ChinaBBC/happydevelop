package com.zx.haijixing.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;
import com.zx.haijixing.R;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/12 11:48
 *@描述 轮播图片加载
 */
public class ScrollLoaders extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions wel = new RequestOptions().error(R.mipmap.login_title_bg).placeholder(R.mipmap.login_title_bg).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(context).load((String) path).apply(wel).into(imageView);
    }

    private static class SingleLoader{
        private static final ScrollLoaders instance = new ScrollLoaders();
    }

    public static ScrollLoaders getInstance(){
        return SingleLoader.instance;
    }
}
