package cn.com.eju.deal.base.persistence.dialect;

import java.util.HashMap;

/**   
* 不同数据库分页的具体实现方式的抽象类
* @author (li_xiaodong)
* @date 2015年10月21日 下午1:52:23
*/
public abstract class Dialect
{
    /* 方言列表 */
    private static final HashMap<String, Dialect> dialects;

    static
    {
        // 初始化所有方言
        dialects = new HashMap<String, Dialect>();
        dialects.put("ORACLE", new OracleDialect());
        dialects.put("MYSQL", new MySql5Dialect());
        dialects.put("POSTGRESQL", new PostgreSQLDialect());
        dialects.put("SQLSERVER", new SQLServerDialect());
    }


    public static Dialect getDialect(final String dialectName)
    {
        return dialects.get(dialectName.toUpperCase());
    }

    /**
     * 获取带分页标识的SQL语句
     * @param sql 截取到的SQL语句
     * @param offset 偏移量
     * @param limit 记录条数
     * @return 修改后的SQL语句
     */
    public abstract String getLimitString(String sql, long offset, int limit) throws Exception;

    /**
     * 获取带某一SQL语句的计算总数的SQL语句
     * @param sql 原SQL语句
     * @return 带Count的SQL语句
     */
    public abstract String getCountString(String sql) throws Exception;

}
