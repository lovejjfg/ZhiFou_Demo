package com.lovejjfg.zhifou.util.glide;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by Joe on 2016-06-06
 * Email: lovejjfg@163.com
 */

/**
 * Copyright (C) 2015 Wasabeef
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class ColorFilterTransformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;

    private int mColor;

    public ColorFilterTransformation(Context context, int color) {
        this(Glide.get(context).getBitmapPool(), color);
    }

    public ColorFilterTransformation(BitmapPool pool, int color) {
        mBitmapPool = pool;
        mColor = color;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Log.e("TAG", "transform: ");
        Bitmap source = resource.get();

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap.Config config =
                source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = mBitmapPool.get(width, height, config);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, config);
        }

        Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColorFilter(new PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_ATOP));

        final ObservableColorMatrix cm = new ObservableColorMatrix();
        final ObjectAnimator saturation = ObjectAnimator.ofFloat(
                cm, ObservableColorMatrix.SATURATION, 0f, 1f);
        saturation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener
                () {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // just animating the color matrix does not invalidate the
                // drawable so need this update listener.  Also have to create a
                // new CMCF as the matrix is immutable :(
                Log.e("TAG--->", "onAnimationUpdate: "+cm.getSaturation());

                paint.setColorFilter(new ColorMatrixColorFilter(cm));
//                holder.image.setColorFilter(new ColorMatrixColorFilter(cm));
            }
        });
        saturation.setDuration(20000L);
//        saturation.setInterpolator(getFastOutSlowInInterpolator(host));
        saturation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("TAG--->", "onAnimationUpdate: END");
//                paint.();
//                holder.image.setHasTransientState(false);
            }
        });
        saturation.start();


//        paint.setColorFilter(new PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_ATOP));

        canvas.drawBitmap(source, 0, 0, paint);

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "ColorFilterTransformation(color=" + mColor + ")";
    }
}
