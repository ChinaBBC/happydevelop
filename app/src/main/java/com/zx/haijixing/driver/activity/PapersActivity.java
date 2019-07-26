package com.zx.haijixing.driver.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zx.haijixing.R;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/10 9:40
 * @描述 证件展示
 */
@Route(path = PathConstant.PAPERS)
public class PapersActivity extends BaseActivity {

    @BindView(R.id.papers_back)
    ImageView back;
    @BindView(R.id.papers_title)
    TextView title;
    @BindView(R.id.papers_change_word)
    TextView changeWord;
    @BindView(R.id.papers_change_img)
    ImageView changeImg;


    @BindView(R.id.papers_one)
    ImageView papersOne;
    @BindView(R.id.papers_two)
    ImageView papersTwo;
    @BindView(R.id.papers_three)
    ImageView papersThree;

    @Autowired(name = "title")
    public String strTitle;
    @Autowired(name = "one")
    public String one;
    @Autowired(name = "two")
    public String two;
    @Autowired(name = "three")
    public String three;
    @Autowired(name = "tag")
    public int tag;
    @Autowired(name = "truckId")
    public String truckId;

    @Override
    protected void initView() {
        title.setText(strTitle);
        RequestOptions options = new RequestOptions().error(R.mipmap.login_title_bg);
        if (ZxStringUtil.isEmpty(three)){
            papersThree.setVisibility(View.GONE);
        }else {
            Glide.with(this).load(three).apply(options).into(papersThree);
        }
        if (ZxStringUtil.isEmpty(two)){
            papersTwo.setVisibility(View.GONE);
        }else {
            Glide.with(this).load(two).apply(options).into(papersTwo);
        }
        if (ZxStringUtil.isEmpty(one)){
            ZxToastUtil.centerToast("暂时没有图片");
            papersOne.setVisibility(View.GONE);
        }else {
            Glide.with(this).load(one).apply(options).into(papersOne);
        }
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_papers;
    }

    @OnClick({R.id.papers_back, R.id.papers_change_word, R.id.papers_change_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.papers_back:
                finish();
                break;
            case R.id.papers_change_word:
            case R.id.papers_change_img:
                ARouter.getInstance().build(PathConstant.PAPERS_CHANGE)
                        .withString("title",strTitle)
                        .withString("one",one)
                        .withString("two",two)
                        .withString("three",three)
                        .withInt("tag",tag)
                        .withString("truckId",truckId)
                        .navigation();
                break;
        }
    }
}
