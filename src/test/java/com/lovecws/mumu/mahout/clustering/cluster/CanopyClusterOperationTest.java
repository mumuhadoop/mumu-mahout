package com.lovecws.mumu.mahout.clustering.cluster;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: canopy 聚类测试
 * @date 2018-01-26 11:42
 */
public class CanopyClusterOperationTest {

    private CanopyClusterOperation canopyClusterDemo = new CanopyClusterOperation();

    @Test
    public void createCanopy() {
        canopyClusterDemo.createCanopy();
    }

    @Test
    public void canopyCluster() {
        String baseDir = MahoutConfiguration.address() + "/mumu/mahout/clustering/cluster" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        canopyClusterDemo.canopyCluster(baseDir);
    }
}
