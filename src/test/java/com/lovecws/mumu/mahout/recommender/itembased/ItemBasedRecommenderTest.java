package com.lovecws.mumu.mahout.recommender.itembased;

import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基于物品推荐
 * @date 2017-11-08 17:33
 */
public class ItemBasedRecommenderTest {

    private ItemBasedRecommender itemBasedRecommender = new ItemBasedRecommender();

    private static final String datafile = ItemBasedRecommenderTest.class.getResource("/grouplens/ratings.csv").getPath();

    @Test
    public void euclideanDistance() throws IOException, TasteException {
        //itemBasedRecommender.recommender(ItemBasedRecommenderTest.class.getResource("/datafile/itembased.csv").getPath(), null);
        itemBasedRecommender.recommender(datafile, "euclideanDistance", "itemBased");
    }

    @Test
    public void pearson() throws IOException, TasteException {
        //itemBasedRecommender.recommender(ItemBasedRecommenderTest.class.getResource("/datafile/itembased.csv").getPath(), null);
        itemBasedRecommender.recommender(datafile, "pearson", "itemBased");
    }

    @Test
    public void uncenteredCosine() throws IOException, TasteException {
        //itemBasedRecommender.recommender(ItemBasedRecommenderTest.class.getResource("/datafile/itembased.csv").getPath(), null);
        itemBasedRecommender.recommender(datafile, "uncenteredCosine", "itemBased");
    }

    @Test
    public void logLikelihood() throws IOException, TasteException {
        //itemBasedRecommender.recommender(ItemBasedRecommenderTest.class.getResource("/datafile/itembased.csv").getPath(), null);
        itemBasedRecommender.recommender(datafile, "logLikelihood", "booleanPrefItem");
    }

    @Test
    public void tanimotoCoefficient() throws IOException, TasteException {
        //itemBasedRecommender.recommender(ItemBasedRecommenderTest.class.getResource("/datafile/itembased.csv").getPath(), null);
        itemBasedRecommender.recommender(datafile, "tanimotoCoefficient", "booleanPrefItem");
    }

    @Test
    public void cachingItem() throws IOException, TasteException {
        //itemBasedRecommender.recommender(ItemBasedRecommenderTest.class.getResource("/datafile/itembased.csv").getPath(), null);
        itemBasedRecommender.recommender(datafile, "cachingItem", "itemBased");
    }
}
