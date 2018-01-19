package com.lovecws.mumu.mahout.recommender.similarity;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 欧式距离相似度算法测试
 * @date 2018-01-19 15:26
 */
public class EuclideanDistanceSimilarityDemoTest {
    private EuclideanDistanceSimilarityDemo euclideanDistanceSimilarityDemo = new EuclideanDistanceSimilarityDemo();
    String itemmodelsPath = MahoutConfiguration.class.getResource("/similarity/itemmodels.csv").getPath();
    String ratingsPath = MahoutConfiguration.class.getResource("/grouplens/ratings.csv").getPath();

    @Test
    public void userBased() throws IOException, TasteException {
        euclideanDistanceSimilarityDemo.userBased(ratingsPath, false);
    }

    @Test
    public void userBasedWithWeight() throws IOException, TasteException {
        euclideanDistanceSimilarityDemo.userBased(ratingsPath, true);
    }

    @Test
    public void itemBased() throws IOException, TasteException {
        euclideanDistanceSimilarityDemo.itemBased(itemmodelsPath, false);
    }

    @Test
    public void itemBasedWithWeight() throws IOException, TasteException {
        euclideanDistanceSimilarityDemo.itemBased(itemmodelsPath, true);
    }
}
