package cn.com.eju.deal.base.seqNo.api.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.seqNo.SeqNoDicConstants;
import cn.com.eju.deal.base.seqNo.api.ISeqNoAPI;
import cn.com.eju.deal.base.seqNo.dao.SeqNoInfoMapper;
import cn.com.eju.deal.base.seqNo.model.SeqNoInfo;
import cn.com.eju.deal.core.support.ResultData;
import cn.com.eju.deal.core.support.ReturnCode;
import cn.com.eju.deal.core.util.StringUtil;

/**   
* seqNoAPI接口
* @author
* @date 2016年3月25日 上午10:20:10
*/
@Service("seqNoAPI")
public class SeqNoAPIImpl implements ISeqNoAPI
{
    @Resource
    private SeqNoInfoMapper seqNoDao;
    
    /**
    * 
    * 获取最新的编号
    * @param operateUserId
    * @param typecode
    * @return
     */
    @Override
    public ResultData<String> getSeqNoByTypeCode(String typecode)
    {
        ResultData<String> seqNoResult = new ResultData<String>();
        
        seqNoResult = getSeqNoByTypeCode(typecode, true);
        
        return seqNoResult;
    };
    
    /**
     *
    * 查看下一个编号
    * @param operateUserId
    * @param typecode
    * @param upflag :false 只看不取，true:获取最新
    * @return
     */
    @Override
    public ResultData<String> getSeqNoByTypeCode(String typecode, boolean upflag)
    {
        ResultData<String> seqNoResult = new ResultData<String>();
        try
        {
            seqNoResult = this.getSeqNo(typecode, upflag);
        }
        catch (Exception e)
        {
            seqNoResult.setReturnCode(ReturnCode.FAILURE);
        }
        return seqNoResult;
        
    };
    
    /** 
    * 
    * @param typeCode
    * @param update
    * @return
    */
    private ResultData<String> getSeqNo(String typeCode, boolean update)
    {
        ResultData<String> seqNoResult = new ResultData<String>();
        
        if (StringUtil.isEmpty(typeCode))
        {
            seqNoResult.setReturnCode(ReturnCode.FAILURE);
            seqNoResult.setReturnMsg("规则没有指定！");
            return seqNoResult;
        }
        
        //获取详细规则
        List<SeqNoInfo> seqList = this.seqNoDao.getListByTypeCode(typeCode);
        if (seqList == null || seqList.size() == 0)
        {
            seqNoResult.setReturnCode(ReturnCode.FAILURE);
            seqNoResult.setReturnMsg("规则未定义详细生成规则");
            return seqNoResult;
        }
        //
        seqNoResult = generateNo(seqList, update);
        
        return seqNoResult;
    }
    
    /** 
    * 生成
    * @param seqList
    * @param update
    * @return
    */
    private ResultData<String> generateNo(List<SeqNoInfo> seqList, boolean update)
    {
        
        ResultData<String> br = new ResultData<String>();
        
        StringBuffer strB = new StringBuffer();
        
        SeqNoInfo vo = null;
        
        for (int i = 0; i < seqList.size(); i++)
        {
            vo = seqList.get(i);
            
            String type = vo.getValueType();
            
            //固定值
            if (SeqNoDicConstants.TYPE_122_1221.equals(type))
            {
                // 连接固定值
                strB.append(StringUtil.toStringWithEmpty(vo.getConstantValue()));
            }
            //系统日期
            else if (SeqNoDicConstants.TYPE_122_1222.equals(type))
            {
                String formattype = vo.getValueFormat();
                
                // 系统日期
                String formatPattern = seqNoDao.getFormatValueByCode(formattype);
                Date currentDate = new Date();
                String formatDate = null;
                if (StringUtil.isNotEmpty(formatPattern))
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
                    formatDate = dateFormat.format(currentDate);
                }
                else
                {
                    if (StringUtil.isEmpty(formatDate))
                    {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                        dateFormat.format(currentDate);
                    }
                }
                
                strB.append(formatDate);
            }
            //流水号
            else if (SeqNoDicConstants.TYPE_122_1223.equals(type))
            {
                ResultData<String> brSeq = genSeq(vo, update);
                if (!ReturnCode.SUCCESS.equals(brSeq.getReturnCode()))
                {
                    br.setReturnMsg(brSeq.getReturnMsg());
                    br.setReturnCode(ReturnCode.FAILURE);
                    break;
                }
                strB.append((String)brSeq.getReturnData());
            }
            vo = new SeqNoInfo();
        }
        
        br.setReturnCode(ReturnCode.SUCCESS);
        
        br.setReturnData(strB.toString());
        
