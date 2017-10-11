package cn.com.eju.deal.base.file.dao;

import cn.com.eju.deal.base.file.model.FileRecord;
import cn.com.eju.deal.core.dao.IDao;

public interface FileRecordMapper extends IDao<FileRecord>
{
    /** 
    * 根据relateId获取FileRecord
    * @param relateId
    * @return
    * @throws Exception
    */
    FileRecord getByRelateId(String relateId)
        throws Exception;
    
    /** 
    * 删除 by fileNo
    * @param fileNo
    * @param operateId
    * @return
    * @throws Exception
    */
    int deleteByFileNo(String fileNo)
        throws Exception;
}