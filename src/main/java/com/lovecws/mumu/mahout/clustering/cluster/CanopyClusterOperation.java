package com.lovecws.mumu.mahout.clustering.cluster;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import com.lovecws.mumu.mahout.clustering.util.SequenceFileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.UncommonDistributions;
import org.apache.mahout.clustering.canopy.Canopy;
import org.apache.mahout.clustering.canopy.CanopyClusterer;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: canopy 聚类算法
 * @date 2018-01-26 11:13
 */
public class CanopyClusterOperation {

    public void createCanopy() {
        //填充向量
        List<Vector> vectors = new ArrayList<Vector>();
        for (int i = 0; i < 50000; i++) {
            vectors.add(new DenseVector(new double[]{
                    UncommonDistributions.rNorm(1, 3),
                    UncommonDistributions.rNorm(1, 3),
                    UncommonDistributions.rNorm(1, 3)
            }));
        }
        List<Canopy> canopies = CanopyClusterer.createCanopies(vectors, new EuclideanDistanceMeasure(), 3.0, 1.5);
        for (Canopy canopy : canopies) {
            System.out.println("canopy id : " + canopy.getId() + ", center : " + canopy.getCenter().asFormatString());
        }
    }

    public void canopyCluster(String baseDir) {
        //填充向量
        List<Vector> vectors = new ArrayList<Vector>();
        for (int i = 0; i < 100; i++) {
            vectors.add(new DenseVector(new double[]{
                    UncommonDistributions.rNorm(1, 3),
                    UncommonDistributions.rNorm(1, 3)
            }));
        }
        SequenceFileUtil.writePointToSequenceFile(vectors, baseDir + "/points/file1");
        FileSystem fileSystem = MahoutConfiguration.distributedFileSystem();
        Configuration conf = fileSystem.getConf();
        try {
            CanopyDriver.run(conf,
                    new Path(baseDir + "/points"),
                    new Path(baseDir + "/output"),
                    new EuclideanDistanceMeasure(),
                    3.5,
                    3.0,
                    true,
                    0.0,
                    true);
            SequenceFileUtil.readeSequenceFile(baseDir + "/output", "SequenceFile");
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            MahoutConfiguration.closeFileSystem();
        }
    }
}
