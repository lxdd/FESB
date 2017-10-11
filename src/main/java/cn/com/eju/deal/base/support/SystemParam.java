package cn.com.eju.deal.base.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.eju.deal.base.code.dao.CommonCodeMapper;
import cn.com.eju.deal.base.code.dao.IpWhiteMapper;
import cn.com.eju.deal.base.code.dao.WebConfigMapper;
import cn.com.eju.deal.base.code.dao.WsWebConfigMapper;
import cn.com.eju.deal.base.code.model.Code;
import cn.com.eju.deal.base.code.model.IpWhite;
import cn.com.eju.deal.base.code.model.WebConfig;
import cn.com.eju.deal.base.code.model.WsWebConfig;
import cn.com.eju.deal.base.linkage.dao.AreaMapper;
import cn.com.eju.deal.base.linkage.dao.CityMapper;
import cn.com.eju.deal.base.linkage.dao.DistrictMapper;
import cn.com.eju.deal.base.linkage.model.Area;
import cn.com.eju.deal.base.linkage.model.City;
import cn.com.eju.deal.base.linkage.model.District;
import cn.com.eju.deal.core.dao.IDao;
import cn.com.eju.deal.core.helper.SpringConfigHelper;
import cn.com.eju.deal.core.util.StringUtil;

/**   
* 系统参数缓存，包括了系统通用参数，行权限参数等各种参数，都是以单例的形式存在
* @author (li_xiaodong)
* @date 2015年11月9日 下午3:25:12
*/
public class SystemParam
{
    
    /**
     * 码表对象List，所有的码表对象都会缓存在这个对象中
     */
    private static Map<String, List<Code>> codeListMap;
    
    /**
     * 码表对象，所有的码表对象都会缓存在这个对象中
     */
    private static Map<String, Code> codeMap;
    
    /**
     * 数据配置map对象
     */
    private static Map<String, String> webConfigMap = new HashMap<String, String>();
    
    /**
     * WS对象MAP，所有的WS对象都会缓存在这个对象中
     */
    private static Map<String, WsWebConfig> wsWebConfigMap;
    
    /**
     * ipWhite对象List，所有的ipWhite对象都会缓存在这个对象中
     */
    private static List<IpWhite> ipWhiteList;
    

    //City District Area
    
    /**
     * City对象List，所有的cityList对象都会缓存在这个对象中
     */
    private static List<City> cityList;
    
    /**
     * 所有的City对象都会缓存在这个对象中
     */
    private static Map<String, String> cityMap;
    
    /**
     * District对象List，所有的DistrictList对象都会缓存在这个对象中
     */
    private static List<District> districtList;
    
    /**
     * 所有的City对象都会缓存在这个对象中
     */
    private static Map<String, String> districtMap;
    
    
    /**
     * Area对象List，所有的AreaList对象都会缓存在这个对象中
     */
    private static List<Area> areaList;
    
    /**
     * 所有的area对象都会缓存在这个对象中
     */
    private static Map<String, String> areaMap;
    
    
    
    
    /** 日志 */
    private static final Logger log = LoggerFactory.getLogger(SystemParam.class);
    
    /**
     * 根据码表的key获取码表对象
     * @param mapKey 码表的key
     * @return List<Code>
     */
    public static String getDicValueByDicCode(String dicCode)
    {
        if (codeMap == null)
        {
            initCodeMap();
        }
        
        String dicValue = "";
        
        if (StringUtil.isNotEmpty(dicCode))
        {
            
            Code code = codeMap.get(dicCode);
            
            if (null != code)
            {
                dicValue = code.getDicValue();
            }
        }
        
        return dicValue;
    }
    
    /**
     * 根据码表的key获取码表对象
     * @param mapKey 码表的key
     * @return List<Code>
     */
    public static Code getCodeMapByKey(String dicCode)
    {
        if (codeMap == null)
        {
            initCodeMap();
        }
        
        return codeMap.get(dicCode);
    }
    
