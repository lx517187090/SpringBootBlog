package com.example.springbootblog.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 张忠亮 on 2015/6/8.
 */
public class SqlUtil {

    public static final String DATABSE_TYPE_MYSQL = "mysql";
    public static final String DATABSE_TYPE_POSTGRE = "postgresql";
    public static final String DATABSE_TYPE_ORACLE = "oracle";
    public static final String DATABSE_TYPE_SQLSERVER = "sqlserver";

    /**
     * 分页SQL
     */
    public static final String MYSQL_SQL = "select * from ( {0}) sel_tab00 limit {1},{2}";         //mysql
    public static final String POSTGRE_SQL = "select * from ( {0}) sel_tab00 limit {2} offset {1}";//postgresql
    public static final String ORACLE_SQL = "select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}"; //oracle
    public static final String SQLSERVER_SQL = "select * from ( select row_number() over(order by tempColumn) tempRowNumber, * from (select top {1} tempColumn = 0, {0}) t ) tt where tempRowNumber > {2}"; //sqlserver

    /**
     * 获取所有表的SQL
     */
    public static final String MYSQL_ALLTABLES_SQL = "select distinct table_name from information_schema.columns where table_schema = {0}";
    public static final String POSTGRE__ALLTABLES_SQL = "SELECT distinct c.relname AS  table_name FROM pg_class c";
    public static final String ORACLE__ALLTABLES_SQL = "select distinct colstable.table_name as  table_name from user_tab_cols colstable";
    public static final String SQLSERVER__ALLTABLES_SQL = "select distinct c.name as  table_name from sys.objects c";

    /**
     * 获取指定表的所有列名
     */
    public static final String MYSQL_ALLCOLUMNS_SQL = "select column_name from information_schema.columns where table_name = {0} and table_schema = {1}";
    public static final String POSTGRE_ALLCOLUMNS_SQL = "select table_name from information_schema.columns where table_name = {0}";
    public static final String ORACLE_ALLCOLUMNS_SQL = "select column_name from all_tab_columns where table_name ={0}";
    public static final String SQLSERVER_ALLCOLUMNS_SQL = "select name from syscolumns where id={0}";

