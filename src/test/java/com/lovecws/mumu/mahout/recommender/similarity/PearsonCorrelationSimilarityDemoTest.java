package com.lovecws.mumu.mahout.recommender.similarity;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 皮尔孙相关系数相似度测试
 * @date 2018-01-19 14:24
 */
public class PearsonCorrelationSimilarityDemoTest {

    private PearsonCorrelationSimilarityDemo pearsonCorrelationSimilarityDemo = new PearsonCorrelationSimilarityDemo();
    String itemmodelsPath = MahoutConfiguration.class.getResource("/similarity/itemmodels.csv").getPath();
    String ratingsPath = MahoutConfiguration.class.getResource("/grouplens/ratings.csv").getPath();

    @Test
    public void userBasedPearson() throws IOException, TasteException {
        pearsonCorrelationSimilarityDemo.userBasedPearson(ratingsPath, false);
    }

    @Test
    public void userBasedPearsonWithWeight() throws IOException, TasteException {
        pearsonCorrelationSimilarityDemo.userBasedPearson(ratingsPath, true);
    }

    @Test
    public void itemBasedPearson() throws IOException, TasteException {
        pearsonCorrelationSimilarityDemo.itemBasedPearson(itemmodelsPath, false);
    }

    @Test
    public void itemBasedPearsonWithWeight() throws IOException, TasteException {
        pearsonCorrelationSimilarityDemo.itemBasedPearson(itemmodelsPath, true);
    }
}
