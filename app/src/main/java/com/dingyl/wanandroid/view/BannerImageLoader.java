package com.dingyl.wanandroid.view;

import android.content.Context;
import android.widget.ImageView;

import com.dingyl.wanandroid.util.GlideUtil;
import com.youth.banner.loader.ImageLoader;

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideUtil.loadImage(context,(String)path,imageView);
    }
}
