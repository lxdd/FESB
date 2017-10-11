package cn.com.eju.deal.base.file.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.core.support.SystemCfg;
import cn.com.eju.deal.core.util.DateUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**   
* 文件操作(上传、下载、查看)帮助类,供FileUtil类调用
* @author mimi.sun
* @date 2015年11月2日 下午5:37:56
*/
public class FileAssist
{
    
    /**
    * 文件上传是否预处理-是
    */
    public static final String UPLOAD_FILE_IS_HANDLE_YES = "1";
    
    /**
     * 文件上传是否预处理-否
     */
    public static final String UPLOAD_FILE_IS_HANDLE_NO = "0";
    
    /**
     * 日志
     */
    private final LogHelper logger = LogHelper.getLogger(FileAssist.class);
    
    //上传文件的byte数组
    byte[] bs = new byte[0];
    
    //加密解密处理
    private EncryptUtil encryptUtils = new EncryptUtil();
    
    //界限字符串
    private final String boundary = "-------" + new Date();
    
    private final String DATE_FORMAT = "HHmmssSSS";
    
    private final SimpleDateFormat DATE_DIR_SDF = new SimpleDateFormat("yyyyMMdd");
    
//    private static FileHelper instance = null;
    
    /** 
    * 实现单例
    * @return fileHelper对象
    */
//    public static synchronized FileHelper getInstance()
//    {
//        
//        if (null == instance)
//        {
//            instance = new FileHelper();
//        }
//        
//        return instance;
//        
//    }
    