    /**
     * 获取全sql
     *
     * @param sql
     * @param params
     * @return
     */
    public static String getFullSql(String sql, Map params) {
        StringBuilder sqlB = new StringBuilder();
        sqlB.append("SELECT t.* FROM ( ");
        sqlB.append(sql + " ");
        sqlB.append(") t ");
        if (params != null && params.size() >= 1) {
            sqlB.append("WHERE 1=1  ");
            Iterator it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                String value = String.valueOf(params.get(key));
                if (!StringUtils.isEmpty(value) && !"null".equals(value)) {
                    sqlB.append(" AND ");
                    sqlB.append(" " + key + value);
                }
            }
        }
        return sqlB.toString();
    }

    /**
     * 获取求数量sql
     *
     * @param sql
     * @param params
     * @return
     */
    public static String getCountSql(String sql, Map params) {
        String querySql = getFullSql(sql, params);
        querySql = "SELECT COUNT(*) FROM (" + querySql + ") t2";
        return querySql;
    }


    private static int getAfterSelectInsertPoint(String sql) {
        int selectIndex = sql.toLowerCase().indexOf("select");
        int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
        return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
    }

    public static String getAllTableSql(String dbType, String... param) {
        if (StringUtils.isNotEmpty(dbType)) {
            if (dbType.equals(DATABSE_TYPE_MYSQL)) {
                return MessageFormat.format(MYSQL_ALLTABLES_SQL, param);
            } else if (dbType.equals(DATABSE_TYPE_ORACLE)) {
                return ORACLE__ALLTABLES_SQL;
            } else if (dbType.equals(DATABSE_TYPE_POSTGRE)) {
                return POSTGRE__ALLTABLES_SQL;
            } else if (dbType.equals(DATABSE_TYPE_SQLSERVER)) {
                return SQLSERVER__ALLTABLES_SQL;
            }
        }
        return null;
    }

    public static String getAllCloumnSql(String dbType, String... param) {
        if (StringUtils.isNotEmpty(dbType)) {
            if (dbType.equals(DATABSE_TYPE_MYSQL)) {
                return MessageFormat.format(MYSQL_ALLCOLUMNS_SQL, param);
            } else if (dbType.equals(DATABSE_TYPE_ORACLE)) {
                return MessageFormat.format(ORACLE_ALLCOLUMNS_SQL, param);
            } else if (dbType.equals(DATABSE_TYPE_POSTGRE)) {
                return MessageFormat.format(POSTGRE_ALLCOLUMNS_SQL, param);
            } else if (dbType.equals(DATABSE_TYPE_SQLSERVER)) {
                return MessageFormat.format(SQLSERVER_ALLCOLUMNS_SQL, param);
            }
        }
        return null;
    }

    /**
     * 拼接占位符式的sql拼接语句
     * @param values in中的内容
     * @param fieldName 查询的字段名称
     * @param params sql查询参数集合
     * @return (fieldName in(placeholder)) 或者 (fieldName in(placeholder) or fieldName in(placeholder))
     */
    public static String getInSqlByParams(List values, String fieldName,Map<String,Object> params) {
        return getInSqlByParams(values, fieldName, null,params);
    }

    /**
     * 拼接占位符式的sql拼接语句
     * @param values in中的内容
     * @param fieldName 查询的字段名称
     * @param params sql查询参数集合
     * @return (fieldName in(placeholder)) 或者 (fieldName in(placeholder) or fieldName in(placeholder))
     */
    public static String getInSqlByParams(Object[] values, String fieldName,Map<String,Object> params) {
        return getInSqlByParams(values, fieldName, null,params);
    }

    /**
     * 拼接占位符式的sql拼接语句
     * @param values in中的内容
     * @param fieldName 查询的字段名称
     * @param placeholder 占位符别名
     * @param params sql查询参数集合
     * @return (fieldName in(placeholder)) 或者 (fieldName in(placeholder) or fieldName in(placeholder))
     */
    public static String getInSqlByParams(List values, String fieldName, String placeholder,Map<String,Object> params) {
        Object[] arrValues = null;
        if (values != null && values.size() > 0) {
            arrValues = values.toArray(new Object[values.size()]);
        }
        return getInSqlByParams(arrValues, fieldName, placeholder, params);
    }

    /**
     * 拼接占位符式的sql拼接语句
     * @param values in中的内容
     * @param fieldName 查询的字段名称
     * @param placeholder 占位符别名
     * @param params sql查询参数集合
     * @return (fieldName in(placeholder)) 或者 (fieldName in(placeholder) or fieldName in(placeholder))
     */
    public static String getInSqlByParams(Object[] values, String fieldName, String placeholder,Map<String,Object> params) {
        return getInOrNotInSqlByParams(values, fieldName, placeholder, params,false);
    }


    /**
     * 拼接占位符式的sql拼接语句
     * @param values in中的内容
     * @param fieldName 查询的字段名称
     * @param params sql查询参数集合
     * @return (fieldName not in(placeholder)) 或者 (fieldName not in(placeholder) and fieldName not in(placeholder))
     */
    public static String getNotInSqlByParams(List values, String fieldName,Map<String,Object> params) {
        return getNotInSqlByParams(values, fieldName, null,params);
    }

    /**
     * 拼接占位符式的sql拼接语句
     * @param values in中的内容
     * @param fieldName 查询的字段名称
     * @param params sql查询参数集合
     * @return (fieldName not in(placeholder)) 或者 (fieldName not in(placeholder) and fieldName not in(placeholder))
     */
    public static String getNotInSqlByParams(Object[] values, String fieldName,Map<String,Object> params) {
        return getNotInSqlByParams(values, fieldName, null,params);
    }

    /**
     * 拼接占位符式的sql拼接语句
     * @param values in中的内容
     * @param fieldName 查询的字段名称
     * @param placeholder 占位符别名
     * @param params sql查询参数集合
     * @return (fieldName not in(placeholder)) 或者 (fieldName not in(placeholder) and fieldName not in(placeholder))
     */
    public static String getNotInSqlByParams(List values, String fieldName, String placeholder,Map<String,Object> params) {
        Object[] arrValues = null;
        if (values != null && values.size() > 0) {
            arrValues = values.toArray(new Object[values.size()]);
        }
        return getNotInSqlByParams(arrValues, fieldName, placeholder, params);
    }

    /**
     * 拼接占位符式的sql拼接语句
     * @param values in中的内容
     * @param fieldName 查询的字段名称
     * @param placeholder 占位符别名
     * @param params sql查询参数集合
     * @return (fieldName not in(placeholder)) 或者 (fieldName not in(placeholder) and fieldName not in(placeholder))
     */
    public static String getNotInSqlByParams(Object[] values, String fieldName, String placeholder,Map<String,Object> params) {
        return getInOrNotInSqlByParams(values, fieldName, placeholder, params,true);
    }

    /**
     * 拼接占位符式的sql拼接语句
     * @param values in中的内容
     * @param fieldName 查询的字段名称
     * @param placeholder 占位符别名
     * @param params sql查询参数集合
     * @param isNotin sql是否notin
     * @return (fieldName in(placeholder)) 或者 (fieldName in(placeholder) or fieldName in(placeholder))
     */
    public static String getInOrNotInSqlByParams(Object[] values, String fieldName, String placeholder,Map<String,Object> params, boolean isNotin) {
        String otherSql = isNotin ? " 1=1 " : " 1<>1 ";
        if(StringUtils.isEmpty(fieldName)||params==null){
//            throw new
        }
        if (values == null || values.length == 0) {
            return otherSql;
        }
        StringBuilder sql = new StringBuilder();
        String inOrNotIn = isNotin ? " not in " : " in ";
        String andOr = isNotin ? " and " : " or ";
        if(StringUtils.isEmpty(placeholder)){
            placeholder="btPlaceholderParams";
        }
        sql.append("( ");
        int numCs=999;
        if(values.length>numCs){
            int numSize=values.length/numCs;
            int numQY=values.length%numCs;
            if(numQY>0){
                numSize++;
            }
            for(int i=0;i<numSize;i++){
                if(i>0){
                    sql.append(andOr);
                }
                sql.append(fieldName).append(inOrNotIn).append("(:").append(placeholder).append(i).append(")");
                Object[] valuesNew=new Object[numCs];
                if(numQY>0&&(i+1)==numSize){
                    valuesNew=new Object[numQY];
                }
                System.arraycopy(values, i*numCs, valuesNew, 0, valuesNew.length);
                params.put(placeholder+i,valuesNew);
            }
        }else{
            sql.append(fieldName).append(inOrNotIn).append("(:").append(placeholder).append(")");
            params.put(placeholder,values);
        }
        sql.append(")");
        return sql.toString();
    }

    /**
     * 构造in()查询条件，如果in()中长度超过1000则自动or in()附加 <br>
     * valus为null 返回1<>1 valus为{null,null} 返回1<>1
     *
     * @param values    list<Sring>
     * @param fieldName 字段名
     * @param isString  字符数组中是否为字符串
     * @return (fieldName in(1, 2, 3, 4)) 或者 (fieldName in(1,2) or fieldName in(3,4))
     * @author zhengg
     * @see #getInSqlByParams(List, String, Map)
     * @date 2016-08-24
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String getInSql(List values, String fieldName, boolean isString) {
        String[] valus = null;
        if (values != null && values.size() > 0) {
            valus = (String[]) values.toArray(new String[values.size()]);
        }
        return getInSql(valus, fieldName, isString);
    }

    /**
     * 构造not in()查询条件，如果not in()中长度超过1000则自动and not in()附加 <br>
     * valus为null 返回1=1 valus为{null,null} 返回1=1
     *
     * @param values    list<Sring>
     * @param fieldName 字段名
     * @param isString  字符数组中是否为字符串
     * @return (fieldName not in(1, 2, 3, 4)) 或者 (fieldName not in(1,2) and fieldName not in(3,4))
     * @author zhengg
     * @see #getNotInSqlByParams(List, String, Map)
     * @date 2016-08-24
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String getNotinSql(List values, String fieldName, boolean isString) {
        String[] valus = null;
        if (values != null && values.size() > 0) {
            valus = (String[]) values.toArray(new String[values.size()]);
        }
        return getNotinSql(valus, fieldName, isString);
    }

    /**
     * 构造in()查询条件，如果in()中长度超过1000则自动or in()附加 <br>
     * valus为null 返回1<>1 valus为{null,null} 返回1<>1
     *
     * @param valus
     * @param fieldName 字段名
     * @param isString  字符数组中是否为字符串
     * @return (fieldName not in(1, 2, 3, 4)) 或者 (fieldName not in(1,2) and fieldName not in(3,4))
     * @author zhengg
     * @date 2016-08-24
     */
    public static String getInSql(String[] valus, String fieldName, boolean isString) {
        return getInOrNotinSql(valus, fieldName, isString, false);
    }

    /**
     * 构造in()查询条件，如果in()中长度超过1000则自动or in()附加 <br>
     * valus为null 返回1=1 valus为{null,null} 返回1=1
     *
     * @param valus
     * @param fieldName 字段名
     * @param isString  字符数组中是否为字符串
     * @return (fieldName not in(1, 2, 3, 4)) 或者 (fieldName not in(1,2) and fieldName not in(3,4))
     * @author zhengg
     * @see #getNotInSqlByParams(Object[], String, Map)
     * @date 2016-08-24
     */
    public static String getNotinSql(String[] valus, String fieldName, boolean isString) {
        return getInOrNotinSql(valus, fieldName, isString, true);
    }

    /**
     * 构造in()查询条件，如果in()中长度超过1000则自动or in()附加 <br>
     * valus为null 返回1<>1 valus为{null,null} 返回1<>1
     *
     * @param valus
     * @param fieldName 字段名
     * @param isString  字符数组中是否为字符串
     * @param isNotin   sql是否notin
     * @return (fieldName in(1, 2, 3, 4)) 或者 (fieldName in(1,2) or fieldName in(3,4)) 或者 (fieldName not in(1,2,3,4)) 或者 (fieldName not in(1,2) and fieldName not in(3,4))
     * @author zhengg
     * @date 2016-08-24
     */
    public static String getInOrNotinSql(String[] valus, String fieldName, boolean isString, boolean isNotin) {
        String otherSql = isNotin ? " 1=1 " : " 1<>1 ";
        if (valus == null || valus.length == 0) {
            return otherSql;
        }
        StringBuilder sql = new StringBuilder();
        String inOrNotin = isNotin ? " not in " : " in ";
        String andor = isNotin ? " and " : " or ";
        sql.append("( ").append(fieldName).append(inOrNotin).append("( ");
        int num = 1;
        boolean flag = false;
        for (int i = 0; i < valus.length; i++) {
            if (valus[i] == null) {
                continue;
            }
            if (num == 1000) {
                sql.append(")").append(andor).append(fieldName).append(inOrNotin).append("( ");
                num = 1;
            }
            sql.append(num == 1 ? "" : ",");
            if (isString) {
                sql.append("'").append(valus[i]).append("'");
            } else {
                sql.append(valus[i]);
            }
            num++;
            flag = true;
        }
        sql.append(" ))");
        return flag ? sql.toString() : otherSql;
    }

    public static void main(String[] args) {
        Object[] values={1,2,4,5,6,7,8,9,10};
        Map<String,Object> params=new HashMap<>();
        String str=SqlUtil.getInSqlByParams(values,"a.test",params);

    }
}
