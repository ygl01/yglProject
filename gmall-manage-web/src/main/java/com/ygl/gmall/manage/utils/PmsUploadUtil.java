package com.ygl.gmall.manage.utils;

import com.ygl.gmall.manage.GmallManageWebApplication;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.ygl.gmall.manage.utils.ConstantInterface.URL;

/**
 * @author ygl
 * @description 上传工具类
 * @date 2021/1/5 14:38
 */
public class PmsUploadUtil {
    /**
     * @author ygl
     * 工具类上传图片方法
     * @date 2021-01-05 14:41
     */
    public static String uploadImage(MultipartFile multipartFile) throws IOException, MyException {

        //配置fdfs的全局连接地址
        String tracker = PmsUploadUtil.class.getResource("/tracker.conf").getPath();//获得配置文件路径

        ClientGlobal.init(tracker);
        TrackerClient trackerClient = new TrackerClient();

        //获得一个trackerServer的实例
        //注意1.27和1.29版本不一样
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        //通过tracker获得一个Storage连接客户端
        StorageClient storageClient = new StorageClient(trackerServer, null);

        //获得上传的二进制对象
        byte[] bytes = multipartFile.getBytes();
        String filename = multipartFile.getOriginalFilename();//获得上传的对象名称 a.jpg
        System.out.println("文件名：" + filename);
        //获取最后一个 点 的位置
        int i = filename.lastIndexOf(".");
        //从点的之后以为开始截取 获得后缀名
        String extName = filename.substring(i + 1);
        System.out.println("文件后缀名：" + extName);
        //local_filename:文件绝对路径和名字；file_ext_name:文件后缀名；meta_list：文件原生信息
        String[] uploadInfos = storageClient.upload_file(bytes, extName, null);
        String url = URL;
        System.out.println("URL："+url);
        for (String uploadInfo : uploadInfos) {
            url += "/" + uploadInfo;
        }
        System.out.println("地址：" + url);
        return url;
    }

}
