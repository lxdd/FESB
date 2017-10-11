package cn.com.eju.deal.base.dto.file;

import java.io.Serializable;
import java.util.Date;

/**   
* 文件渠道系统-file主表fileDto
* @author (li_xiaodong)
* @date 2016年6月29日 下午9:19:27
*/
public class FileDto implements Serializable
{
    
    /**
    * 
    */
    private static final long serialVersionUID = -3782793921175416768L;
    
    private Integer fileId;
    
    private String fileNo;
    
    private String fileCode;
    
    private Integer fileState;
    
    private String channelCode;
    
    private String systemCode;
    
    private Date uploadTime;
    
    private String remark;
    
    private Date dateCreate;
    
    private String delFlag;
    
    public Integer getFileId()
    {
        return fileId;
    }
    
    public void setFileId(Integer fileId)
    {
        this.fileId = fileId;
    }
    
    public String getFileNo()
    {
        return fileNo;
    }
    
    public void setFileNo(String fileNo)
    {
        this.fileNo = fileNo == null ? null : fileNo.trim();
    }
    
    public String getFileCode()
    {
        return fileCode;
    }
    
    public void setFileCode(String fileCode)
    {
        this.fileCode = fileCode == null ? null : fileCode.trim();
    }
    
    public Integer getFileState()
    {
        return fileState;
    }
    
    public void setFileState(Integer fileState)
    {
        this.fileState = fileState;
    }
    
    public String getChannelCode()
    {
        return channelCode;
    }
    
    public void setChannelCode(String channelCode)
    {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }
    
    public String getSystemCode()
    {
        return systemCode;
    }
    
    public void setSystemCode(String systemCode)
    {
        this.systemCode = systemCode == null ? null : systemCode.trim();
    }
    
    public Date getUploadTime()
    {
        return uploadTime;
    }
    
    public void setUploadTime(Date uploadTime)
    {
        this.uploadTime = uploadTime;
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
    
    public String getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
}