package cn.com.eju.deal.base.seqNo.model;

import cn.com.eju.deal.core.model.BaseModel;

public class SeqNoInfo extends BaseModel
{
    /**
    * 
    */
    private static final long serialVersionUID = -1647778970873579219L;
    
    //规则类型主键
    private int seqTypeId;
    
    //规则类型编码
    private String seqTypeCode;
    
    //规则类型名称
    private String seqTypeName;
    
    //详细规则主键
    private int seqValueId;
    
    //详细规则序号
    private int seqValueIdx;
    
    //详细规则分段类型(固定值,日期,流水号)
    private String valueType;
    
    //日期显示格式
    private String valueFormat;
    
    //固定值
    private String constantValue;
    
    //流水号初始值
    private int initValue;
    
    //流水号下一个值
    private int nextValue;
    
    //流水号最大值
    private Long maxValue;
    
    //流水号步长
    private int step;
    
    //流水号补位符
    private String paddingstr;
    
    //流水号补位方向
    private String paddingDirect;
    
    //流水号补位后最大长度
    private int paddinglength;
    
    //流水号补位后最大长度
    private int version;
    
    public int getSeqTypeId()
    {
        return seqTypeId;
    }
    
    public void setSeqTypeId(int seqTypeId)
    {
        this.seqTypeId = seqTypeId;
    }
    
    public String getSeqTypeCode()
    {
        return seqTypeCode;
    }
    
    public void setSeqTypeCode(String seqTypeCode)
    {
        this.seqTypeCode = seqTypeCode;
    }
    
    public String getSeqTypeName()
    {
        return seqTypeName;
    }
    
    public void setSeqTypeName(String seqTypeName)
    {
        this.seqTypeName = seqTypeName;
    }
    
    public int getSeqValueId()
    {
        return seqValueId;
    }
    
    public void setSeqValueId(int seqValueId)
    {
        this.seqValueId = seqValueId;
    }
    
    public int getSeqValueIdx()
    {
        return seqValueIdx;
    }
    
    public void setSeqValueIdx(int seqValueIdx)
    {
        this.seqValueIdx = seqValueIdx;
    }
    
    public String getValueType()
    {
        return valueType;
    }
    
    public void setValueType(String valueType)
    {
        this.valueType = valueType;
    }
    
    public String getValueFormat()
    {
        return valueFormat;
    }
    
    public void setValueFormat(String valueFormat)
    {
        this.valueFormat = valueFormat;
    }
    
    public String getConstantValue()
    {
        return constantValue;
    }
    
    public void setConstantValue(String constantValue)
    {
        this.constantValue = constantValue;
    }
    
    public int getInitValue()
    {
        return initValue;
    }
    
    public void setInitValue(int initValue)
    {
        this.initValue = initValue;
    }
    
    public int getNextValue()
    {
        return nextValue;
    }
    
    public void setNextValue(int nextValue)
    {
        this.nextValue = nextValue;
    }
    
    public Long getMaxValue()
    {
        return maxValue;
    }
    
    public void setMaxValue(Long maxValue)
    {
        this.maxValue = maxValue;
    }
    
    public int getStep()
    {
        return step;
    }
    
    public void setStep(int step)
    {
        this.step = step;
    }
    
    public String getPaddingstr()
    {
        return paddingstr;
    }
    
    public void setPaddingstr(String paddingstr)
    {
        this.paddingstr = paddingstr;
    }
    
    public String getPaddingDirect()
    {
        return paddingDirect;
    }
    
    public void setPaddingDirect(String paddingDirect)
    {
        this.paddingDirect = paddingDirect;
    }
    
    public int getPaddinglength()
    {
        return paddinglength;
    }
    
    public void setPaddinglength(int paddinglength)
    {
        this.paddinglength = paddinglength;
    }
    
    public int getVersion()
    {
        return version;
    }
    
    public void setVersion(int version)
    {
        this.version = version;
    }
    
}
