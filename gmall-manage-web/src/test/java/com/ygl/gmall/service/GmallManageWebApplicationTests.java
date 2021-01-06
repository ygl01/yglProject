package com.ygl.gmall.service;


import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.ygl.gmall.manage.utils.ConstantInterface.URL;

@SpringBootTest
public class GmallManageWebApplicationTests {

    @Test
    public void contextLoads() throws IOException, MyException {
        //配置fdfs的全局连接地址
        String tracker = GmallManageWebApplicationTests.class.getResource("/tracker.conf").getPath();//获得配置文件路径

        ClientGlobal.init(tracker);
        TrackerClient trackerClient = new TrackerClient();

        //获得一个trackerServer的实例
        //注意1.27和1.29版本不一样
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        //通过tracker获得一个Storage连接客户端
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //
        //local_filename:文件绝对路径和名字；file_ext_name:文件后缀名；meta_list：文件原生信息
        String[] uploadInfos = storageClient.upload_file("E:\\github\\gmall\\photo\\b.jpg", "jpg", null);
        String url = URL;
        System.out.println("地址1：" + url);
        for (String uploadInfo : uploadInfos) {
            url += "/" + uploadInfo;
            System.out.println(uploadInfo);
        }
        System.out.println("地址：" + url);
    }

}
