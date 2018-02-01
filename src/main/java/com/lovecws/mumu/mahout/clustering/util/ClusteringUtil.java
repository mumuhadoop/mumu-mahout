package com.lovecws.mumu.mahout.clustering.util;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 聚类工具类
 * @date 2018-01-22 12:02
 */
public class ClusteringUtil {

    /**
     * 获取到集合
     *
     * @param raw
     * @return
     */
    public static List<Vector> getPoints(double[][] raw) {
        List<Vector> vectors = new ArrayList<Vector>();
        for (int i = 0; i < raw.length; i++) {
            double[] fr = raw[i];
            Vector vector = new RandomAccessSparseVector(fr.length);
            vector.assign(fr);
            vectors.add(vector);
        }
        return vectors;
    }


    /**
     * 初始化簇 选择中心点
     *
     * @param vectors
     * @param fileName
     * @param k
     * @throws IOException
     */
    public static void initClustering(List<Vector> vectors, String fileName, int k) throws IOException {
        FileSystem fs = MahoutConfiguration.distributedFileSystem();
        try {
            Path path = new Path(fileName);
            if (fs.exists(path)) {
                fs.delete(path, true);
            }
            SequenceFile.Writer writer = new SequenceFile.Writer(fs, fs.getConf(), path, Text.class, Kluster.class);
            for (int i = 0; i < k; i++) {
                Vector vector = vectors.get(i);
                Kluster cluster = new Kluster(vector, i, new EuclideanDistanceMeasure());
                writer.append(new Text(cluster.getIdentifier()), cluster);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
