package cn.com.eju.deal.base.file.model;

import java.util.Date;

public class FileRecord
{
    private Integer id;
    
    private String fileNo;
    
    private String fileName;
    
    private Integer relateId;
    
    private String fileTypeCode;
    
    private String remark;
    
    private Date dateCreate;
    
    private Integer userCreate;
    
    private Boolean delFlag;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getFileNo()
    {
        return fileNo;
    }
    
    public void setFileNo(String fileNo)
    {
        this.fileNo = fileNo == null ? null : fileNo.trim();
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName == null ? null : fileName.trim();
    }
    
    public Integer getRelateId()
    {
        return relateId;
    }
    
    public void setRelateId(Integer relateId)
    {
        this.relateId = relateId;
    }
    
    public String getFileTypeCode()
    {
        return fileTypeCode;
    }
    
    public void setFileTypeCode(String fileTypeCode)
    {
        this.fileTypeCode = fileTypeCode == null ? null : fileTypeCode.trim();
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark == null ? null : remark.trim();
    }
    
    public Date getDateCreate()
    {
        return dateCreate;
    }
    
    public void setDateCreate(Date dateCreate)
    {
        this.dateCreate = dateCreate;
    }
    
    public Integer getUserCreate()
    {
        return userCreate;
    }
    
    public void setUserCreate(Integer userCreate)
    {
        this.userCreate = userCreate;
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