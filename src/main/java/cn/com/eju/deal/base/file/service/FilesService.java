package cn.com.eju.deal.base.file.service;

import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.dto.file.FileDto;
import cn.com.eju.deal.base.service.BaseService;
import cn.com.eju.deal.core.support.ResultData;
import cn.com.eju.deal.core.support.SystemCfg;

/**   
* 文件渠道管理系统-Service
* @author (li_xiaodong)
* @date 2016年2月2日 下午9:30:27
*/
@Service("filesService")
public class FilesService extends BaseService
{
    
    private final static String REST_SERVICE = SystemCfg.getString("filesRestServer");
    
    /** 
     * 查询-根据fileNo
     * @param id
     * @return
     * @throws Exception
     */
    public ResultData<?> getByFileNo(String fileNo)
        throws Exception
    {
        
        //调用 接口
        String url = REST_SERVICE + "/fileNo/{fileNo}";
        
        ResultData<?> backResult = get(url, fileNo);
        
        return backResult;
    }
    
    /** 
    * 更新
    * @param studentDto
    * @return
    * @throws Exception
    */
    public void update(FileDto fileDto)
        throws Exception
    {
        
        String url = REST_SERVICE + "";
        
        put(url, fileDto);
        
    }
    
    /** 
    * 删除
    * @param id
    * @param updateId
    * @return
    * @throws Exception
    */
    public void deleteByFileNo(String fileNo, int operateId)
        throws Exception
    {
        
        String url = REST_SERVICE + "/fileNo/{fileNo}/{operateId}";
        
        delete(url, fileNo, operateId);
        
    }
    
    /** 
     * 查询-获取渠道配置
     * @param id
     * @return
     * @throws Exception
     */
    public ResultData<?> getFileConfig(String channelCode)
        throws Exception
    {
        
        //调用 接口
        String url = REST_SERVICE + "/config";
        
        ResultData<?> backResult = get(url);
        
        return backResult;
    }
    
}
