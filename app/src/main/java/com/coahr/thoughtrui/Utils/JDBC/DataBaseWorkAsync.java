package com.coahr.thoughtrui.Utils.JDBC;

import android.content.ContentValues;
import android.database.Cursor;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindCallback;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 李浩
 * 2018/5/7
 * 数据库操作工具类
 */
public class DataBaseWorkAsync {

    /*======================================添加===================================================*/

    /**
     * 批量添加
     *
     * @param list 集合
     * @param <T>
     */
    public static <T extends DataSupport> void DBSaveAll(final List<T> list, final JDBCSaveListener listener) {
        DataSupport.saveAllAsync(list).listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                if (listener != null) {
                    listener.save(success);
                }
            }
        });
    }


    /*======================================删除==================================*/

    /**
     * 根据Id单个删除
     *
     * @param modelClass 表名
     * @param id         删除的id
     * @param <T>        泛型
     * @return 删除个数
     */
    public static <T extends DataSupport> void DBDeleteById(Class<T> modelClass, long id, final JDBCDeleteListener listener) {
        DataSupport.deleteAsync(modelClass, id).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                if (listener != null) {
                    listener.delete(rowsAffected);
                }
            }
        });
    }

    /**
     * 根据条件删除
     *
     * @param modelClass 表名
     * @param conditions 条件：例如["name=? and age=?","张三"，"20"]
     * @param <T>
     * @return 删除的个数
     */
    public static <T extends DataSupport> void DBDeleteByConditions(Class<T> modelClass, final JDBCDeleteListener listener, String... conditions) {

      DataSupport.deleteAllAsync(modelClass, conditions).listen(new UpdateOrDeleteCallback() {
          @Override
          public void onFinish(int rowsAffected) {
              if (listener != null) {
                  listener.delete(rowsAffected);
              }
          }
      });
    }

    /**
     * 清空数据库
     *
     * @param modelClass
     * @param <T>
     * @return 删除个数
     */
    public static <T extends DataSupport> void DBDeleteByAll(Class<T> modelClass, final JDBCDeleteListener listener) {
      DataSupport.deleteAllAsync(modelClass).listen(new UpdateOrDeleteCallback() {
          @Override
          public void onFinish(int rowsAffected) {
              if (listener != null) {
                  listener.delete(rowsAffected);
              }
          }
      });
    }

    /*====================================修改=================================================*/

    /**
     * 创建ContentValues根据Id修改
     * 例如：
     * ContentValues values = new ContentValues();
     * values.put("字段名", "值");
     * DataSupport.update(表名, values, id);
     *
     * @param modelClass 表名
     * @param values     要修改的列表和值
     * @param id         要修改的ID
     * @param <T>
     * @return
     */
    public static <T extends DataSupport> void DBUpdateById(Class<T> modelClass, ContentValues values, Long id, final JDBCUpdateListener listener) {

        DataSupport.updateAsync(modelClass, values, id).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                if (listener != null) {
                    listener.UpdateById(rowsAffected);
                }
            }
        });
    }

    /**
     * 创建ContentValues条件修改
     * 例如：
     * ContentValues values = new ContentValues();
     * values.put("字段名1", "值1");
     * DataSupport.updateAll((表名, values, "字段名1= ?", "值");
     * 或者多条件
     * DataSupport.updateAll((表名, values, "字段名1= ?and 字段名2=？", "值1","值2");
     *
     * @param modelClass 表名
     * @param values     要修改的列表和值
     * @param conditions
     * @param <T>
     * @return
     */
    public static <T extends DataSupport> void DBUpdateByConditions(Class<T> modelClass, final JDBCUpdateListener listener, ContentValues values, String... conditions) {
        DataSupport.updateAllAsync(modelClass, values, conditions).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                if (listener != null) {
                    listener.UpdateById(rowsAffected);
                }
            }
        });
    }

    /**
     * 把表中的某一字段全部修改为
     *
     * @param modelClass 表名
     * @param values     ContentValues
     * @param <T>        泛型
     * @return 返回修改的个数
     */
    public static <T extends DataSupport> int DBUpdateAll(Class<T> modelClass, ContentValues values) {
        int i = DataSupport.updateAll(modelClass, values);
        return i;
    }
    /*=====================================查询==============================================*/

    /**
     * 根据Id单条查询
     *
     * @param modelClass 表名
     * @param id         查询的ID
     * @param <T>        泛型
     * @return 返回结果
     */
    public static <T extends DataSupport> void DBSelectById(final Class<T> modelClass, long id, final JDBCSelectListener listener) {
        DataSupport.findAsync(modelClass, id).listen(new FindCallback() {
            @Override
            public <T> void onFinish( T t) {
                if (listener != null) {
                    listener.Select(t);
                }
            }
        });

    }




    /**
     * 查询表中第一条数据
     * @param modelClass
     *              表名
     * @param <T>
     *            泛型
     * @return
     *          对应对象
     */
    public static <T extends DataSupport> T DBSelectByFirst(Class<T> modelClass){
            T first = DataSupport.findFirst(modelClass);
            return first;
        }

    /**
     * 查询表中最后一条数据
      * @param modelClass
     *              表名
     * @param <T>
     *              泛型
     * @return
     *              返回结果
     */
    public static <T extends DataSupport> T DBSelectByLast(Class<T> modelClass){
            T last = DataSupport.findLast(modelClass);
            return last;
     }

    /**
     * 根据id多条数据查询
     * sql语句：select * from 表名 where id=?,?,?...;
     * @param modelClass
     *          表名
     * @param ids
     *          ID数组
     * @param <T>
     *          泛型
     * @return
     *          返回结果
     */
     public static <T extends DataSupport> List<T> DBSelectByIds(Class<T> modelClass, List<Integer> ids){
         List<T> lists=new ArrayList<T>();
         for (int i = 0; i < ids.size(); i++) {
             T t = DataSupport.find(modelClass, ids.get(i));
             lists.add(t);
         }
         return lists;
     }

    public static <T extends DataSupport> List DBSelectByColumnName(Class<T> modelClass, String... ColumuName){
        List<T> ts =  DataSupport.select(ColumuName).find(modelClass);
        return ts;
    }

    /**条件语句全查询
     * 例如
     * select * from 表名 where 条件语句;
     * @param modelClass
     *                 表名
     * @param where
     *
     * @param <T>
     * @return
     */
    public static <T extends DataSupport> void DBSelectByTogether_Where(Class<T> modelClass, final JDBCSelectMultiListener listener, String... where){
         DataSupport.where(where).findAsync(modelClass).listen(new FindMultiCallback() {
             @Override
             public <T> void onFinish(List<T> t) {
                 if (listener != null) {
                     listener.SelectMulti(t);
                 }
             }
         });
    }

    /**字段和条件语句查询
     * 例如
     * select 字段1,字段2 from 表名 where 条件;
     * @param modelClass
     *              表名
     * @param column_name
     *              要查询字段名
     * @param where
     *              where条件句
     * @param <T>
     *               泛型
     * @return
     *              对应结果
     */
    public static <T extends DataSupport> void DBSelectBy_Where(Class<T> modelClass, final JDBCSelectMultiListener listener , String[] column_name, String... where){
        DataSupport.select(column_name).where(where).findAsync(modelClass).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                if (listener != null) {
                    listener.SelectMulti(t);
                }
            }
        });

    }

    /**
     * 查询表中所有数据
     * sql语句：select * from  表名
     * @param modelClass
     *                  表名（类）
     * @param <T>
     *                  泛型
     * @return
     *                  对应结果
     */
    public static <T extends DataSupport> List<T> DBSelectAll(Class<T> modelClass){
        List<T> all = DataSupport.findAll(modelClass);
        return all;
    }




    /**
     *order多条件查询
     * 例如
     * DataSupport.select(String [] 字段名)
     *.where(String[] 条件）
     *.find(表名);
     * sql语句：select 字段名 from 表名 where  条件 order by 排序字段+排序方式;
     * @param modelClass
     *              表名
     * @param where
     *              where条件
     * @param column_name
     *              要查询字段名
     *@param  order
     *             字段+desc/asc(正序或倒序)
     * @param <T>
     *              泛型
     * @return
     *              对应结果
     */
     public static <T extends DataSupport> List<T> DBSelectBy_order(Class<T> modelClass, String[] where , String order, String... column_name){
         List<T> ts = DataSupport.select(column_name)
                 .where(where)
                 .order(order)
                 .find(modelClass);
         return ts;
     }

    /**
     * order多条件全查询
     *
     * DataSupport.where(String[] 条件）.order(字段名).find(表名);
     *
     * sql语句：select * from 表名 where  条件 order by 条件 desc/asc;
     * @param modelClass
     *          表名
     * @param where
     *           条件
     * @param order
     *           字段+desc/asc(正序或倒序)
     * @param <T>
     * @return
     */
    public static <T extends DataSupport> List<T> DBSelectByTogether_order(Class<T> modelClass, String[] where , String order){
        List<T> ts = DataSupport
                .where(where)
                .order(order)
                .find(modelClass);
        return ts;
    }

    /**
     * limit多条件全查询
     * DataSupport.where(条件).order (排序字段+排序方式).limit(界限).find(表名);
     * sql语句：select * from 表名 where 查询条件 order by 排序条件 limit 界限
     * @param modelClass
     *              表名
     * @param order
     *               字段+desc/asc(正序或倒序)
     * @param limit
     *               界限0 - limit
     * @param where
     *                查询条件
     * @param <T>
     *                泛型
     * @return
     *                返回结果
     */
    public static <T extends DataSupport> List<T> DBSelectByTogether_limit(Class<T> modelClass, String order, int  limit, String... where){
        List<T> ts = DataSupport
                .where(where)
                .order(order).limit(limit)
                .find(modelClass);
        return ts;
    }

    /**
     * limit 多条件查询
     * DataSupport.select（查询字段）.where(查询条件).order (排序字段+排序方式).limit(界限).find(表名);
     * limit多条件全查询
     * sql语句：select 查询字段 from 表名 where 查询条件 order by 排序条件 limit 界限
     * @param modelClass
     *              表名
     * @param order
     *               字段+desc/asc(正序或倒序)
     * @param limit
     *               界限0 - limit
     * @param where
     *                查询条件
     * @param column_name
     *                 查询字段
     * @param <T>
     *                泛型
     * @return
     *                返回结果
     */
    public static <T extends DataSupport> List<T> DBSelectBy_limit(Class<T> modelClass, String[] where, String order, int  limit, String... column_name ){
        List<T> ts = DataSupport.select(column_name)
                .where(where)
                .order(order).limit(limit)
                .find(modelClass);
        return ts;
    }

    /**
     * * offset多条件全查询（分页查询）
     * 例如
     *  DataSupport
     * .where(String[] 条件）
     * .order(排序).limit().offset()
     * .find(表名);
     * sql语句：select * from 表名 where 条件 order by 字段 desc limit 0,0;
     * @param modelClass
     *              表名（类）
     * @param order
     *              字段+desc/asc(正序或倒序)
     * @param limit
     *              界限0 - limit
     * @param offset
     *              偏移量（分页查询）可为null
     * @param where
     *               查询条件
     * @param <T>
     *              泛型
     * @return
     *              对应结果
     */
    public static <T extends DataSupport> List<T> DBSelectByTogether_offset(Class<T> modelClass, String[] where, String order, int limit, int offset){
        List<T> ts = DataSupport
                .where(where)
                .order(order).limit(limit).offset(offset)
                .find(modelClass);
        return ts;
    }


    /**
     * offset多条件查询（分页查询）
     * 例如
     *  DataSupport.select（查询字段）
     * .where(String[] 条件）
     * .order(排序).limit().offset()
     * .find(表名);
     * sql语句：select 查询字段 from 表名 where 条件 order by 字段 desc limit 0,0;
     *  @param modelClass
     *              表名（类）
     * @param order
     *              字段+desc/asc(正序或倒序)
     * @param limit
     *              界限0 - limit
     * @param offset
     *              偏移量（分页查询）可为null
     * @param where
     *               查询条件
     * @param column_name
     *               查询字段
     * @param <T>
     *              泛型
     * @return
     *              对应结果
     */
    public static <T extends DataSupport> List<T> DBSelectBy_offset(Class<T> modelClass, String[] where, String order, int limit, int offset, String... column_name){
        List<T> ts = DataSupport.select(column_name)
                .where(where)
                .order(order).limit(limit).offset(offset)
                .find(modelClass);
        return ts;
    }
    /**
     * 根据编写的Sql语句查询
     * @param sql
     *          sql语句
     * @param <T>
     * @return
     *           对应结果
     */
    public static <T extends DataSupport> Cursor DBSelectBySQL(String sql){
        Cursor bySQL = DataSupport.findBySQL(sql);
        return bySQL;
    }
}
