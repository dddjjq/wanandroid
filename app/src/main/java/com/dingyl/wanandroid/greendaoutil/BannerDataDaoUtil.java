package com.dingyl.wanandroid.greendaoutil;

import com.dingyl.wanandroid.data.BannerDataBean;
import com.dingyl.wanandroid.greendao.BannerDataBeanDao;
import com.dingyl.wanandroid.greendao.DaoSession;
import com.dingyl.wanandroid.util.MyApplication;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class BannerDataDaoUtil {

    private static BannerDataDaoUtil instance;
    private DaoSession daoSession;
    private BannerDataBeanDao dao;

    private BannerDataDaoUtil(){
        daoSession = MyApplication.getInstance().getDaoSession();
        dao = daoSession.getBannerDataBeanDao();
    }

    public static BannerDataDaoUtil getInstance() {
        if (instance == null){
            instance = new BannerDataDaoUtil();
        }
        return instance;
    }

    public void insertData(BannerDataBean data){
        dao.insert(data);
    }

    public void insertDataList(ArrayList<BannerDataBean> data){
        if(data == null || data.isEmpty())
            return;
        dao.insertInTx(data);
    }

    public void deleteData(BannerDataBean data){
        dao.delete(data);
    }

    public void deleteAllData(){
        if (queryDataList() != null)
            dao.deleteAll();
    }

    public void updateData(BannerDataBean data){
        dao.update(data);
    }

    public List<BannerDataBean> queryDataList(){
        QueryBuilder<BannerDataBean> qb = dao.queryBuilder();
        List<BannerDataBean> list = qb.list();
        return list;
    }
}
