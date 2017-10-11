package cn.com.eju.deal.base.file.model;

public class FileType
{
    private Integer id;
    
    private String fileTypeCode;
    
    private String fileTypeName;
    
    private Boolean delFlag;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getFileTypeCode()
    {
        return fileTypeCode;
    }
    
    public void setFileTypeCode(String fileTypeCode)
    {
        this.fileTypeCode = fileTypeCode == null ? null : fileTypeCode.trim();
    }
    
    public String getFileTypeName()
    {
        return fileTypeName;
    }
    
    public void setFileTypeName(String fileTypeName)
    {
        this.fileTypeName = fileTypeName == null ? null : fileTypeName.trim();
    }
    
    public Boolean getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Boolean delFlag)
    {
        this.delFlag = delFlag;
    }
}