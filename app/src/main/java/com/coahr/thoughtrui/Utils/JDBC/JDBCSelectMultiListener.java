package com.coahr.thoughtrui.Utils.JDBC;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/14
 * on 11:59
 */
public interface JDBCSelectMultiListener {

     <T>void SelectMulti(List<T> t);
}
