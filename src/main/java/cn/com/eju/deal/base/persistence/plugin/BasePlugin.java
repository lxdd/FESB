package cn.com.eju.deal.base.persistence.plugin;

import java.lang.reflect.Proxy;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.eju.deal.core.reflect.MetaObject;

/**   
* (Mybatis插件基类)
* @author (li_xiaodong)
* @date 2015年10月21日 上午10:14:57
*/
public abstract class BasePlugin implements Interceptor
{
    /** 日志 */
    private static final Logger log = LoggerFactory.getLogger(BasePlugin.class);
    
    /** 配置 */
    protected Properties properties;
    
    /**
     * <p>从代理对象中获取真实对象。</p>
     *
     * 插件中获得的对象可能是代理，应用此方法将代理层层剥去，获得原始对象
     *
     * @param target 代理对象
     * @return 原始对象
     */
    @SuppressWarnings("unchecked")
    protected <T> T unProxy(T target)
    {
        if (log.isDebugEnabled())
        {
            log.debug("unProxy:target={}:{}", target.getClass().getName(), target);
        }
        
        T unProxyObj = target;
        while (Proxy.isProxyClass(unProxyObj.getClass()))
        {
            Plugin plugin = (Plugin)Proxy.getInvocationHandler(target);
            MetaObject<Plugin> meta = MetaObject.fromObject(plugin);
            unProxyObj = (T)meta.getValue("target");
            
            if (log.isDebugEnabled())
            {
                log.debug("unProxy:unProxyObj={}:{}", unProxyObj.getClass().getName(), target);
            }
        }
        
        return unProxyObj;
    }
    
    /**
     * <p>从委托对象中获取真实对象。</p>
     *
     * 插件中获得的对象可能是委托，应用此方法获得原始对象。
     * 如果传入的对象中不存在delegate属性
     *
     * @param target 委托对象
     * @return 原始对象
     */
    @SuppressWarnings("unchecked")
    protected <T> T unDelegate(T target)
    {
        if (log.isDebugEnabled())
        {
            log.debug("unDelegate:target={}:{}", target.getClass().getName(), target);
        }
        
        T unDelegateObj = target;
        MetaObject<T> meta = MetaObject.fromObject(target);
        if (meta.hasFiled("delegate"))
        {
            unDelegateObj = (T)meta.getValue("delegate");
        }
        
        return unDelegateObj;
    }
    
    @Override
    public final Object plugin(Object target)
    {
        if (log.isDebugEnabled())
        {
            log.debug("plugin:{}->{}", this, target);
        }
        
        return Plugin.wrap(target, this);
    }
    
    @Override
    public final void setProperties(Properties properties)
    {
        this.properties = properties;
    }
    
}
