package com.zx.haijixing.driver.activity;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.contract.NewsActivityContract;
import com.zx.haijixing.driver.presenter.NewsActivityImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = PathConstant.DRIVER_NEWS)
public class NewsActivity extends BaseActivity<NewsActivityImp> implements NewsActivityContract.NewDetailView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.news_detail_content)
    WebView content;
    @Autowired(name = "newId")
    public String newId;

    @Override
    protected void initView() {
        title.setText(R.string.news_detail);
        mPresenter.detailMethod(newId);
    }

    @Override
    protected void initInjector() {
        mPresenter = new NewsActivityImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void detailSuccess(String data) {
        content.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        content.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (content != null){
            content.removeAllViews();
            content.destroy();
        }
    }
}
