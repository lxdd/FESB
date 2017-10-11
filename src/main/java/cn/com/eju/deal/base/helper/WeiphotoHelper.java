package cn.com.eju.deal.base.helper;


/**   
* weiphoto文件上传--获取文件路径、文件下载 
* @author (li_xiaodong)
* @date 2016年5月28日 下午4:46:55
*/
public class WeiphotoHelper
{
    /**
     * 日志
     */
    private static final LogHelper logger = LogHelper.getLogger(WeiphotoHelper.class);
    
    /**
     * http://183.136.160.249:2080/weiphoto/upload_pic
     */
    //    private static final String WEIPHOTO_SERVICE = "http://183.136.160.249:2080/";
    private static final String WEIPHOTO_SERVICE = "http://183.136.160.248:2081/";
    
    /**
    * http://183.136.160.249:2080/pic/{pid}/{size}
    */
    private static final String PATH_URL = "pic/%s/%s";
    
    /** 
    * 获取文件路径
    * @param fileCode "720-960-42e972556d09a1fdc280f0eb8e391264"
    * @param size 100
    * @return http://183.136.160.249:2080/pic/{pid}/{size}
    */
    public static String getFilePath(String fileCode, String size)
    {
        String filePath = String.format(WEIPHOTO_SERVICE + PATH_URL, fileCode, size);
        
        return filePath;
    }
    
    
}
