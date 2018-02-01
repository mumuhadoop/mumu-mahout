package com.lovecws.mumu.mahout.clustering.cluster;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: kmeans聚类算法测试
 * @date 2018-01-31 14:08
 */
public class KMenasClusterOperationTest {

    private static final double[][] points = new double[][]{{1, 1, 2}, {2, 1, 2}, {1, 2, 1}, {2, 2, 4}, {3, 3, 3}, {8, 8, 6}, {9, 8, 4}, {8, 9, 3}, {9, 9, 3}};

    @Test
    public void kmeansDriver() {
        KMenasClusterOperation kMenasClusterOperation = new KMenasClusterOperation();
        String baseDir = MahoutConfiguration.address() + "/mumu/mahout/clustering/kmeans" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        kMenasClusterOperation.kmeansDriver(baseDir, 3, points);
    }
}
