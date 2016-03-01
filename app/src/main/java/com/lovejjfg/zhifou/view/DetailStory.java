package com.lovejjfg.zhifou.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.presenters.DetailPresenter;
import com.lovejjfg.zhifou.presenters.DetailPresenterImpl;

public class DetailStory extends AppCompatActivity implements DetailPresenter.View {

    private ImageView mHeaderImage;
    private WebView mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        initView();
        DetailPresenter detailPresenter = new DetailPresenterImpl(this);
        detailPresenter.onLoading(getIntent().getIntExtra(Constants.ID, -1));

    }

    private void initView() {
        mHeaderImage = (ImageView) findViewById(R.id.iv_header);
        mWeb = (WebView) findViewById(R.id.wv);
    }

    @Override
    public void isLoading(boolean isLoading) {

    }

    @Override
    public void isLoadingMore(boolean isLoadingMore) {

    }

    @Override
    public void onBindImage(String image) {
        mHeaderImage.setVisibility(View.VISIBLE);
        Glide.with(this).load(image).into(mHeaderImage);
    }

    @Override
    public void onBindWebView(String data) {
//        mWeb.loadData(data, Constants.MIME_TYPE, Constants.ENCODING);
//        mWeb.loadData(data, "text/html; charset=UTF-8", null);
        mWeb.loadDataWithBaseURL(Constants.BASE_URL, data, Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL);
    }
}
