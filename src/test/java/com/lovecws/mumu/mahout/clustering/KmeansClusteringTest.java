package com.lovecws.mumu.mahout.clustering;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: kmens聚类测试
 * @date 2017-11-13 14:07
 */
public class KmeansClusteringTest {

    @Test
    public void kmens() throws ClassNotFoundException, URISyntaxException, InterruptedException, IOException {
        KmeansClustering.main(new String[]{MahoutConfiguration.HADOOP_ADDRESS+"/mumu/mahout/clustering"});
    }
}
