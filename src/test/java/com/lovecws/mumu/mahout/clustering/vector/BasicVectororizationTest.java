package com.lovecws.mumu.mahout.clustering.vector;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基本向量特征化
 * @date 2018-01-22 14:45
 */
public class BasicVectororizationTest {

    @Test
    public void vectororization(){
        new BasicVectororization().vectororization(MahoutConfiguration.address() + "/mumu/mahout/clustering/vector/named");
    }
}
