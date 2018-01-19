package com.lovecws.mumu.mahout.recommender.similarity;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.IOException;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 相似度
 * @date 2018-01-19 15:01
 */
public class AbstractSimilrityDemo {

    private static final Logger log = Logger.getLogger(PearsonCorrelationSimilarityDemo.class);

    /**
     * 基于用户的相似度算法
     *
     * @param dataModel
     * @param userSimilarity
     * @param recommendCount
     */
    public void userBased(DataModel dataModel, UserSimilarity userSimilarity, int recommendCount) throws IOException, TasteException {
        userBasedBooleanPref(dataModel, userSimilarity, recommendCount, false);
    }

    /**
     * 基于用户的相似度算法
     *
     * @param dataModel
     * @param userSimilarity
     * @param recommendCount
     * @param booleanPref
     */
    public void userBasedBooleanPref(DataModel dataModel, UserSimilarity userSimilarity, int recommendCount, boolean booleanPref) throws IOException, TasteException {
        try {
            //创建用户领域
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(recommendCount, userSimilarity, dataModel);
            Recommender recommender = null;
            if (booleanPref) {
                recommender = new GenericBooleanPrefUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
            } else {
                recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
            }
            LongPrimitiveIterator userIDs = dataModel.getUserIDs();
            while (userIDs.hasNext()) {
                long userId = userIDs.nextLong();
                List<RecommendedItem> recommendedItems = recommender.recommend(userId, recommendCount);
                System.out.printf("uid:%s", userId);
                for (RecommendedItem recommendedItem : recommendedItems) {
                    System.out.printf("(%s,%f)", recommendedItem.getItemID(), recommendedItem.getValue());
                }
                System.out.println();
            }
        } catch (TasteException e) {
            log.error(e);
        }
    }


    /**
     * 基于物品的相似度算法
     *
     * @param dataModel
     * @param itemSimilarity
     * @param recommendCount
     */
    public void itemBased(DataModel dataModel, ItemSimilarity itemSimilarity, int recommendCount) throws IOException, TasteException {
        itemBasedBooleanPref(dataModel, itemSimilarity, recommendCount, false);
    }

    /**
     * 基于物品的相似度算法
     *
     * @param dataModel
     * @param itemSimilarity
     * @param recommendCount
     * @param booleanPref
     */
    public void itemBasedBooleanPref(DataModel dataModel, ItemSimilarity itemSimilarity, int recommendCount, boolean booleanPref) {
        try {
            //创建权重的皮尔孙相似度
            Recommender recommender = null;
            if (booleanPref) {
                recommender = new GenericBooleanPrefItemBasedRecommender(dataModel, itemSimilarity);
            } else {
                recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
            }
            LongPrimitiveIterator userIDs = dataModel.getUserIDs();
            while (userIDs.hasNext()) {
                long userId = userIDs.nextLong();
                List<RecommendedItem> recommendedItems = recommender.recommend(userId, recommendCount);
                System.out.printf("uid:%s", userId);
                for (RecommendedItem recommendedItem : recommendedItems) {
                    System.out.printf("(%s,%f)", recommendedItem.getItemID(), recommendedItem.getValue());
                }
                System.out.println();
            }
        } catch (TasteException e) {
            log.error(e);
        }
    }
}
