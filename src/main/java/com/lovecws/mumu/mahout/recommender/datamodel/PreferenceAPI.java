package com.lovecws.mumu.mahout.recommender.datamodel;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveArrayIterator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericItemPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: mahout偏好数据
 * @date 2017-11-07 16:09
 */
public class PreferenceAPI {

    public void dataModel() throws TasteException {
        PreferenceArray preferences = new GenericUserPreferenceArray(3);
        preferences.setUserID(0, 1l);
        preferences.setItemID(0, 101);
        preferences.setValue(0, 5.0f);
        preferences.setItemID(1, 102);
        preferences.setValue(1, 3.0f);
        preferences.setItemID(2, 103);
        preferences.setValue(2, 2.5f);

        PreferenceArray preferences2 = new GenericUserPreferenceArray(4);
        preferences2.setUserID(0, 2l);
        preferences2.setItemID(0, 101l);
        preferences2.setValue(0, 2.0f);
        preferences2.setItemID(1, 102l);
        preferences2.setValue(1, 2.5f);
        preferences2.setItemID(2, 103l);
        preferences2.setValue(2, 5.0f);
        preferences2.setItemID(3, 104l);
        preferences2.setValue(3, 2.0f);

        PreferenceArray preferences3 = new GenericUserPreferenceArray(2);
        preferences3.setUserID(0, 3l);
        preferences3.setItemID(0, 101l);
        preferences3.setValue(0, 2.5f);
        preferences3.setItemID(1, 104l);
        preferences3.setValue(1, 4.0f);

        PreferenceArray preferences4 = new GenericUserPreferenceArray(4);
        preferences4.setUserID(0, 4l);
        preferences4.setItemID(0, 101l);
        preferences4.setValue(0, 5.0f);
        preferences4.setItemID(1, 103l);
        preferences4.setValue(1, 3.0f);
        preferences4.setItemID(2, 104l);
        preferences4.setValue(2, 4.5f);
        preferences4.setItemID(3, 106l);
        preferences4.setValue(3, 4.0f);

        PreferenceArray preferences5 = new GenericUserPreferenceArray(6);
        preferences5.setUserID(0, 5l);
        preferences5.setItemID(0, 101l);
        preferences5.setValue(0, 4.0f);
        preferences5.setItemID(1, 102l);
        preferences5.setValue(1, 3.0f);
        preferences5.setItemID(2, 103l);
        preferences5.setValue(2, 2.0f);
        preferences5.setItemID(3, 104l);
        preferences5.setValue(3, 4.0f);
        preferences5.setItemID(4, 105l);
        preferences5.setValue(4, 3.5f);
        preferences5.setItemID(5, 106l);
        preferences5.setValue(5, 4.0f);

        FastByIDMap<PreferenceArray> fastByIDMap = new FastByIDMap<PreferenceArray>();
        fastByIDMap.put(1l, preferences);
        fastByIDMap.put(2l, preferences2);
        fastByIDMap.put(3l, preferences3);
        fastByIDMap.put(4l, preferences4);
        fastByIDMap.put(5l, preferences5);
        DataModel dataModel = new GenericDataModel(fastByIDMap);

        UserSimilarity userSimilarity = new EuclideanDistanceSimilarity(dataModel);
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);

