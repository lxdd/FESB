package cn.com.eju.deal.base.seqNo.dao;

import java.util.List;

import cn.com.eju.deal.base.seqNo.model.SeqNoInfo;
import cn.com.eju.deal.core.dao.IDao;

/**   
* TODO (这里用一句话描述这个文件的作用)
* @author (guowei)
* @date 2015年10月28日  17:26
*/
public interface SeqNoInfoMapper extends IDao <SeqNoInfo> 
{
    List<SeqNoInfo> getListByTypeCode(String typeCode);
    

    String getFormatValueByCode(String typeCode);
    
}