    /** 
    * 上传操作
    * @return 响应结果 {{pic_height=352, flag=true, pic_width=441, pic_size=43317, 
    * pic_path=/BHME/Source_pic/cd/62/BHMEc6dfcb94569a9ec6a43ecb1aa3402c2d.jpg, 
    * pic_id=BHMEc6dfcb94569a9ec6a43ecb1aa3402c2d.jpg}}
    */
    public Map<String, Object> upload(File file, String isHandle, Map<String, Object> optionalMap, String permitCode, String fileCategory, String uploadUrl)
    {
        Map<String, Object> rspMap = new HashMap<String, Object>();
        
        try
        {
            
            //授权号
            //String permitCode = SystemParam.getWebConfigValue("file_permit_code");
            //String permitCode = SystemCfg.getString("file_permit_code");
            //四位文件类型(系统中心申请注册，如CRIC)
            //String fileCategory = SystemParam.getWebConfigValue("file_category");
            //String fileCategory = SystemCfg.getString("file_category");
            
            //文本类型的参数
            Map<String, String> params = new HashMap<String, String>();
            //授权号
            params.put("permit_code", permitCode);
            //四位文件类型(系统中心申请注册，如CRIC)
            params.put("file_category", fileCategory);
            //验证令牌
            params.put("key", getMd5PermitCode(permitCode));
            
            //将文件内容转成byte数组
            appendBytes(getFileBytes(file));
            
            //文件内容的md5
            String md5fileInfo = encryptUtils.MD5(bs.toString());
            //文件编号
            params.put("fileid", fileCategory + md5fileInfo);
            //是否需要返回文件路径
            params.put("is_return_path", "true");
            
            //选择预处理(进行图片处理)
            if (UPLOAD_FILE_IS_HANDLE_YES.equals(isHandle))
            {
                JSONObject obj = new JSONObject();
                JSONObject obj2 = new JSONObject();
                //图片宽度
                obj2.put("width", optionalMap.get("width"));
                //图片高度
                obj2.put("height", optionalMap.get("height"));
                //裁剪类型
                obj2.put("cut_type", optionalMap.get("cutType"));
                //水印位置
                obj2.put("water_position", optionalMap.get("waterPosition"));
                //水印图片
                obj2.put("water_pic", optionalMap.get("waterPic"));
                obj.put("1", obj2);
                //预处理参数
                params.put("params", obj.toJSONString());
            }
            
            //数据流的形式
            Map<String, byte[]> files = new HashMap<String, byte[]>();
            //获取文件名称
            String fileName = file.getName();
            files.put(fileName, bs);
            
            // 设置文件上传的路径
            //URL url = new URL(SystemCfg.getString("uploadfile_path"));
            URL url = new URL(uploadUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // 设置HttpUrlConnection参数
            StringBuilder sb = setConnectionInfo(conn, params);
            
            // 请求文件上传操作
            rspMap = executeUpload(conn, sb, files);
            
        }
        catch (FileNotFoundException e)
        {
            logger.error("", e.getMessage(), e, null);
            rspMap.put("flag", false);
            rspMap.put("result", e.getMessage());
        }
        catch (IOException e)
        {
            logger.error("", e.getMessage(), e, null);
            rspMap.put("flag", false);
            rspMap.put("result", e.getMessage());
        }
        catch (Exception e)
        {
            logger.error("", e.getMessage(), e, null);
            rspMap.put("flag", false);
            rspMap.put("result", e.getMessage());
        }
        return rspMap;
    }
    
    /** 
    * 请求文件上传
    * @param conn HttpUrlConnection对象
    * @param sb 组拼后的文本类型参数
    * @param files 文件数据
    * @return 响应结果
    * @throws FileNotFoundException
    * @throws IOException
    */
    private Map<String, Object> executeUpload(HttpURLConnection conn, StringBuilder sb, Map<String, byte[]> files)
        throws FileNotFoundException, IOException
    {
        
        Map<String, Object> hashMap = new HashMap<String, Object>();
        
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        InputStream in = null;
        // 发送文件数据
        if (files != null)
        {
            for (Map.Entry<String, byte[]> file : files.entrySet())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append("--");
                sb1.append(boundary);
                sb1.append("\r\n");
                sb1.append("Content-Disposition: form-data; name=\"pfile\"; filename=\"" + file.getKey() + "\""
                    + "\r\n");
                sb1.append("Content-Type: application/octet-stream; charset=UTF-8" + "\r\n");
                sb1.append("\r\n");
                outStream.write(sb1.toString().getBytes());
                
                outStream.write(file.getValue());
                
                outStream.write("\r\n".getBytes());
            }
            // 请求结束标志
            byte[] end_data = ("--" + boundary + "--" + "\r\n").getBytes();
            outStream.write(end_data);
            outStream.flush();
            
            StringBuilder strBui = null;
            // 得到响应码
            int res = conn.getResponseCode();
            if (res == 200)
            {
                in = conn.getInputStream();
                int ch;
                strBui = new StringBuilder();
                while ((ch = in.read()) != -1)
                {
                    strBui.append((char)ch);
                }
                
                in.close();
                
                // 将json字符串转换成jsonObject
                JSONObject jsonObj = JSONObject.parseObject(strBui.toString());
                JSONObject jsonObj2 = jsonObj.getJSONObject("result");
                hashMap = reflect(jsonObj2);
                hashMap.put("flag", jsonObj.get("flag"));
            }
            else
            {
                hashMap.put("flag", false);
                hashMap.put("result", res);
            }
            
            outStream.close();
            conn.disconnect();
        }
        else
        {
            hashMap.put("flag", false);
            hashMap.put("result", "上传文件是空文件!");
        }
        //服务器返回来的数据
        return hashMap;
    }
    
