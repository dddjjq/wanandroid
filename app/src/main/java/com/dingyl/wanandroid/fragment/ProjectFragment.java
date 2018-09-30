package com.dingyl.wanandroid.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.data.ProjData;
import com.dingyl.wanandroid.data.ProjDataBean;
import com.dingyl.wanandroid.adapter.ProjectViewPagerAdapter;
import com.dingyl.wanandroid.presenter.ProjPresenter;
import com.dingyl.wanandroid.util.Constants;
import com.dingyl.wanandroid.util.SharedPreferenceUtil;
import com.dingyl.wanandroid.util.ToastUtil;
import com.dingyl.wanandroid.view.BaseView;

import java.util.ArrayList;

public class ProjectFragment extends BaseFragment implements BaseView<ProjData>{

    private ProjPresenter presenter;
    private ArrayList<ProjDataBean> projDataBeanArrayList;
    private ArrayList<String> titles;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<ProjectEntryFragment> fragments;
    private ProjectViewPagerAdapter viewPagerAdapter;
    private FloatingActionButton linkTypeButton;
    private ToastUtil toastUtil;

    @Override
    protected void initAnim() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.project_fragment;
    }

    @Override
    protected void initView(View view) {
        viewPager = view.findViewById(R.id.project_viewpager);
        tabLayout = view.findViewById(R.id.project_tab);
        linkTypeButton = view.findViewById(R.id.link_type_button);
        int type = SharedPreferenceUtil.getInstance(getContext()).getProjectLinkType();
        if (type == Constants.TYPE_WEB){
            linkTypeButton.setImageResource(R.drawable.web);
        }else {
            linkTypeButton.setImageResource(R.drawable.github);
        }
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        toastUtil = new ToastUtil(getContext());
        viewPagerAdapter = new ProjectViewPagerAdapter(getFragmentManager(),titles,fragments);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setTabsFromPagerAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter = new ProjPresenter();
        presenter.attachView(this);
        presenter.getProjData();
        initListener();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.detachView();
    }
    @Override
    public void showSuccess(ProjData data) {
        projDataBeanArrayList = data.getData();
        for (ProjDataBean pdb : projDataBeanArrayList){
            titles.add(pdb.getName());
        }
        for (int i = 0;i<projDataBeanArrayList.size();i++){
            ProjectEntryFragment fragment = new ProjectEntryFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",projDataBeanArrayList.get(i).getId());
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading() {

    }

    private void initListener(){
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        linkTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = SharedPreferenceUtil.getInstance(getContext()).getProjectLinkType();
                if (type == Constants.TYPE_WEB){
                    SharedPreferenceUtil.getInstance(getContext()).setProjectLinkType(Constants.TYPE_GITHUB);
                    linkTypeButton.setImageResource(R.drawable.github);
                    toastUtil.makeText("项目地址");
                }else {
                    SharedPreferenceUtil.getInstance(getContext()).setProjectLinkType(Constants.TYPE_WEB);
                    linkTypeButton.setImageResource(R.drawable.web);
                    toastUtil.makeText("网页地址");
                }
            }
        });
    }
}
