package com.lovecws.mumu.mahout.clustering;


import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class KmeansClustering {

    private static final double[][] points = new double[][]{{1, 1}, {2, 1}, {1, 2}, {2.2}, {3, 3}, {8, 8}, {9, 8}, {8, 9}, {9, 9}};

    /**
     * 将二维数组信息写入到sequenceFile文件中
     *
     * @param vectors
     * @param fileName
     * @param fs
     * @param conf
     * @throws IOException
     */
    public static void writePointToFile(List<Vector> vectors, String fileName, FileSystem fs, Configuration conf) throws IOException {
        Path path = new Path(fileName);
        if (fs.exists(path)) {
            fs.delete(path, false);
        }
        SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, new Path(fileName), LongWritable.class, VectorWritable.class);
        VectorWritable vectorWritable = new VectorWritable();
        long count = 0;
        for (Vector vector : vectors) {
            vectorWritable.set(vector);
            writer.append(new LongWritable(count++), vectorWritable);
        }
        writer.close();
    }

    /**
     * 初始化簇
     *
     * @param vectors
     * @param fileName
     * @param fs
     * @param conf
     * @throws IOException
     */
    public static void writeCluster(List<Vector> vectors, String fileName, FileSystem fs, Configuration conf) throws IOException {
        Path path = new Path(fileName);
        if (fs.exists(path)) {
            fs.delete(path, false);
        }
        SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, new Path(fileName), Text.class, Kluster.class);
        for (int i = 0; i < 2; i++) {
            Vector vector = vectors.get(i);
            Cluster cluster = new Kluster(vector, i, new SquaredEuclideanDistanceMeasure());
            writer.append(new Text(cluster.toString()), cluster);
        }
        writer.close();
    }

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
     * 读取sequenceFile文件
     *
     * @param fileName
     * @param fs
     * @param conf
     * @throws IOException
     */
    public static void readeCluster(String fileName, FileSystem fs, Configuration conf) throws IOException {
        SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path(fileName), conf);
        IntWritable intWritable = new IntWritable();
        WeightedVectorWritable vectorWritable = new WeightedVectorWritable();
        while (reader.next(intWritable, vectorWritable)) {
            System.out.println(intWritable + "\t" + vectorWritable);
        }
        reader.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        if (args.length != 1) {
            System.out.println("useage: <base dir>");
            System.exit(1);
        }
        String filePath = args[0];
        //将二维数据信息写入到向量集合中
        List<Vector> vectors = getPoints(KmeansClustering.points);

        //将向量写入初始中心点sequenceFile文件中
        Configuration conf = new Configuration();
        FileSystem fs = new DistributedFileSystem();
        fs.initialize(new URI(MahoutConfiguration.HADOOP_ADDRESS), conf);
        writePointToFile(vectors, filePath + "/points/file1", fs, conf);
        writeCluster(vectors, filePath + "/clusters/part-00000", fs, conf);

        Path outputPath = new Path(filePath + "/output");
        if (fs.exists(outputPath)) {
            fs.delete(outputPath, true);
        }
        KMeansDriver.run(conf,
                new Path(filePath + "/points"),
                new Path(filePath + "/clusters"),
                new Path(filePath + "/output"),
                0.1,
                2,
                false,
                0.1,
                false);

        readeCluster(filePath + "/output/" + Cluster.CLUSTERED_POINTS_DIR + "/part-m-00000", fs, conf);
    }
}