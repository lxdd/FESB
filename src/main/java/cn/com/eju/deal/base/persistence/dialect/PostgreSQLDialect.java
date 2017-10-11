package cn.com.eju.deal.base.persistence.dialect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.eju.deal.core.util.SqlUtil;

/**   
* PostgreSQL方言
* @author (li_xiaodong)
* @date 2015年10月21日 下午1:53:18
*/
class PostgreSQLDialect extends Dialect
{
    /**
     * 得到查询总数的sql
     */
    @Override
    public String getCountString(String querySelect)
    {
        querySelect = SqlUtil.cleanSql(querySelect);
        int formIndex = getAfterFormInsertPoint(querySelect);
        int orderIndex = getLastOrderInsertPoint(querySelect);
        String select = querySelect.substring(0, formIndex);

        StringBuilder countSqlBuilder = new StringBuilder();
        //如果SELECT 中包含 DISTINCT 只能在外层包含COUNT
        if (select.toUpperCase().startsWith("SELECT DISTINCT") || querySelect.toUpperCase().contains("GROUP BY"))
        {
            countSqlBuilder.append("SELECT COUNT(1) COUNT FROM (");
            String selectWithoutOrder = querySelect.substring(0, orderIndex);
            countSqlBuilder.append(selectWithoutOrder);
            countSqlBuilder.append(") t");
        }
        else
        {
            countSqlBuilder.append("SELECT COUNT(1) COUNT ");
            String fromWithoutOrder = querySelect.substring(formIndex, orderIndex);
            countSqlBuilder.append(fromWithoutOrder);
        }

        return countSqlBuilder.toString();
    }

    /**
     * 得到分页的SQL
     * @param offset    偏移量
     * @param limit     位置
     * @return  分页SQL
     */
    @Override
    public String getLimitString(String querySql, long offset, int limit)
    {
        String cleanedSql = SqlUtil.cleanSql(querySql);
        StringBuilder limitSql = new StringBuilder(cleanedSql);
        limitSql.append(" LIMIT ");
        limitSql.append(limit);
        limitSql.append(" OFFSET ");
        limitSql.append(offset);

        return limitSql.toString();
    }

    /**
     * 得到最后一个Order By的插入点位置
     * @return 返回最后一个Order By插入点的位置
     */
    private int getLastOrderInsertPoint(String querySelect)
    {
        int orderIndex = querySelect.toLowerCase().lastIndexOf("order by");
        if (orderIndex == -1 || !isBracketCanPartnership(querySelect.substring(orderIndex, querySelect.length())))
        {
            throw new RuntimeException("分页必须要有Order by 语句!");
        }

        return orderIndex;
    }

    /**
     * 得到SQL第一个正确的FROM的的插入点
     */
    private int getAfterFormInsertPoint(String querySelect)
    {
        String regex = "\\s+FROM\\s+";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(querySelect);
        while (matcher.find())
        {
            int fromStartIndex = matcher.start(0);
            String text = querySelect.substring(0, fromStartIndex);
            if (isBracketCanPartnership(text))
            {
                return fromStartIndex;
            }
        }

        return 0;
    }

    /**
     * 判断括号"()"是否匹配,并不会判断排列顺序是否正确
     *
     * @param text
     *            要判断的文本
     * @return 如果匹配返回TRUE,否则返回FALSE
     */
    private boolean isBracketCanPartnership(String text)
    {
        if (text == null || getIndexOfCount(text, '(') != getIndexOfCount(text, ')'))
        {
            return false;
        }
        return true;
    }

    /**
     * 得到一个字符在另一个字符串中出现的次数
     * @param text  文本
     * @param ch    字符
     */
    private int getIndexOfCount(String text, char ch)
    {
        int count = 0;
        for (int i = 0; i < text.length(); i++)
        {
            count = text.charAt(i) == ch ? count + 1 : count;
        }

        return count;
    }

    public boolean supportsLimit()
    {
        return true;
    }

}
