package com.lovecws.mumu.mahout;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 推荐评估
 * @date 2017-11-07 14:38
 */
public class MahoutConfiguration {

    public static final String HADOOP_ADDRESS = "hdfs://192.168.11.25:9000";

    public static String address() {
        String hadoop_address = System.getenv("HADOOP_ADDRESS");
        if (hadoop_address == null) {
            hadoop_address = HADOOP_ADDRESS;
        }
        return hadoop_address;
    }
}