        return br;
    }
    
    /**
     * 生成序列号。
     * 
     * @param seq
     *            序列号记录
     * @param update
     *            是否更新到数据库
     * @return
     */
    private ResultData<String> genSeq(SeqNoInfo seq, boolean update)
    {
        ResultData<String> br = new ResultData<String>();
        String s = "";
        //补位符
        String paddingStr = (seq.getPaddingstr() == null) ? "0" : seq.getPaddingstr();
        //补位后长度
        Integer length = seq.getPaddinglength();
        //初期值
        Integer initValue = seq.getInitValue();
        if (initValue == null)
        {
            initValue = 1;
            seq.setInitValue(initValue);
        }
        //下一个值
        Integer nextValue = seq.getNextValue();
        if (nextValue == null)
        {
            nextValue = initValue;
        }
        if (nextValue < initValue)
        {
            nextValue = initValue;
        }
        
        Integer step = seq.getStep();
        if (step == null)
        {
            step = 1;
            seq.setStep(step);
        }
        // set next value.
        Long newNextValue = (long)(nextValue + step);
        Long maxValue = seq.getMaxValue();
        if (maxValue == null)
        {
            maxValue = (long)(Integer.MAX_VALUE - 1);
            seq.setMaxValue(maxValue);
        }
        //
        if (maxValue != null && (newNextValue.intValue() > maxValue.intValue()))
        {
            newNextValue = (long)initValue;
        }
        //
        if (length < ("" + nextValue).length())
        {
            br.setReturnMsg("下个值[" + nextValue + "]的长度超过设定的长度[" + length + "]");
            br.setReturnCode(ReturnCode.FAILURE);
            return br;
        }
        if (update)
        {
            int updatereturn = this.save(seq, newNextValue);
            if (updatereturn < 1)
            {
                br.setReturnMsg("无法正常获得流水号，请重新提交。");
                br.setReturnCode(ReturnCode.FAILURE);
                return br;
            }
        }
        s = "" + nextValue;
        if (length <= 0)
        {
            br.setReturnMsg("补位启用,但是长度不足");
            br.setReturnCode(ReturnCode.FAILURE);
            return br;
        }
        else
        {
            if (SeqNoDicConstants.TYPE_124_1241.equals(seq.getPaddingDirect()))
            {
                s = leftPad(s, length, paddingStr);
            }
            else if (SeqNoDicConstants.TYPE_124_1242.equals(seq.getPaddingDirect()))
            {
                s = rightPad(s, length, paddingStr);
            }
            
        }
        br.setReturnData(s);
        br.setReturnCode(ReturnCode.SUCCESS);
        return br;
    }
    
    private int save(SeqNoInfo seq, long newNextValue)
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("version", seq.getVersion());
        map.put("seqValueId", seq.getSeqValueId());
        map.put("newnextValue", newNextValue);
        
        return this.seqNoDao.updateParam(map);
        
    }
    
    public static String leftPad(String str, int size, String padStr)
    {
        if (str == null)
        {
            return null;
        }
        if ((padStr == null) || (padStr.length() == 0))
        {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0)
        {
            return str;
        }
        if ((padLen == 1) && (pads <= 8192))
        {
            return leftPad(str, size, padStr.charAt(0));
        }
        
        if (pads == padLen)
            return padStr.concat(str);
        if (pads < padLen)
        {
            return padStr.substring(0, pads).concat(str);
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; ++i)
        {
            padding[i] = padChars[(i % padLen)];
        }
        return new String(padding).concat(str);
    }
    
    public static String rightPad(String str, int size, char padChar)
    {
        if (str == null)
        {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0)
        {
            return str;
        }
        if (pads > 8192)
        {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(padding(pads, padChar));
    }
    
    public static String rightPad(String str, int size, String padStr)
    {
        if (str == null)
        {
            return null;
        }
        if ((padStr == null) || (padStr.length() == 0))
        {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0)
        {
            return str;
        }
        if ((padLen == 1) && (pads <= 8192))
        {
            return rightPad(str, size, padStr.charAt(0));
        }
        
        if (pads == padLen)
            return str.concat(padStr);
        if (pads < padLen)
        {
            return str.concat(padStr.substring(0, pads));
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; ++i)
        {
            padding[i] = padChars[(i % padLen)];
        }
        return str.concat(new String(padding));
    }
    
    public static String leftPad(String str, int size, char padChar)
    {
        if (str == null)
        {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0)
        {
            return str;
        }
        if (pads > 8192)
        {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }
    
    public static String padding(int repeat, char padChar)
        throws IndexOutOfBoundsException
    {
        if (repeat < 0)
        {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; ++i)
        {
            buf[i] = padChar;
        }
        return new String(buf);
    }
    
}
