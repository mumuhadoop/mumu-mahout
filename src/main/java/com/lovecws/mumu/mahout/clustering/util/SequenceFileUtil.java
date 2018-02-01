package com.lovecws.mumu.mahout.clustering.util;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.mahout.clustering.iterator.ClusterWritable;
import org.apache.mahout.clustering.iterator.ClusteringPolicyWritable;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: sequence工具类
 * @date 2018-01-22 11:56
 */
public class SequenceFileUtil {

    /**
     * 将二维数组信息写入到sequenceFile文件中
     *
     * @param vectors
     * @param fileName
     * @throws IOException
     */
    public static void writePointToSequenceFile(List<Vector> vectors, String fileName) {
        FileSystem fs = MahoutConfiguration.distributedFileSystem();
        try {
            Path path = new Path(fileName);
            if (fs.exists(path)) {
                fs.delete(path, true);
            }
            SequenceFile.Writer writer = new SequenceFile.Writer(fs, fs.getConf(), new Path(fileName), LongWritable.class, VectorWritable.class);
            VectorWritable vectorWritable = new VectorWritable();
            long count = 0;
            for (Vector vector : vectors) {
                vectorWritable.set(vector);
                writer.append(new LongWritable(count++), vectorWritable);
            }
            writer.sync();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取sequenceFile文件
     *
     * @param filePath
     * @throws IOException
     */
    public static void readeSequenceFile(String filePath, String fileType) {
        FileSystem fs = MahoutConfiguration.distributedFileSystem();
        try {
            FileSystem fileSystem = MahoutConfiguration.distributedFileSystem();
            FileStatus[] fileStatuses = fileSystem.listStatus(new Path(filePath));
            for (FileStatus fileStatus : fileStatuses) {
                System.out.println(fileStatus);
                if (fileStatus.isFile()) {
                    if (fileStatus.getLen() == 0) {
                        continue;
                    }
                    if ("SequenceFile".equalsIgnoreCase(fileType)) {
                        SequenceFile.Reader reader = new SequenceFile.Reader(fileSystem, fileStatus.getPath(), fileSystem.getConf());

                        Class<Writable> keyClass = (Class<Writable>) reader.getKeyClass();
                        Class<Writable> valueClass = (Class<Writable>) reader.getValueClass();
                        Writable keyWritable = keyClass.newInstance();
                        Writable valueWritable = valueClass.newInstance();
                        while (reader.next(keyWritable, valueWritable)) {
                            if (valueWritable instanceof ClusterWritable) {
                                ClusterWritable clusterWritable = (ClusterWritable) valueWritable;
                                System.out.println(keyWritable + "\t" + clusterWritable.getValue());
                            } else if (valueWritable instanceof VectorWritable) {
                                VectorWritable vectorWritable = (VectorWritable) valueWritable;
                                System.out.println(keyWritable + "\t" + vectorWritable.get());
                            } else if (valueWritable instanceof ClusteringPolicyWritable) {
                                ClusteringPolicyWritable clusteringPolicyWritable = (ClusteringPolicyWritable) valueWritable;
                                System.out.println(keyWritable + "\t" + clusteringPolicyWritable.getValue());
                            } else {
                                System.out.println(keyWritable + "\t" + valueWritable);
                            }
                            System.out.println();
                        }
                        reader.close();
                    } else {
                        FSDataInputStream fsDataInputStream = fileSystem.open(fileStatus.getPath());
                        byte[] bytes = new byte[fsDataInputStream.available()];
                        fsDataInputStream.read(bytes);
                        System.out.println(new String(bytes));
                        fsDataInputStream.close();
                    }
                } else {
                    readeSequenceFile(fileStatus.getPath().toString(), fileType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
