package cn.com.eju.deal.base.persistence.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.util.StringUtils;

import cn.com.eju.deal.core.reflect.MetaObject;

/**   
 * 多数据源命名生成器。
 * 
 * 用于配合{@link MultiDataSourceBeanNameGenerator}以根据Mapper接口上标注的注解类型生成数据源的Mapper
* @author (li_xiaodong)
* @date 2015年10月21日 下午2:15:35
*/
public class MultiDataSourceMapperScannerConfigurer extends MapperScannerConfigurer
{
    /** 日志 */
    private static final Logger log = LoggerFactory.getLogger(MultiDataSourceMapperScannerConfigurer.class);
    
    /**
     * 命名生成器
     */
    private MultiDataSourceBeanNameGenerator beanNameGenerator;
    
    public void setBeanNameGenerator(MultiDataSourceBeanNameGenerator beanNameGenerator)
    {
        this.beanNameGenerator = beanNameGenerator;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry)
        throws BeansException
    {
        try
        {
            /*
             * if (this.processPropertyPlaceHolders) 
             * {
             *     processPropertyPlaceHolders(); 
             * }
             */
            MetaObject<MultiDataSourceMapperScannerConfigurer> thisMetaObject = MetaObject.fromObject(this);
            boolean processPropertyPlaceHolders = (Boolean)thisMetaObject.getValue("processPropertyPlaceHolders");
            if (processPropertyPlaceHolders)
            {
                Method method = MapperScannerConfigurer.class.getDeclaredMethod("processPropertyPlaceHolders");
                method.setAccessible(true);
                method.invoke(this);
            }
            
            /*
             * Scanner scanner = new Scanner(beanDefinitionRegistry);
             */
            Class<?> clazz = Class.forName("org.mybatis.spring.mapper.MapperScannerConfigurer$Scanner");
            Constructor<?> constructor =
                clazz.getDeclaredConstructor(MapperScannerConfigurer.class, BeanDefinitionRegistry.class);
            constructor.setAccessible(true);
            ClassPathBeanDefinitionScanner scanner =
                (ClassPathBeanDefinitionScanner)constructor.newInstance(this, beanDefinitionRegistry);
            
            /*
             * scanner.setResourceLoader(this.applicationContext);
             */
            scanner.setResourceLoader((ApplicationContext)thisMetaObject.getValue("applicationContext"));
            
            // 修改bean名称生成器
            beanNameGenerator.setAnnotationClass((Class<? extends Annotation>)thisMetaObject.getValue("annotationClass"));
            scanner.setBeanNameGenerator(beanNameGenerator);
            
            /*
             * scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage,
             *      ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
             */
            scanner.scan(StringUtils.tokenizeToStringArray((String)thisMetaObject.getValue("basePackage"),
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
        }
        catch (Exception e)
        {
            if (log.isWarnEnabled())
            {
                log.warn("An error has occurred, scanner will not be work!", e);
            }
        }
    }
    
}
