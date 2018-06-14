package com.lovecws.mumu.mahout;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 推荐评估
 * @date 2017-11-07 14:38
 */
public class MahoutConfiguration {

    public static final String HADOOP_ADDRESS = "hdfs://192.168.11.25:9000";
    public static DistributedFileSystem distributedFileSystem = null;
    private static final Logger log = Logger.getLogger(MahoutConfiguration.class);

    /**
     * 获取hadoop的环境变量
     *
     * @return
     */
    public static String address() {
        String hadoop_address = System.getenv("HADOOP_ADDRESS");
        if (hadoop_address == null) {
            hadoop_address = HADOOP_ADDRESS;
        }
        return hadoop_address;
    }

    /**
     * 获取到分布式文件
     *
     * @return
     */
    public static DistributedFileSystem distributedFileSystem() {
        String hadoop_address = address();
        if (distributedFileSystem != null) {
            return distributedFileSystem;
        }
        distributedFileSystem = new DistributedFileSystem();
        Configuration conf = new Configuration();
        try {
            distributedFileSystem.initialize(new URI(hadoop_address), conf);
        } catch (IOException | URISyntaxException e) {
            log.error(e);
        }
        return distributedFileSystem;
    }

    /**
     * 关闭分布式文件
     */
    public static void closeFileSystem() {
        if (distributedFileSystem != null) {
            try {
                distributedFileSystem.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}
