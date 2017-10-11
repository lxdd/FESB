package cn.com.eju.deal.base.persistence.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
 * 历史库标记。
 * 
 * 将此注解标注在Mapper接口上，以标识其所用数据源为历史库。
 * @author (li_xiaodong)
 * @date 2015年10月21日 下午2:14:09
*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface History
{
}
