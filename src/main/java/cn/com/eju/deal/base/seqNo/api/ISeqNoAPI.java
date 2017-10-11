package cn.com.eju.deal.base.seqNo.api;

import cn.com.eju.deal.core.support.ResultData;

/**   
* seqNoAPI接口
* @author
* @date 2016年3月25日 上午10:20:44
*/
public interface ISeqNoAPI
{
    /**
    * 
    * 获取最新的编号
    * @param operateUserId
    * @param typecode
    * @return
     */
    public ResultData<String> getSeqNoByTypeCode(String typeCode);
    
    /**
    *
    * 查看下一个编号
    * @param operateUserId
    * @param typecode
    * @param upflag 是否更新
    * @return
    */
    public ResultData<String> getSeqNoByTypeCode(String typeCode, boolean upflag);
    
}
