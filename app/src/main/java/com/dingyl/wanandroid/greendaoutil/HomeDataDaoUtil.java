package com.dingyl.wanandroid.greendaoutil;

import com.dingyl.wanandroid.data.HomeDataBean;
import com.dingyl.wanandroid.greendao.DaoSession;
import com.dingyl.wanandroid.greendao.HomeDataBeanDao;
import com.dingyl.wanandroid.util.MyApplication;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HomeDataDaoUtil {

    private static HomeDataDaoUtil instance;
    private DaoSession daoSession;
    private HomeDataBeanDao dao;

    private HomeDataDaoUtil(){
        daoSession = MyApplication.getInstance().getDaoSession();
        dao = daoSession.getHomeDataBeanDao();
    }

    public static HomeDataDaoUtil getInstance() {
        if (instance == null){
            instance = new HomeDataDaoUtil();
        }
        return instance;
    }

    public void insertData(HomeDataBean data){
        dao.insert(data);
    }

    public void insertDataList(ArrayList<HomeDataBean> data){
        if(data == null || data.isEmpty())
            return;
        dao.insertInTx(data);
    }

    public void deleteData(HomeDataBean data){
        if (queryDataList() != null)
            dao.delete(data);
    }

    public void deleteAllData(){
        dao.deleteAll();
    }

    public void updateData(HomeDataBean data){
        dao.update(data);
    }

    public List<HomeDataBean> queryDataList(){
        QueryBuilder<HomeDataBean> qb = dao.queryBuilder();
        List<HomeDataBean> list = qb.list();
        return list;
    }
}
