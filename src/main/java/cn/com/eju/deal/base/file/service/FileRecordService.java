package cn.com.eju.deal.base.file.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.dto.file.FileRecordDto;
import cn.com.eju.deal.base.file.dao.FileRecordMapper;
import cn.com.eju.deal.base.file.model.FileRecord;
import cn.com.eju.deal.base.service.BaseService;
import cn.com.eju.deal.core.support.QueryConst;
import cn.com.eju.deal.core.support.ResultData;

@Service("fileRecordService")
public class FileRecordService extends BaseService
{
    
    @Resource(name = "fileRecordMapper")
    private FileRecordMapper fileRecordMapper;
    
    /** 
    * 查询
    * @param id
    * @return
    */
    
    public FileRecord getById(int id)
        throws Exception
    {
        FileRecord fileRecord = fileRecordMapper.getById(id);
        return fileRecord;
    }
    
    /** 
     * 查询-根据relateId
     * @param id
     * @return
     */
    
    public FileRecord getByRelateId(String relateId)
        throws Exception
    {
        FileRecord fileRecord = fileRecordMapper.getByRelateId(relateId);
        return fileRecord;
    }
    
    /** 
     * 查询-list
     * @param queryParam
     * @return
     */
    
    public ResultData<List<FileRecordDto>> queryList(Map<?, ?> param)
        throws Exception
    {
        
        //构建返回
        ResultData<List<FileRecordDto>> resultData = new ResultData<List<FileRecordDto>>();
        
        //查询
        final List<FileRecord> moList = fileRecordMapper.queryList(param);
        
        //转换
        List<FileRecordDto> dtoList = convertData(moList);
        
        resultData.setTotalCount((String)param.get(QueryConst.TOTAL_COUNT));
        
        resultData.setReturnData(dtoList);
        
        return resultData;
    }
    
    /** 
     * 创建
     * @param param
     * @return
     */
    public int create(FileRecord fileRecord)
    {
        int count = fileRecordMapper.create(fileRecord);
        return count;
    }
    
    /** 
     * 更新
     * @param param
     * @return
     */
    
    public int update(FileRecord fileRecord)
        throws Exception
    {
        int count = fileRecordMapper.update(fileRecord);
        return count;
    }
    
    /** 
    * 删除 by fileNo
    * @param id 
    * @param operateId 更新人
    * @return
    */
    
    public int deleteByFileNo(String fileNo)
        throws Exception
    {
        int count = fileRecordMapper.deleteByFileNo(fileNo);
        return count;
    }
    
    /** 
     * 对象转换MO--DTO
     * @param stuList
     * @return List<StudentDto>
     */
    private List<FileRecordDto> convertData(List<FileRecord> moList)
        throws Exception
    {
        List<FileRecordDto> dtoList = new ArrayList<FileRecordDto>();
        
        if (null != moList && !moList.isEmpty())
        {
            FileRecordDto dto = null;
            for (FileRecord mo : moList)
            {
                dto = new FileRecordDto();
                BeanUtils.copyProperties(mo, dto);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
}
