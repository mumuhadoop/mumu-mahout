package com.lovecws.mumu.mahout.recommender.similarity;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 对数似然比测试
 * @date 2018-01-19 16:43
 */
public class LogLikelihoodSimilarityDemoTest {

    private LogLikelihoodSimilarityDemo logLikelihoodSimilarityDemo = new LogLikelihoodSimilarityDemo();
    String itemmodelsPath = MahoutConfiguration.class.getResource("/similarity/itemmodels.csv").getPath();
    String ratingsPath = MahoutConfiguration.class.getResource("/grouplens/ratings.csv").getPath();

    @Test
    public void userBasedBooleanPref() throws IOException, TasteException {
        logLikelihoodSimilarityDemo.userBasedBooleanPref(itemmodelsPath);
    }

    @Test
    public void itemBasedBooleanPref() throws IOException, TasteException {
        logLikelihoodSimilarityDemo.itemBasedBooleanPref(itemmodelsPath);
    }
}
