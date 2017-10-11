package cn.com.eju.deal.base.file.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.eju.deal.base.controller.BaseController;
import cn.com.eju.deal.base.dto.file.FileRecordDto;
import cn.com.eju.deal.base.file.model.FileRecord;
import cn.com.eju.deal.base.file.service.FileRecordService;
import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.core.support.ResultData;
import cn.com.eju.deal.core.util.JsonUtil;

/**
 * 服务层
 *
 * @author (li_xiaodong)
 * @date 2016年1月19日 下午6:05:44
 */

@RestController
@RequestMapping(value = "fileRecord")
public class FileRecordController extends BaseController
{
    
    /**
     * 日志
     */
    private final LogHelper logger = LogHelper.getLogger(this.getClass());
    
    @Resource(name = "fileRecordService")
    private FileRecordService fileRecordService;
    
    /**
     * 查询-对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable int id)
    {
        //构建返回
        ResultData<FileRecordDto> resultData = new ResultData<FileRecordDto>();
        
        //查询
        try
        {
            FileRecord mo = fileRecordService.getById(id);
            
            //Model转换Dto
            FileRecordDto dto = new FileRecordDto();
            
            BeanUtils.copyProperties(mo, dto);
            
            resultData.setReturnData(dto);
            
        }
        catch (Exception e)
        {
            logger.error("FileRecord", "FileRecordController", "getById", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
    /**
     * 查询-对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/relateId/{relateId}", method = RequestMethod.GET)
    public String getByRelateId(@PathVariable String relateId)
    {
        //构建返回
        ResultData<FileRecordDto> resultData = new ResultData<FileRecordDto>();
        
        //查询
        try
        {
            FileRecord mo = fileRecordService.getByRelateId(relateId);
            
            //Model转换Dto
            FileRecordDto dto = new FileRecordDto();
            
            BeanUtils.copyProperties(mo, dto);
            
            resultData.setReturnData(dto);
            
        }
        catch (Exception e)
        {
            logger.error("FileRecord", "FileRecordController", "getByRelateId", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
    /**
     * 查询-list
     *
     * @param param 查询条件
     * @return
     */
    @RequestMapping(value = "/q/{param}", method = RequestMethod.GET)
    public String list(@PathVariable String param)
    {
        //构建返回
        ResultData<List<FileRecordDto>> resultData = new ResultData<List<FileRecordDto>>();
        
        try
        {
            Map<?, ?> queryParam = JsonUtil.parseToObject(param, Map.class);
            
            resultData = fileRecordService.queryList(queryParam);
        }
        catch (Exception e)
        {
            logger.error("FileRecord", "FileRecordController", "list", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
    /**
     * 创建
     *
     * @param jsonDto 对象字符串
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@RequestBody String jsonDto)
    {
        
        //构建返回
        ResultData<FileRecordDto> resultData = new ResultData<FileRecordDto>();
        
        try
        {
            FileRecordDto dto = JsonUtil.parseToObject(jsonDto, FileRecordDto.class);
            
            FileRecord mo = new FileRecord();
            
            //赋值
            BeanUtils.copyProperties(dto, mo);
            
            int count = fileRecordService.create(mo);
            if (count <= 0)
            {
                resultData.setFail();
            }
        }
        catch (Exception e)
        {
            logger.error("FileRecord", "FileRecordController", "create", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
    /**
     * 更新
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String update(@RequestBody String studentDtoJson)
    {
        
        //构建返回
        ResultData<FileRecordDto> resultData = new ResultData<FileRecordDto>();
        
        try
        {
            FileRecordDto dto = JsonUtil.parseToObject(studentDtoJson, FileRecordDto.class);
            
            FileRecord mo = new FileRecord();
            
            //赋值
            BeanUtils.copyProperties(dto, mo);
            
            int count = fileRecordService.update(mo);
            if (count <= 0)
            {
                resultData.setFail();
            }
        }
        catch (Exception e)
        {
            logger.error("FileRecord", "FileRecordController", "update", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
    /**
     * 删除 by fileNo
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "fileNo/{fileNo}/{operateId}", method = RequestMethod.DELETE)
    public String deleteByFileNo(@PathVariable String fileNo, @PathVariable int operateId)
    {
        //构建返回
        ResultData<FileRecordDto> resultData = new ResultData<FileRecordDto>();
        
        try
        {
            fileRecordService.deleteByFileNo(fileNo);
        }
        catch (Exception e)
        {
            
            logger.error("FileRecord", "FileRecordController", "deleteByFileNo", "", operateId, "", "根据fileNo删除异常", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
}