    /**
     * 根据wsKey获取WS对象
     * @param wsKey
     * @return List<Code>
     */
    public static WsWebConfig getWsWebConfigByWsKey(String wsKey)
    {
        if (wsWebConfigMap == null)
        {
            initWsWebConfigPram();
        }
        
        return wsWebConfigMap.get(wsKey);
    }
    
    /**
     * 根据wsNo获取IpWhite对象
     * @param wsNo
     * @return List<Code>
     */
//    public static List<IpWhite> getIpWhiteByWsNO(String wsNo)
//    {
//        List<IpWhite> rspIpWhiteList = new ArrayList<IpWhite>();
//        
//        if (ipWhiteList == null)
//        {
//            initIpWhitePram();
//        }
//        
//        for (IpWhite ipWhite : ipWhiteList)
//        {
//            
//            if (null != wsNo && wsNo.equals(ipWhite.getWsNo()))
//            {
//                rspIpWhiteList.add(ipWhite);
//            }
//            
//        }
//        
//        return rspIpWhiteList;
//    }
    
    /**
     * 获取IpWhiteList对象
     * 
     * @return List<Code>
     */
    public static List<IpWhite> getIpWhiteList()
    {
        if (ipWhiteList == null)
        {
            initIpWhitePram();
        }
        
        return ipWhiteList;
    }
    
    /**
     * 根据码表的key和代码获取码值内容
     * @param mapKey 码表的key
     * @param dicCode 码值
     * @return 码值内容（名称）
     */
    public static String getCodeNameByMapKeyAndCode(String mapKey, String dicCode)
    {
        
        List<Code> codeMap = getCodeListByKey(mapKey);
        if (codeMap != null && codeMap.size() > 0)
        {
            for (Code code : codeMap)
            {
                if (null != dicCode && dicCode.equals(code.getDicCode()))
                {
                    return code.getDicValue();
                }
            }
        }
        return "";
    }
    
    /** 
    * 根据webConfigKey获取配置值 value 
    * @param key
    * @return
    */
    public static String getWebConfigValue(String webConfigKey)
    {
        
        if (webConfigMap.isEmpty())
        {
            //初始化配置表信息
            initWebConfigPram();
        }
        
        String value = null;
        if (webConfigMap.containsKey(webConfigKey))
        {
            value = webConfigMap.get(webConfigKey);
            if (log.isDebugEnabled())
            {
                log.debug("获取配置信息成功:{}={}", webConfigKey, value);
            }
        }
        else
        {
            if (log.isWarnEnabled())
            {
                log.warn("获取配置信息失败:{}", webConfigKey);
            }
        }
        
        return value;
    }
    
    /**
     * 根据码表的key获取码表对象List
     * @param mapKey 码表的key
     * @return List<Code>
     */
    public static List<Code> getCodeListByKey(String typeId)
    {
        if (codeListMap == null)
        {
            initCodeMap();
        }
        
        return codeListMap.get(typeId);
    }
    
    /** 
     * <p>初始化码表信息到内存中。</p>
     */
    private synchronized static void initCodeMap()
    {
        codeListMap = new HashMap<String, List<Code>>();
        
        codeMap = new HashMap<String, Code>();
        
        //查询出码表
        List<Code> codeMapList = loadCodeData();
        
        for (Code code : codeMapList)
        {
            //dicCode
            String dicCodeKey = "" + code.getDicCode();
            
            //typeId
            String typeIdKey = "" + code.getTypeId();
            
            //拼装codeMap
            if (!codeMap.containsKey(dicCodeKey))
            {
                codeMap.put(dicCodeKey, code);
            }
            
            //拼装codeListMap
            if (codeListMap.containsKey(typeIdKey))
            {
                codeListMap.get(typeIdKey).add(code);
            }
            else
            {
                List<Code> codeList = new ArrayList<Code>();
                codeList.add(code);
                codeListMap.put(typeIdKey, codeList);
            }
            
        }
        
    }
    
