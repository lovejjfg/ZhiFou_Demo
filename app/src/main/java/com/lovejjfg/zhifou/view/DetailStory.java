package com.lovejjfg.zhifou.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.presenters.DetailPresenter;
import com.lovejjfg.zhifou.presenters.DetailPresenterImpl;
import com.lovejjfg.zhifou.ui.widget.ElasticDragDismissFrameLayout;
import com.lovejjfg.zhifou.ui.widget.ForegroundImageView;
import com.lovejjfg.zhifou.ui.widget.ParallaxScrimageView;
import com.lovejjfg.zhifou.util.AnimUtils;
import com.lovejjfg.zhifou.util.ColorUtils;
import com.lovejjfg.zhifou.util.GlideUtils;
import com.lovejjfg.zhifou.util.ViewUtils;
import com.lovejjfg.zhifou.util.glide.ColorFilterTransformation;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailStory extends AppCompatActivity implements DetailPresenter.View {
//    @Bind(R.id.container)
//    CoordinatorLayout parent;
//    @Bind(R.id.app_bar)
//    AppBarLayout appBarLayout;
    @Bind(R.id.wbv)
    WebView mWeb;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.tv_tittle)
//    TextView mTittle;
//    @Bind(R.id.fl_container)
//    FrameLayout container;

    @Bind(R.id.scroll_view)
    NestedScrollView mScrollView;
    @Bind(R.id.parent_container)
    ElasticDragDismissFrameLayout dragDismissFrameLayout;

    @Bind(R.id.iv_header)
    ParallaxScrimageView mHeaderImage;
    private DetailPresenter detailPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story1);
        ButterKnife.bind(this);
        initView();
        detailPresenter = new DetailPresenterImpl(this);
        detailPresenter.onLoading(getIntent().getIntExtra(Constants.ID, -1));
        dragDismissFrameLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        dragDismissFrameLayout.addListener(new ElasticDragDismissFrameLayout.SystemChromeFader(getWindow()) {
            @Override
            public void onDragDismissed() {
                expandImageAndFinish();
            }
        });
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("TAG", "onScrollChange: "+scrollY);
                mHeaderImage.setOffset(-scrollY);
            }
        });
    }

    @Override
    public void onBackPressed() {
        expandImageAndFinish();
    }

    @Override
    public boolean onNavigateUp() {
        expandImageAndFinish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.onDestroy();
    }

    private void expandImageAndFinish() {
        if (mHeaderImage.getOffset() != 0f) {
            Animator expandImage = ObjectAnimator.ofFloat(mHeaderImage, ParallaxScrimageView.OFFSET,
                    0f);
            expandImage.setDuration(80);
            expandImage.setInterpolator(AnimUtils.getFastOutSlowInInterpolator(getApplicationContext()));
            expandImage.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    finishAfterTransition();
                }
            });
            expandImage.start();
        } else {
            finishAfterTransition();
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void initView() {

//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DetailStory.this.finishAfterTransition();
//            }
//        });
//        int totalScrollRange = appBarLayout.getTotalScrollRange();
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Log.i("TAG", "onOffsetChanged: " + verticalOffset);
//                boolean expanded = verticalOffset == 0;
//            }
//        });

    }

    @Override
    public void isLoading(boolean isLoading) {
        Log.i("TAG", "isLoading: "+isLoading);
    }

    @Override
    public void isLoadingMore(boolean isLoadingMore) {

    }

    @Override
    public void onBindImage(String image) {
        Glide.with(getApplicationContext())
                .load(image)
//                .listener(shotLoadListener)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .priority(Priority.IMMEDIATE)
//                .thumbnail(0.1f)
//                .fitCenter()
//                .bitmapTransform()
//                .bitmapTransform(new ColorFilterTransformation(getApplicationContext(), Color.RED))
                .into(mHeaderImage);

        mHeaderImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindWebView(String data) {
        mWeb.loadDataWithBaseURL(Constants.BASE_URL, data, Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL);
    }

    @Override
    public void onBindTittle(String title) {
//        mTittle.setText(title);
    }

    private RequestListener shotLoadListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean onResourceReady(GlideDrawable resource, String model,
                                       Target<GlideDrawable> target, boolean isFromMemoryCache,
                                       boolean isFirstResource) {
            final Bitmap bitmap = GlideUtils.getBitmap(resource);
            float imageScale = (float) mHeaderImage.getHeight() / (float) bitmap.getHeight();
            float twentyFourDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                    getResources().getDisplayMetrics());
            Palette.from(bitmap)
                    .maximumColorCount(3)
                    .clearFilters()
                    .setRegion(0, 0, bitmap.getWidth() - 1, (int) (twentyFourDip / imageScale))
                            // - 1 to work around https://code.google.com/p/android/issues/detail?id=191013
                    .generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            boolean isDark;
                            @ColorUtils.Lightness int lightness = ColorUtils.isDark(palette);
                            if (lightness == ColorUtils.LIGHTNESS_UNKNOWN) {
                                isDark = ColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
                            } else {
                                isDark = lightness == ColorUtils.IS_DARK;
                            }

//                            if (!isDark) { // make back icon dark on light images
//                                back.setColorFilter(ContextCompat.getColor(
//                                        DribbbleShot.this, R.color.dark_icon));
//                            }

                            // color the status bar. Set a complementary dark color on L,
                            // light or dark color on M (with matching status bar icons)
//                            int statusBarColor = getWindow().getStatusBarColor();
//                            Palette.Swatch topColor = ColorUtils.getMostPopulousSwatch(palette);
//                            if (topColor != null &&
//                                    (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
//                                statusBarColor = ColorUtils.scrimify(topColor.getRgb(),
//                                        isDark, SCRIM_ADJUSTMENT);
                            // set a light status bar on M+
                            if (!isDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                ViewUtils.setLightStatusBar(mHeaderImage);
                            }
                        }

//                            if (statusBarColor != getWindow().getStatusBarColor()) {
//                                mHeaderImage.setScrimColor(statusBarColor);
//                                ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(getWindow
//                                        ().getStatusBarColor(), statusBarColor);
//                                statusBarColorAnim.addUpdateListener(new ValueAnimator
//                                        .AnimatorUpdateListener() {
//                                    @Override
//                                    public void onAnimationUpdate(ValueAnimator animation) {
//                                        getWindow().setStatusBarColor((int) animation
//                                                .getAnimatedValue());
//                                    }
//                                });
//                                statusBarColorAnim.setDuration(1000);
//                                statusBarColorAnim.setInterpolator(AnimationUtils
//                                        .loadInterpolator(DribbbleShot.this, android.R
//                                                .interpolator.fast_out_slow_in));
//                                statusBarColorAnim.start();
//                            }

                    });
//            Palette.from(bitmap)
//                    .clearFilters() // by default palette ignore certain hues (e.g. pure
//                            // black/white) but we don't want this.
//                    .generate(new Palette.PaletteAsyncListener() {
//                        @TargetApi(Build.VERSION_CODES.M)
//                        @Override
//                        public void onGenerated(Palette palette) {
//                            // color the ripple on the image spacer (default is grey)
//                            // slightly more opaque ripple on the pinned image to compensate
//                            // for the scrim
//                            container.setBackground(ViewUtils.createRipple(palette, 0.3f, 0.6f,
//                                    ContextCompat.getColor(DetailStory.this, R.color.mid_grey),
//                                    true));
//                            mHeaderImage.setForeground(ViewUtils.createRipple(palette, 0.3f, 0.6f,
//                                    ContextCompat.getColor(DetailStory.this, R.color.mid_grey),
//                                    true));
//                        }
//                    });
            mHeaderImage.setBackground(null);
            return false;

        }
    };
}
