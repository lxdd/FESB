package cn.com.eju.deal.base.code.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.code.dao.WebConfigMapper;
import cn.com.eju.deal.base.code.model.WebConfig;
import cn.com.eju.deal.base.dto.code.WebConfigDto;
import cn.com.eju.deal.base.service.BaseService;
import cn.com.eju.deal.core.support.ResultData;

import com.googlecode.ehcache.annotations.Cacheable;

/**   
* ErrorLogService 服务类
* @author li_xiaodong
* @date 2016年2月2日 下午7:57:09
*/
@Service("webConfigService")
public class WebConfigService extends BaseService 
{
    
    @Resource
    private WebConfigMapper webConfigMapper;
    
    /** 
     * 查询-list
     * @param queryParam
     * @return
     */
    @Cacheable(cacheName = "testCache")
    public ResultData<?> queryList(Map<?, ?> param) throws Exception
    {
        
        //构建返回
        ResultData<List<WebConfigDto>> resultData = new ResultData<List<WebConfigDto>>();
        
        //查询
        final List<WebConfig> webConfigList = webConfigMapper.queryList(param);
        
        //转换
        List<WebConfigDto> codeDtoList = convertData(webConfigList);
        
        resultData.setReturnData(codeDtoList);
        
        return resultData;
        
    }
    
    /** 
     * 对象转换MO--DTO
     * @param stuList
     * @return List<StudentDto>
     */
    private List<WebConfigDto> convertData(List<WebConfig> webConfigList) throws Exception
    {
        List<WebConfigDto> webConfigDtoList = new ArrayList<WebConfigDto>();
        
        if (null != webConfigList && !webConfigList.isEmpty())
        {
            WebConfigDto webConfigDto = null;
            for (WebConfig webConfig : webConfigList)
            {
                webConfigDto = new WebConfigDto();
                BeanUtils.copyProperties(webConfig, webConfigDto);
                webConfigDtoList.add(webConfigDto);
            }
        }
        return webConfigDtoList;
    }
    
}
