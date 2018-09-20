package com.dingyl.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BannerPageAdapter extends PagerAdapter{

    private ArrayList<View> viewContainer;

    public BannerPageAdapter(ArrayList<View> viewContainer){
        this.viewContainer = viewContainer;
    }

    @Override
    public int getCount() {
        if(viewContainer == null){
            return 0;
        }
        return viewContainer.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container,int position){
        ((ViewPager) container).addView(viewContainer.get(position));
        return viewContainer.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView(viewContainer.get(position));
    }
}
