package cn.com.eju.deal.base.dto.code;

import cn.com.eju.deal.core.model.BaseModel;

/**   
* 码表对象
* @author (li_xiaodong)
* @date 2015年11月9日 下午3:31:04
*/
public class CommonCodeDto extends BaseModel
{
    /**
    * TODO(用一句话描述这个变量表示什么)
    */ 
    private static final long serialVersionUID = -1805226259908270485L;

    /**
     * 类型值ID
     */
    private Integer dicId;
    
    /**
     * 字典类型编号
     */
    private Integer typeId;
    
    /**
     * 字典类型名字
     */
    private String typeName;
    
    /**
     * 字典类型组名字
     */
    private String dicGroup;
    
    /**
     * 说明类型编码
     */
    private Integer dicCode;
    
    /**
     * 说明类型值
     */
    private String dicValue;
    
    /**
    * 顺序
    */
    private Integer dicSortNo;
    
    /**
     * 类型值编号
     * @return the dicId 
     */
    public Integer getDicId()
    {
        return dicId;
    }
    
    /**
     * 设置类型值编号为dicId
     * @param dicId the dicId to set 
     */
    public void setDicId(Integer dicId)
    {
        this.dicId = dicId;
    }
    
    /**
     * 字典类型编号
     * @return the typeId 
     */
    public Integer getTypeId()
    {
        return typeId;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    /**
     * 字典类型编号设为typeId
     * @param typeId the typeId to set 
     */
    public void setTypeId(Integer typeId)
    {
        this.typeId = typeId;
    }
    
    public String getDicGroup()
    {
        return dicGroup;
    }
    
    public void setDicGroup(String dicGroup)
    {
        this.dicGroup = dicGroup;
    }
    
    /**
     * 类型值编码
     * @return the dicCode 
     */
    public Integer getDicCode()
    {
        return dicCode;
    }
    
    /**
     * 将类型值编码设为dicCode
     * @param dicCode the dicCode to set 
     */
    public void setDicCode(Integer dicCode)
    {
        this.dicCode = dicCode;
    }
    
    /**
     * 类型值
     * @return the dicValue 
     */
    public String getDicValue()
    {
        return dicValue;
    }
    
    /**
     * 将类型值设为dicValue
     * @param dicValue the dicValue to set 
     */
    public void setDicValue(String dicValue)
    {
        this.dicValue = dicValue;
    }
    
    public Integer getDicSortNo()
    {
        return dicSortNo;
    }
    
    public void setDicSortNo(Integer dicSortNo)
    {
        this.dicSortNo = dicSortNo;
    }
    
}