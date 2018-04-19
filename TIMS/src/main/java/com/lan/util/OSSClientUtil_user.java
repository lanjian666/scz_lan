package com.lan.util;

import com.aliyun.oss.OSSClient;
import com.lan.model.UserInformation;
import com.lan.vo.ProjectVO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 阿里云 OSS工具类
 *
 */
@Controller
public class OSSClientUtil_user {
    public static UserInformation getUserInfor(HttpServletRequest request,UserInformation userInformation){
        Map map=new HashMap();
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String headPortrait=null;

        // 在解析请求之前先判断请求类型是否为文件上传类型
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String status = "not OK";
        // 文件上传处理工厂
        FileItemFactory factory = new DiskFileItemFactory();
        // 创建文件上传处理器
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置编码，获取的FileItem时，还需要转码,(经测试只需在获取时设置就行）
        //upload.setHeaderEncoding("utf-8");
        // 开始解析请求信息
        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 对所有请求信息进行判断
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            // 信息为普通的格式
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                //获取的FileItem执行如下转码方式
                String value = null;
                try {
                    value = item.getString("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                map.put(fieldName,value);
            }

            // 信息为文件格式
            else if(item.getName().length()>0){
                String fileName = item.getName();
                InputStream inputStream = null;
                try {
                    inputStream = item.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 将文件写入
                String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
                // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
                // ht tps://ram.console.aliyun.com 创建
                String accessKeyId = "LTAIvTthEd7SfAPh";
                String accessKeySecret = "1MbHX744NDwFfW7yEptCEvurdGgq73";
                // 创建OSSClient实例
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                // 上传文件
                headPortrait=fileName;
                ossClient.putObject("lanjian666", headPortrait, inputStream);
                // 关闭client
                ossClient.shutdown();
            }
        }
        userInformation.setUsername((String)map.get("username"));
        userInformation.setPassword((String)map.get("password"));
        userInformation.setName((String)map.get("name"));
        userInformation.setSex((String)map.get("sex"));
        userInformation.setPhone((String)map.get("phone"));
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        try {
            userInformation.setBirthday(sdf.parse((String)map.get("birthday")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        OSSClientUtil ossClientUtil=new OSSClientUtil();
        String url = ossClientUtil.getImgUrl(headPortrait);
        userInformation.setPhotoAddress(url);

        return  userInformation;
    }
    public static final Logger logger = LoggerFactory.getLogger(OSSClientUtil_user.class);
    // endpoint
    private String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    // accessKey
    private String accessKeyId = "LTAIvTthEd7SfAPh";
    private String accessKeySecret = "1MbHX744NDwFfW7yEptCEvurdGgq73";
    // 空间
    private String bucketName = "lanjian666";
    // 文件存储目录

    private OSSClient ossClient;

    public OSSClientUtil_user() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }
    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        System.out.println(fileUrl);
        if (!StringUtils.isEmpty(fileUrl)) {
            /*String[] split = fileUrl.split("/");*/
            return this.getUrl(fileUrl);
        }
        return null;
    }
    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }
}