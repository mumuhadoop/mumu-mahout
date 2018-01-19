package com.lovecws.mumu.mahout.recommender.similarity;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: TODO
 * @date 2018-01-19 16:48
 */
public class SpearmanCorrelationSimilarityDemoTest {
    private SpearmanCorrelationSimilarityDemo spearmanCorrelationSimilarityDemo = new SpearmanCorrelationSimilarityDemo();
    String itemmodelsPath = MahoutConfiguration.class.getResource("/similarity/itemmodels.csv").getPath();
    String ratingsPath = MahoutConfiguration.class.getResource("/grouplens/ratings.csv").getPath();

    @Test
    public void userBasedPearson() throws IOException, TasteException {
        spearmanCorrelationSimilarityDemo.userBased(ratingsPath);
    }
}
