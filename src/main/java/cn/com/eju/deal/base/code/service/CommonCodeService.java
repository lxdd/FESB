package cn.com.eju.deal.base.code.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.code.dao.CommonCodeMapper;
import cn.com.eju.deal.base.code.model.Code;
import cn.com.eju.deal.base.dto.code.CommonCodeDto;
import cn.com.eju.deal.base.service.BaseService;
import cn.com.eju.deal.core.support.ResultData;

import com.googlecode.ehcache.annotations.Cacheable;

/**   
* ErrorLogService 服务类
* @author li_xiaodong
* @date 2016年2月2日 下午7:57:09
*/
@Service("commonCodeService")
public class CommonCodeService extends BaseService 
{
    
    @Resource
    private CommonCodeMapper commonCodeMapper;
    
    
    /** 
     * 查询-list
     * @param queryParam
     * @return
     */
    @Cacheable(cacheName = "testCache")
    public ResultData<?> queryList(Map<?, ?> param) throws Exception
    {
        
        //构建返回
        ResultData<List<CommonCodeDto>> resultData = new ResultData<List<CommonCodeDto>>();
        
        //查询
        final List<Code> codeList = commonCodeMapper.queryList(param);
        
        //转换
        List<CommonCodeDto> codeDtoList = convertData(codeList);
        
        resultData.setReturnData(codeDtoList);
        
        return resultData;
    }
    
    
    
    /** 
     * 对象转换MO--DTO
     * @param stuList
     * @return List<StudentDto>
     */
    private List<CommonCodeDto> convertData(List<Code> codeList) throws Exception
    {
        List<CommonCodeDto> codeDtoList = new ArrayList<CommonCodeDto>();
        
        if (null != codeList && !codeList.isEmpty())
        {
            CommonCodeDto codeDto = null;
            for (Code code : codeList)
            {
                codeDto = new CommonCodeDto();
                BeanUtils.copyProperties(code, codeDto);
                codeDtoList.add(codeDto);
            }
        }
        return codeDtoList;
    }
    
}
