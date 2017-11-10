package com.lovecws.mumu.mahout.recommender;

import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 推荐算法测试
 * @date 2017-11-08 10:11
 */
public class UserBasedRecommenderTest {

    private UserBasedRecommender userBasedRecommender = new UserBasedRecommender();

    @Test
    public void euclideanDistanceSimilarity() throws IOException, TasteException {
        //userBasedRecommender.recommender("/datafile/item.csv", "euclideanDistance", null, 2, 3);
        userBasedRecommender.recommender("/grouplens/ratings.csv", "euclideanDistance", null, 2, 3);
    }

    @Test
    public void spearmanCorrelationSimilarity() throws IOException, TasteException {
        userBasedRecommender.recommender("/datafile/item.csv", "pearson", null, 2, 3);
    }

    @Test
    public void pearsonCorrelationSimilarity() throws IOException, TasteException {
        userBasedRecommender.recommender("/datafile/item.csv", "spearman", null, 2, 3);
    }

    @Test
    public void logLikelihood() throws IOException, TasteException {
        userBasedRecommender.recommender("/datafile/item.csv", "logLikelihood", null, 2, 3);
    }

    @Test
    public void thresholdUser() throws IOException, TasteException {
        userBasedRecommender.recommender("/grouplens/ratings.csv", "euclideanDistance", "thresholdUser", 0.7, 3);
    }
}
