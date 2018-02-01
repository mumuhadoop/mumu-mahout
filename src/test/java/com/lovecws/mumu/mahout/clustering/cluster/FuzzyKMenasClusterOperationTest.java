package com.lovecws.mumu.mahout.clustering.cluster;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: fuzzykmeans聚类算法测试
 * @date 2018-01-31 14:08
 */
public class FuzzyKMenasClusterOperationTest {

    private static final double[][] points = new double[][]{{1, 1, 2}, {2, 1, 2}, {1, 2, 1}, {2, 2, 4}, {3, 3, 3}, {8, 8, 6}, {9, 8, 4}, {8, 9, 3}, {9, 9, 3}};

    @Test
    public void fuzzyKMeansDriver() {
        FuzzyKMenasClusterOperation fuzzyKMenasClusterOperation = new FuzzyKMenasClusterOperation();
        String baseDir = MahoutConfiguration.address() + "/mumu/mahout/clustering/fuzzykmeans" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        fuzzyKMenasClusterOperation.fuzzyKMeansDriver(baseDir, 3, points);
    }
}