    /** 
    * 查询出码表
    * @return
    */
    public static List<Code> loadCodeData()
    {
        final IDao<?> commonCodeMapper = SpringConfigHelper.getDaoBeanByDaoClassName("commonCodeMapper");
        
        final CommonCodeMapper dao = (CommonCodeMapper)commonCodeMapper;
        
        Map<String, Object> queryParam = new HashMap<String, Object>();
        
        List<Code> codeMapList = dao.queryList(queryParam);
        
        return codeMapList;
    }
    
    /**
    *
    * <p>初始化Web数据配置信息到内存中。</p>
    *
    */
    private synchronized static void initWebConfigPram()
    {
        
        webConfigMap = new HashMap<String, String>();
        
        //取得系统表数据
        List<WebConfig> webConfigInfoList = loadWebConfigData();
        
        for (WebConfig webConfig : webConfigInfoList)
        {
            String key = webConfig.getWebConfigName();
            
            if (!webConfigMap.containsKey(key))
            {
                
                webConfigMap.put(key, webConfig.getWebConfigValue());
            }
            
        }
        
    }
    
    /** 
     * 查询出数据配置表
     * @return
     */
    public static List<WebConfig> loadWebConfigData()
    {
        final IDao<?> WebConfigMapper = SpringConfigHelper.getDaoBeanByDaoClassName("webConfigMapper");
        
        final WebConfigMapper dao = (WebConfigMapper)WebConfigMapper;
        
        Map<String, Object> queryParam = new HashMap<String, Object>();
        
        List<WebConfig> WebConfigList = dao.queryList(queryParam);
        
        return WebConfigList;
    }
    
    /**
    *
    * <p>初始化WS数据配置信息到内存中。</p>
    *
    */
    private synchronized static void initWsWebConfigPram()
    {
        
        wsWebConfigMap = new HashMap<String, WsWebConfig>();
        
        //取得系统表数据
        List<WsWebConfig> wsWebConfigList = loadWSConfigData();
        
        String wsKey = null;
        
        for (WsWebConfig wsWebConfig : wsWebConfigList)
        {
            wsKey = wsWebConfig.getWsKey();
            
            if (!wsWebConfigMap.containsKey(wsKey))
            {
                wsWebConfigMap.put(wsKey, wsWebConfig);
            }
            
        }
        
    }
    
    /** 
     * 查询出WS配置表
     * @return
     */
    public static List<WsWebConfig> loadWSConfigData()
    {
        final IDao<?> wsWebConfigMapper = SpringConfigHelper.getDaoBeanByDaoClassName("wsWebConfigMapper");
        
        final WsWebConfigMapper dao = (WsWebConfigMapper)wsWebConfigMapper;
        
        Map<String, Object> queryParam = new HashMap<String, Object>();
        
        List<WsWebConfig> wsWebConfigList = dao.queryList(queryParam);
        
        return wsWebConfigList;
    }
    
    /**
    *
    * <p>初始化IpWhite数据配置信息到内存中。</p>
    *
    */
    private synchronized static void initIpWhitePram()
    {
        
        //取得ipWhite数据
        ipWhiteList = loadIpWhiteData();
        
    }
    
    /** 
     * 查询出IpWhite配置表
     * @return
     */
    public static List<IpWhite> loadIpWhiteData()
    {
        final IDao<?> ipWhiteMapper = SpringConfigHelper.getDaoBeanByDaoClassName("ipWhiteMapper");
        
        final IpWhiteMapper dao = (IpWhiteMapper)ipWhiteMapper;
        
        Map<String, Object> queryParam = new HashMap<String, Object>();
        
        List<IpWhite> ipWhiteList = dao.queryList(queryParam);
        
        return ipWhiteList;
    }
    
    /**
     * 
     * @param mapKey 码表的key
     * @return List<Code>
     */
    public static String getCityNameByCityNo(String cityNo)
    {
        if (cityMap == null)
        {
            initCityMap();
        }
        
        String cityName = "";
        
        if (StringUtil.isNotEmpty(cityNo))
        {
            
            cityName = cityMap.get(cityNo);
        }
        
        return cityName;
    }
    