    /** 
    * 请求下载文件(自动弹出保存窗口)
    * @param conn HttpUrlConnection对象
    * @param sb 组拼后的文本类型参数
    * @param response 请求响应对象
    * @throws Exception
    */
    public void executeDownload(String fileId, String downloadFileName, HttpURLConnection conn, StringBuilder sb,
        HttpServletResponse response)
        throws Exception
    {
        
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        InputStream in = null;
        
        // 请求结束标志
        byte[] end_data = ("--" + boundary + "--" + "\r\n").getBytes();
        
        outStream.write(end_data);
        outStream.flush();
        StringBuilder strBui = null;
        // 得到响应码
        int res = conn.getResponseCode();
        if (res == 200)
        {
            in = conn.getInputStream();
            int count = 0;
            strBui = new StringBuilder();
            // 必要地清除response中的缓存信息
            response.reset();
            //告诉浏览器输出内容为流
            response.setContentType("application/octet-stream");
            //注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
            //Content-Disposition中指定的类型是文件的扩展名，
            //并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，
            //点保存后，文件以filename的值命名，保存类型以Content中设置的为准
            if (downloadFileName != null)
            {
                response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(downloadFileName, "UTF-8"));
            }
            else
            {
                response.setHeader("Content-Disposition", "attachment; filename=" + fileId);
            }
            
            // response输出文件流
            OutputStream out = response.getOutputStream();
            while ((count = in.read()) != -1)
            {
                strBui.append((char)count);
                out.write(count);
            }
            out.close();
        }
        outStream.close();
        conn.disconnect();
    }
    
    /** 
    * 设置请求参数
    * @param conn HttpUrlConnection对象
    * @param params 文本类型的参数
    * @return 组拼后的文本类型参数
    * @throws IOException
    */
    public StringBuilder setConnectionInfo(HttpURLConnection conn, Map<String, String> params)
        throws IOException
    {
        // 允许输入
        conn.setDoInput(true);
        // 允许输出
        conn.setDoOutput(true);
        // 不允许使用缓存
        conn.setUseCaches(false);
        // 设定请求的方法为"POST"
        conn.setRequestMethod("POST");
        // 设定传送的内容类型
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        
        //组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            sb.append("--");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + "\r\n");
            sb.append("\r\n");
            sb.append(entry.getValue());
            sb.append("\r\n");
        }
        return sb;
    }
    
    /** 
     * 复制byte数组 
     * @param bytes byte数组 
     */
    private void appendBytes(byte[] bytes)
    {
        byte[] newByte = new byte[bs.length + bytes.length];
        //arraycopy(被复制的数组, 从第几个元素开始复制, 要复制到的数组, 从第几个元素开始粘贴, 一共需要复制的元素个数);
        System.arraycopy(bs, 0, newByte, 0, bs.length);
        System.arraycopy(bytes, 0, newByte, bs.length, bytes.length);
        bs = newByte;
    }
    
    /** 
     * 获得指定文件的byte数组 
     * @param file 上传的文件
     * @return byte数组
     */
    private byte[] getFileBytes(File file)
    {
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }
    
    /** 
     * 获取验证令牌(根据授权号和当天时间)
     * @param permitCode 授权号
     * @return 验证令牌
     */
    public String getMd5PermitCode(String permitCode)
        throws Exception
    {
        String mD5String = "";
        
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        
        df.format(new Date());
        
        // 获取验证信息:授权号和当天时间（格式如20151031）的md5值
        mD5String = encryptUtils.MD5(permitCode + df.format(new Date()));
        
        return mD5String;
    }
    
    /** 
     * 将JSONObjec对象转换成Map-List集合 
     * @see JSONHelper#reflect(JSONArray) 
     * @param json 需要转换的JSON对象
     * @return 转换后的Map对象
     */
    private Map<String, Object> reflect(JSONObject json)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<?> keys = json.keySet();
        for (Object key : keys)
        {
            Object o = json.get(key);
            if (o instanceof JSONObject)
                map.put((String)key, reflect((JSONObject)o));
            else
                map.put((String)key, o);
        }
        return map;
    }
    
    /**
     * 得到一个临时文件绝对路径
     * @param fileName 文件名 如 a.txt
     * @param request
     * @return
     */
    public String getTmpRealPath(String fileName, HttpServletRequest request)
    {
        
        String tempFilePath = SystemCfg.getString("tempFilePath");
        
        return getTmpRealPath(request, fileName, tempFilePath);
    }
    
    /**
     * 得到一个临时文件绝对路径
     * @param fileName 文件名 如 a.txt
     * @param request
     * @return
     */
    public String getTmpRealPath(HttpServletRequest request, String fileName, String tempFilePath)
    {
        Date date = new Date();
        tempFilePath = MessageFormat.format(tempFilePath, DATE_DIR_SDF.format(date));
        int index = fileName.lastIndexOf(".");
        if (index > 0)
        {
            fileName = DateUtil.fmtDate(new Date(), DATE_FORMAT) + fileName.substring(index);
        }
        // 创建目录
        File uploadDir = new File(request.getSession().getServletContext().getRealPath(tempFilePath));
        if (!uploadDir.exists())
        {
            uploadDir.mkdirs();
        }
        return uploadDir + File.separator + fileName;
    }
    
}
