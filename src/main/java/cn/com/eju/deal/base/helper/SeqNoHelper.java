package cn.com.eju.deal.base.helper;

import javax.annotation.Resource;

import cn.com.eju.deal.base.seqNo.api.ISeqNoAPI;
import cn.com.eju.deal.core.support.ResultData;


/**   
* 获取编号的共通
* @author (li_xiaodong)
* @date 2015年10月23日 下午1:40:33
*/
public class SeqNoHelper
{
    /**
    * 采集番号API
    */
    @Resource(name = "seqNoAPI")
    ISeqNoAPI seqNoAPI;
   
    /**
    * 
    * 获取最新的编号
    * @param operateUserId
    * @param typecode
    * @return
     */
    public String getSeqNoByTypeCode(String typecode)
    {
        ResultData<String> result = seqNoAPI.getSeqNoByTypeCode(typecode);
        
        String seqNo = result.getReturnData();
        
        return seqNo;
    };
    
   
}