        LongPrimitiveIterator iter = dataModel.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = recommender.recommend(uid, 2);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem recommendedItem : list) {
                System.out.printf("(%s,%f)", recommendedItem.getItemID(), recommendedItem.getValue());
            }
            System.out.println();
        }
    }

    /**
     * 无偏好型 数据模型
     */
    public void booleanPrefDataModel() throws TasteException {
        FastIDSet fastIDSet = new FastIDSet();
        fastIDSet.add(102l);
        fastIDSet.add(103l);
        fastIDSet.add(104l);
        fastIDSet.add(105l);

        FastIDSet fastIDSet2 = new FastIDSet();
        fastIDSet2.add(102l);
        fastIDSet2.add(103l);

        FastIDSet fastIDSet3 = new FastIDSet();
        fastIDSet3.add(103l);
        fastIDSet3.add(104l);
        fastIDSet3.add(105l);
        fastIDSet3.add(106l);

        FastByIDMap<FastIDSet> fastByIDMap = new FastByIDMap<FastIDSet>();
        fastByIDMap.put(1l, fastIDSet);
        fastByIDMap.put(2l, fastIDSet2);
        fastByIDMap.put(3l, fastIDSet3);

        GenericBooleanPrefDataModel genericBooleanPrefDataModel = new GenericBooleanPrefDataModel(fastByIDMap);

        UserSimilarity userSimilarity = new LogLikelihoodSimilarity(genericBooleanPrefDataModel);

        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, genericBooleanPrefDataModel);
        Recommender recommender = new GenericBooleanPrefUserBasedRecommender(genericBooleanPrefDataModel, userNeighborhood, userSimilarity);
        LongPrimitiveArrayIterator userIDs = genericBooleanPrefDataModel.getUserIDs();
        while (userIDs.hasNext()) {
            Long userId = userIDs.next();
            List<RecommendedItem> recommends = recommender.recommend(userId, 2);
            System.out.printf("uid:%s", userId);
            for (RecommendedItem recommendedItem : recommends) {
                System.out.printf("(%s,%f)", recommendedItem.getItemID(), recommendedItem.getValue());
            }
            System.out.println();
        }

    }

    /**
     * 基于物品推荐
     *
     * @throws TasteException
     */
    public void itemPreferenceArray() throws TasteException {
        PreferenceArray preferences = new GenericItemPreferenceArray(3);
        preferences.setUserID(0, 1l);
        preferences.setItemID(0, 101);
        preferences.setValue(0, 5.0f);
        preferences.setItemID(1, 102);
        preferences.setValue(1, 3.0f);
        preferences.setItemID(2, 103);
        preferences.setValue(2, 2.5f);

        PreferenceArray preferences2 = new GenericItemPreferenceArray(4);
        preferences2.setUserID(0, 2l);
        preferences2.setItemID(0, 101l);
        preferences2.setValue(0, 2.0f);
        preferences2.setItemID(1, 102l);
        preferences2.setValue(1, 2.5f);
        preferences2.setItemID(2, 103l);
        preferences2.setValue(2, 5.0f);
        preferences2.setItemID(3, 104l);
        preferences2.setValue(3, 2.0f);

        PreferenceArray preferences3 = new GenericItemPreferenceArray(2);
        preferences3.setUserID(0, 3l);
        preferences3.setItemID(0, 101l);
        preferences3.setValue(0, 2.5f);
        preferences3.setItemID(1, 104l);
        preferences3.setValue(1, 4.0f);

        PreferenceArray preferences4 = new GenericItemPreferenceArray(4);
        preferences4.setUserID(0, 4l);
        preferences4.setItemID(0, 101l);
        preferences4.setValue(0, 5.0f);
        preferences4.setItemID(1, 103l);
        preferences4.setValue(1, 3.0f);
        preferences4.setItemID(2, 104l);
        preferences4.setValue(2, 4.5f);
        preferences4.setItemID(3, 106l);
        preferences4.setValue(3, 4.0f);

        PreferenceArray preferences5 = new GenericItemPreferenceArray(6);
        preferences5.setUserID(0, 5l);
        preferences5.setItemID(0, 101l);
        preferences5.setValue(0, 4.0f);
        preferences5.setItemID(1, 102l);
        preferences5.setValue(1, 3.0f);
        preferences5.setItemID(2, 103l);
        preferences5.setValue(2, 2.0f);
        preferences5.setItemID(3, 104l);
        preferences5.setValue(3, 4.0f);
        preferences5.setItemID(4, 105l);
        preferences5.setValue(4, 3.5f);
        preferences5.setItemID(5, 106l);
        preferences5.setValue(5, 4.0f);

        FastByIDMap<PreferenceArray> fastByIDMap = new FastByIDMap<PreferenceArray>();
        fastByIDMap.put(1l, preferences);
        fastByIDMap.put(2l, preferences2);
        fastByIDMap.put(3l, preferences3);
        fastByIDMap.put(4l, preferences4);
        fastByIDMap.put(5l, preferences5);
        DataModel dataModel = new GenericDataModel(fastByIDMap);

        ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity(dataModel);
        Recommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);

        LongPrimitiveIterator iter = dataModel.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = recommender.recommend(uid, 2);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem recommendedItem : list) {
                System.out.printf("(%s,%f)", recommendedItem.getItemID(), recommendedItem.getValue());
            }
            System.out.println();
        }
    }

}
