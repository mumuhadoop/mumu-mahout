package com.lovecws.mumu.mahout.recommender.userbased;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 推荐引擎
 * @date 2017-11-08 10:10
 */
public class UserBasedRecommender {

    /**
     * 推荐算法
     *
     * @param datafile        数据集
     * @param similarity      相似度算法
     * @param neighbor        附近算法
     * @param neighborhoodNum
     * @param recommenderNum
     * @throws IOException
     * @throws TasteException
     */
    public void recommender(String datafile, String similarity, String neighbor, double neighborhoodNum, int recommenderNum) throws IOException, TasteException {
        DataModel model = new FileDataModel(new File(UserBasedRecommender.class.getResource(datafile).getPath()));
        //设置相关度
        UserSimilarity userSimilarity = null;
        switch (similarity) {
            case "euclideanDistance":
                userSimilarity = new EuclideanDistanceSimilarity(model);
                break;
            case "pearson":
                userSimilarity = new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);
                break;
            case "spearman":
                userSimilarity = new SpearmanCorrelationSimilarity(model);
                break;
            case "cachingUser":
                userSimilarity = new CachingUserSimilarity(new SpearmanCorrelationSimilarity(model), model);
                break;
            //忽略偏好值 基于对数似然比计算相似度
            case "logLikelihood":
                //将有偏好的数据 转换成无偏好的数据
                FastByIDMap<FastIDSet> fastByIDMap = new FastByIDMap<FastIDSet>();
                LongPrimitiveIterator userIDs = model.getUserIDs();
                while (userIDs.hasNext()) {
                    long userId = userIDs.nextLong();
                    fastByIDMap.put(userId, model.getItemIDsFromUser(userId));
                }
                model = new GenericBooleanPrefDataModel(fastByIDMap);
                //model = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(model));
                userSimilarity = new LogLikelihoodSimilarity(model);
                break;
            //忽略偏好值 基于谷本系数计算相似度
            case "tanimotoCoefficient":
                model = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(model));
                userSimilarity=new TanimotoCoefficientSimilarity(model);
                break;
            default:
                userSimilarity = new EuclideanDistanceSimilarity(model);
                break;
        }
        //设置领域
        UserNeighborhood userNeighborhood = null;
        if (neighbor == null) neighbor = "";
        switch (neighbor) {
            case "nearestNUser":
                //获取最近的前几个领域
                userNeighborhood = new NearestNUserNeighborhood((int) neighborhoodNum, userSimilarity, model);
                break;
            case "thresholdUser":
                //设定一个阈值 阈值位于 -1 1 越接近1 相关度越高
                userNeighborhood = new ThresholdUserNeighborhood(0.7, userSimilarity, model);
                break;
            default:
                userNeighborhood = new NearestNUserNeighborhood((int) neighborhoodNum, userSimilarity, model);
                break;
        }
        Recommender recommender = new GenericUserBasedRecommender(model, userNeighborhood, userSimilarity);
        LongPrimitiveIterator iter = model.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = recommender.recommend(uid, recommenderNum);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem recommendedItem : list) {
                System.out.printf("(%s,%f)", recommendedItem.getItemID(), recommendedItem.getValue());
            }
            System.out.println();
        }
    }
}