package com.lovejjfg.zhifou.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.presenters.DetailPresenter;
import com.lovejjfg.zhifou.presenters.DetailPresenterImpl;
import com.lovejjfg.zhifou.ui.widget.BottomSheet;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailStory1 extends AppCompatActivity implements DetailPresenter.View {
    /*mWeb = (WebView) findViewById(R.id.wbv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        mTittle = (TextView) findViewById(R.id.tv_tittle);*/
    @Bind(R.id.wbv)
    WebView mWeb;

    @Bind(R.id.bottom_sheet)
    BottomSheet bottomSheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story1);
        ButterKnife.bind(this);
        initView();
        DetailPresenter detailPresenter = new DetailPresenterImpl(this);
        detailPresenter.onLoading(getIntent().getIntExtra(Constants.ID, -1));
        bottomSheet.addListener(new BottomSheet.Listener() {
            @Override
            public void onDragDismissed() {
                finish();
            }

            @Override
            public void onDrag(int top) {

            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void initView() {



    }

    @Override
    public void isLoading(boolean isLoading) {

    }

    @Override
    public void isLoadingMore(boolean isLoadingMore) {

    }

    @Override
    public void onBindImage(String image) {
    }

    @Override
    public void onBindWebView(String data) {
        mWeb.loadDataWithBaseURL(Constants.BASE_URL, data, Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL);
    }

    @Override
    public void onBindTittle(String title) {
//        mTittle.setText(title);
    }

//    private RequestListener shotLoadListener = new RequestListener<String, GlideDrawable>() {
//        @Override
//        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//            return false;
//        }
//
//        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//        @Override
//        public boolean onResourceReady(GlideDrawable resource, String model,
//                                       Target<GlideDrawable> target, boolean isFromMemoryCache,
//                                       boolean isFirstResource) {
//            final Bitmap bitmap = GlideUtils.getBitmap(resource);
////            float imageScale = (float) mHeaderImage.getHeight() / (float) bitmap.getHeight();
//            float twentyFourDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
//                    getResources().getDisplayMetrics());
//            Palette.from(bitmap)
//                    .maximumColorCount(3)
//                    .clearFilters()
////                    .setRegion(0, 0, bitmap.getWidth() - 1, (int) (twentyFourDip / imageScale))
//                            // - 1 to work around https://code.google.com/p/android/issues/detail?id=191013
//                    .generate(new Palette.PaletteAsyncListener() {
//                        @Override
//                        public void onGenerated(Palette palette) {
//                            boolean isDark;
//                            @ColorUtils.Lightness int lightness = ColorUtils.isDark(palette);
//                            if (lightness == ColorUtils.LIGHTNESS_UNKNOWN) {
//                                isDark = ColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
//                            } else {
//                                isDark = lightness == ColorUtils.IS_DARK;
//                            }
//
////                            if (!isDark) { // make back icon dark on light images
////                                back.setColorFilter(ContextCompat.getColor(
////                                        DribbbleShot.this, R.color.dark_icon));
////                            }
//
//                            // color the status bar. Set a complementary dark color on L,
//                            // light or dark color on M (with matching status bar icons)
////                            int statusBarColor = getWindow().getStatusBarColor();
////                            Palette.Swatch topColor = ColorUtils.getMostPopulousSwatch(palette);
////                            if (topColor != null &&
////                                    (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
////                                statusBarColor = ColorUtils.scrimify(topColor.getRgb(),
////                                        isDark, SCRIM_ADJUSTMENT);
//                            // set a light status bar on M+
//                            if (!isDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                                ViewUtils.setLightStatusBar(mHeaderImage);
//                            }
//                        }
//
////                            if (statusBarColor != getWindow().getStatusBarColor()) {
////                                mHeaderImage.setScrimColor(statusBarColor);
////                                ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(getWindow
////                                        ().getStatusBarColor(), statusBarColor);
////                                statusBarColorAnim.addUpdateListener(new ValueAnimator
////                                        .AnimatorUpdateListener() {
////                                    @Override
////                                    public void onAnimationUpdate(ValueAnimator animation) {
////                                        getWindow().setStatusBarColor((int) animation
////                                                .getAnimatedValue());
////                                    }
////                                });
////                                statusBarColorAnim.setDuration(1000);
////                                statusBarColorAnim.setInterpolator(AnimationUtils
////                                        .loadInterpolator(DribbbleShot.this, android.R
////                                                .interpolator.fast_out_slow_in));
////                                statusBarColorAnim.start();
////                            }
//
//                    });
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
//                                    ContextCompat.getColor(DetailStory1.this, R.color.mid_grey),
//                                    true));
//                            mHeaderImage.setForeground(ViewUtils.createRipple(palette, 0.3f, 0.6f,
//                                    ContextCompat.getColor(DetailStory1.this, R.color.mid_grey),
//                                    true));
//                        }
//                    });
//            mHeaderImage.setBackground(null);
//            return false;
//
//        }
//    };
}
