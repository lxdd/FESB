package cn.com.eju.deal.base.file.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.eju.deal.core.support.SystemCfg;
import cn.com.eju.deal.core.util.DateUtil;
import cn.com.eju.deal.core.util.JsonUtil;

/**   
* weiphoto文件系统 -- 文件上传、预览工具类
* @author (li_xiaodong)
* @date 2016年5月25日 下午2:33:47
*/
public class WeiphotoUtil
{
    private final String DATE_FORMAT = "HHmmssSSS";
    
    private final SimpleDateFormat DATE_DIR_SDF = new SimpleDateFormat("yyyyMMdd");
    
    //界限字符串
    private final String boundary = "-------" + new Date();
    
    /** 
    * 图片上传
    * @param urlStr
    * @param tempFile
    * @param fileName
    * @return
    * @throws Exception
    */
    public static Map<String, Object> upload(String urlStr, File tempFile, String fileName)
        throws Exception
    {
        //响应map
        Map<String, Object> rspMap = new HashMap<String, Object>();
        
        // 换行符
        final String newLine = "\r\n";
        final String boundaryPrefix = "--";
        
        // 定义数据分隔线
        //String BOUNDARY = "========7d4a6d158c9";
        String BOUNDARY = "---------------------------7d4a6d158c9"; // 分隔符
        
        // 服务器的域名
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("contentType", "charset=utf-8");
        conn.setRequestMethod("POST");
        
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(tempFile));
        
        StringBuffer sb = new StringBuffer();
        sb.append(boundaryPrefix);
        sb.append(BOUNDARY);
        sb.append(newLine);
        sb.append("Content-Disposition: form-data; \r\n name=\"pic\"; filename=\"" + fileName + "\"\r\n");
        sb.append("Content-Type: application/msword\r\n\r\n");
        
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="
            + "---------------------------7d4a6d158c9");
        
        byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
        
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        
        dos.write(sb.toString().getBytes("utf-8"));
        
        int cc = 0;
        while ((cc = input.read()) != -1)
        {
            dos.write(cc);
        }
        dos.write(end_data);
        dos.flush();
        dos.close();
        
        //输出
        StringBuilder strBd = new StringBuilder();
        
        InputStream is = conn.getInputStream();
        
        int ch;
        while ((ch = is.read()) != -1)
        {
            strBd.append((char)ch);
        }
        
        if (is != null)
        {
            is.close();
        }
        
        rspMap = JsonUtil.parseToObject(strBd.toString(), Map.class);
        
        return rspMap;
        
    }
    
    public static String upload1(String urlStr, File tempFile, String fileName)
        throws Exception
    {
        String picId = null;
        
        try
        {
            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            
            // 定义数据分隔线
            //String BOUNDARY = "========7d4a6d158c9";
            String BOUNDARY = "---------------------------7d4a6d158c9"; // 分隔符
            
            // 服务器的域名
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            
            // 设定请求的方法为"POST"，默认是GET
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            conn.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoInput(true);
            // Post 请求不能使用缓存
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            //conn.setRequestProperty("contentType", "charset=utf-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            
            // 设定传送的内容类型是可序列化的java对象
            // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            //conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
            
            // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            
            // 上传文件
            //File file = new File(fileName);
            
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,pic参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"pic\";filename=\"" + fileName + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream" + newLine);
            
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);
            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());
            
            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(tempFile));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1)
            {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();
            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();
            // 写上结尾标识
            
            out.write(end_data);
            
            // 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
            out.flush();
            
            // 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓//冲区中, 在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
            out.close();
            
            //定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                System.out.println(line);
            }
            
            //输出
            StringBuilder strBd = new StringBuilder();
            
            // 调用HttpURLConnection连接对象的getInputStream()函数, 
            // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端
            InputStream is = conn.getInputStream();
            
            int ch;
            while ((ch = is.read()) != -1)
            {
                strBd.append((char)ch);
            }
            
            if (is != null)
            {
                is.close();
            }
            
            return strBd.toString().replace("\r\n", "");
            
        }
        catch (Exception e)
        {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        
        return picId;
    }
    
    /** 
     * OA附件上传方法
     * @param oaUrl
     * @param token
     * @param senderLoginName
     * @param tempFile
     * @return
     * @throws Exception
     */
    public static String get(String url, String fid)
        throws Exception
    {
        //接口返回信息
        String rebackMsg = null;
        
        return rebackMsg;
        
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
}
