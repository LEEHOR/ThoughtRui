package com.coahr.thoughtrui.Utils.JDBC;

import org.litepal.crud.DataSupport;

/**
 * Created by Leehor
 * on 2018/11/14
 * on 11:49
 */
public  interface JDBCSelectListener<T> {

      void Select(T t);

}
