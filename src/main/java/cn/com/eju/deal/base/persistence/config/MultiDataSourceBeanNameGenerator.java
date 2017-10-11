package cn.com.eju.deal.base.persistence.config;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**   
 * 多数据源命名生成器。
 * 
 * 用于配合{@link MultiDataSourceMapperScannerConfigurer}以根据Mapper接口上标注的注解类型生成不同的实例名
* @author (li_xiaodong)
* @date 2015年10月21日 下午2:14:50
*/
public class MultiDataSourceBeanNameGenerator extends AnnotationBeanNameGenerator
{
    /** 日志 */
    private static final Logger log = LoggerFactory.getLogger(MultiDataSourceBeanNameGenerator.class);
    
    /** 注解靶 */
    private Class<? extends Annotation> annotationClass;
    
    /**
     * 通过注解类构造此命名生成器。
     * 
     * 在实例扫描过程中，仅扫描具备指定注解类型的类
     * 
     * @param annotationClass 要扫描的注解类型
     */
    public void setAnnotationClass(Class<? extends Annotation> annotationClass)
    {
        this.annotationClass = annotationClass;
    }
    
    /**
     * 扫描后生成相应的实例名。
     * 取决于注解靶,若注解靶为null，则该生成器失效，之后采用默认的命名策略
     * 
     * @param definition 定义
     * @param registry  注册表
     */
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry)
    {
        String beanName = null;
        if (annotationClass == null)
        {
            beanName = super.generateBeanName(definition, registry);
        }
        else
        {
            beanName = definition.getBeanClassName();
            try
            {
                Class<?> beanClass = Class.forName(beanName);
                if (beanClass.getAnnotation(annotationClass) != null)
                {
                    beanName += "@" + annotationClass.getSimpleName().toUpperCase();
                }
            }
            catch (ClassNotFoundException e)
            {
                beanName = super.generateBeanName(definition, registry);
                if (log.isWarnEnabled())
                {
                    log.warn("target annotation is null, use default naming strategy. bean name will be {}", beanName);
                }
            }
            catch (NullPointerException e)
            {
                beanName = super.generateBeanName(definition, registry);
                if (log.isWarnEnabled())
                {
                    log.warn("target annotation is null, use default naming strategy. bean name will be {}", beanName);
                }
            }
        }
        
        return beanName;
    }
    
}
