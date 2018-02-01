package com.lovecws.mumu.mahout.clustering.cluster;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import com.lovecws.mumu.mahout.clustering.util.ClusteringUtil;
import com.lovecws.mumu.mahout.clustering.util.SequenceFileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.math.Vector;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: kmeans聚类算法
 * @date 2018-01-22 15:43
 */
public class KMenasClusterOperation {

    public void kmeansDriver(String baseDir, int k, double[]... points) {
        //根据二维数组生成向量
        List<Vector> vectors = ClusteringUtil.getPoints(points);
        FileSystem fileSystem = MahoutConfiguration.distributedFileSystem();
        try {
            Configuration configuration = fileSystem.getConf();

            //将向量添加到sequencefile文件中
            SequenceFileUtil.writePointToSequenceFile(vectors, baseDir + "/points/file1");

            //初始化簇
            ClusteringUtil.initClustering(vectors, baseDir + "/clusters/part-00000", k);
            KMeansDriver.run(configuration,
                    new Path(baseDir + "/points"),
                    new Path(baseDir + "/clusters"),
                    new Path(baseDir + "/output"),
                    0.001,
                    10,
                    true,
                    0.0,
                    true);
            //读取sequencefile文件
            SequenceFileUtil.readeSequenceFile(baseDir + "/output", "SequenceFile");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MahoutConfiguration.closeFileSystem();
        }
    }
}
