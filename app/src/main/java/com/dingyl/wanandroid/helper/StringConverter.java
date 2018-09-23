package com.dingyl.wanandroid.helper;

import com.dingyl.wanandroid.data.HomeDataBean;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

public class StringConverter implements PropertyConverter<List<HomeDataBean.Tag>,String> {
    @Override
    public List<HomeDataBean.Tag> convertToEntityProperty(String databaseValue) {
        return new ArrayList<HomeDataBean.Tag>();
    }

    @Override
    public String convertToDatabaseValue(List<HomeDataBean.Tag> entityProperty) {
        return "";
    }

}
