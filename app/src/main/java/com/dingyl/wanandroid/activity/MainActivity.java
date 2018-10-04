package com.dingyl.wanandroid.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.fragment.BaseFragment;
import com.dingyl.wanandroid.fragment.HomeFragment;
import com.dingyl.wanandroid.fragment.KnowledgeFragment;
import com.dingyl.wanandroid.fragment.ProjectFragment;
import com.dingyl.wanandroid.fragment.UserFragment;
import com.dingyl.wanandroid.helper.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationView navigationView;
    private List<BaseFragment> fragmentList;
    private HomeFragment homeFragment;
    private KnowledgeFragment knowledgeFragment;
    private ProjectFragment projectFragment;
    private UserFragment userFragment;
    private FrameLayout container;
    private int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        initView();
        initData();
        initListener();
    }

    private void initView(){
        navigationView = findViewById(R.id.navigation_view);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
    }

    private void initListener(){
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        currentPage = 0;
                        showHideFragment(currentPage);
                        break;
                    case R.id.navigation_know:
                        currentPage = 1;
                        showHideFragment(currentPage);
                        break;
                    case R.id.navigation_proj:
                        currentPage = 2;
                        showHideFragment(currentPage);
                        break;
                    case R.id.navigation_user:
                        currentPage = 3;
                        showHideFragment(currentPage);
                        break;
                }
                return true;
            }
        });
    }

    private void initData(){
        fragmentList = new ArrayList<>();
        homeFragment = new HomeFragment();
        knowledgeFragment = new KnowledgeFragment();
        projectFragment = new ProjectFragment();
        userFragment = new UserFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(knowledgeFragment);
        fragmentList.add(projectFragment);
        fragmentList.add(userFragment);
        loadMultipleRootFragment(R.id.container,0,homeFragment,knowledgeFragment
            ,projectFragment,userFragment);
    }

    private void showHideFragment(int currentPage){
        for(int i=0;i < fragmentList.size();i++){
            if(currentPage != i){
                showHideFragment(fragmentList.get(currentPage),fragmentList.get(i));
            }
        }
    }

}