    /**
     * 
     * @param mapKey 码表的key
     * @return List<Code>
     */
    public static String getDistrictNameByDistrictNo(String districtNo)
    {
        if (districtMap == null)
        {
            initCityMap();
        }
        
        String districtName = "";
        
        if (StringUtil.isNotEmpty(districtNo))
        {
            
            districtName = districtMap.get(districtNo);
        }
        
        return districtName;
    }
    
    /**
     * 
     * @param mapKey 码表的key
     * @return List<Code>
     */
    public static String getAreaNameByAreaNo(String areaNo)
    {
        if (areaMap == null)
        {
            initCityMap();
        }
        
        String areaName = "";
        
        if (StringUtil.isNotEmpty(areaNo))
        {
            
            areaName = areaMap.get(areaNo);
        }
        
        return areaName;
    }
    
    /** 
     * <p>初始化码表信息到内存中。</p>
     */
    private synchronized static void initCityMap()
    {
        
        cityMap = new HashMap<String, String>();
        districtMap = new HashMap<String, String>();
        areaMap = new HashMap<String, String>();
        
        if(null == cityList || null == districtList || null == areaList){
            initCityListPram();
        }
        
        
        if (null != cityList && !cityList.isEmpty())
        {
            for (City city : cityList)
            {
                //cityNo
                String cityNo = city.getCityNo();
                
                //cityName
                String cityName = city.getCityName();
                
                //拼装codeMap
                if (!cityMap.containsKey(cityNo))
                {
                    cityMap.put(cityNo, cityName);
                }
                
            }
        }
        
        if (null != districtList && !districtList.isEmpty())
        {
            for (District district : districtList)
            {
                String districtNo = district.getDistrictNo();
                
                String districtName = district.getDistrictName();
                
                //拼装
                if (!districtMap.containsKey(districtNo))
                {
                    districtMap.put(districtNo, districtName);
                }
                
            }
        }
        
        if (null != areaList && !areaList.isEmpty())
        {
            for (Area area : areaList)
            {
                String areaNo = area.getAreaNo();
                
                String areaName = area.getAreaName();
                
                //拼装
                if (!areaMap.containsKey(areaNo))
                {
                    areaMap.put(areaNo, areaName);
                }
                
            }
        }
    }
    
    
    /**
    *
    * <p>初始化CityList/districtList/areaList信息到内存中。</p>
    *
    */
    private synchronized static void initCityListPram()
    {
        
        //取得CityList数据
        cityList = loadCityData();
        districtList = loadDistrictData();
        areaList = loadAreaData();
        
    }
    
    /** 
     * 查询出CityList
     * @return
     */
    public static List<City> loadCityData()
    {
        final IDao<?> cityMapper = SpringConfigHelper.getDaoBeanByDaoClassName("cityMapper");
        
        final CityMapper dao = (CityMapper)cityMapper;
        
        Map<String, Object> queryParam = new HashMap<String, Object>();
        
        List<City> cityList = dao.queryList(queryParam);
        
        return cityList;
    }
    
    /** 
     * 查询出districtList
     * @return
     */
    public static List<District> loadDistrictData()
    {
        final IDao<?> districtMapper = SpringConfigHelper.getDaoBeanByDaoClassName("districtMapper");
        
        final DistrictMapper dao = (DistrictMapper)districtMapper;
        
        Map<String, Object> queryParam = new HashMap<String, Object>();
        
        List<District> districtList = dao.queryList(queryParam);
        
        return districtList;
    }
    
    /** 
     * 查询出AreaList
     * @return
     */
    public static List<Area> loadAreaData()
    {
        final IDao<?> areaMapper = SpringConfigHelper.getDaoBeanByDaoClassName("areaMapper");
        
        final AreaMapper dao = (AreaMapper)areaMapper;
        
        Map<String, Object> queryParam = new HashMap<String, Object>();
        
        List<Area> areaList = dao.queryList(queryParam);
        
        return areaList;
    }
    
    /**
     * 刷新字典表、配置表信息
     */
    public static void refreshCodeMap() throws Exception
    {
        //初始化字典表信息
        initCodeMap();
        
        //初始化配置表信息
        initWebConfigPram();
        
        //加载WS配置信息
        initWsWebConfigPram();
    }
    
}
