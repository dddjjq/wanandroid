package com.dingyl.wanandroid.util;

import com.dingyl.wanandroid.data.HomeDataDaoBean;
import com.dingyl.wanandroid.greendao.DaoSession;
import com.dingyl.wanandroid.greendao.HomeDataDaoBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HomeDataDaoUtil {

    public static void insertData(HomeDataDaoBean homeDataDaoBean){
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        HomeDataDaoBeanDao homeDataDaoBeanDao = daoSession.getHomeDataDaoBeanDao();
        homeDataDaoBeanDao.insert(homeDataDaoBean);
    }

    public static void insertDataList(ArrayList<HomeDataDaoBean> homeDataDaoBeans){
        if(homeDataDaoBeans == null || homeDataDaoBeans.isEmpty())
            return;
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        HomeDataDaoBeanDao homeDataDaoBeanDao = daoSession.getHomeDataDaoBeanDao();
        homeDataDaoBeanDao.insertInTx(homeDataDaoBeans);
    }

    public static void deleteData(HomeDataDaoBean homeDataDaoBean){
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        HomeDataDaoBeanDao homeDataDaoBeanDao = daoSession.getHomeDataDaoBeanDao();
        homeDataDaoBeanDao.delete(homeDataDaoBean);
    }

    public static void deleteAllData(){
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        HomeDataDaoBeanDao homeDataDaoBeanDao = daoSession.getHomeDataDaoBeanDao();
        homeDataDaoBeanDao.deleteAll();
    }

    public static void updateData(HomeDataDaoBean homeDataDaoBean){
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        HomeDataDaoBeanDao homeDataDaoBeanDao = daoSession.getHomeDataDaoBeanDao();
        homeDataDaoBeanDao.update(homeDataDaoBean);
    }

    public static List<HomeDataDaoBean> queryDataList(){
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        HomeDataDaoBeanDao homeDataDaoBeanDao = daoSession.getHomeDataDaoBeanDao();
        QueryBuilder<HomeDataDaoBean> qb = homeDataDaoBeanDao.queryBuilder();
        List<HomeDataDaoBean> list = qb.list();
        return list;
    }
}
