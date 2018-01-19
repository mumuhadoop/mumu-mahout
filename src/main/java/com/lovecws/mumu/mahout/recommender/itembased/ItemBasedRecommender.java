package com.lovecws.mumu.mahout.recommender.itembased;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.ParallelSGDFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基于物品的推荐
 * @date 2017-11-08 17:06
 */
public class ItemBasedRecommender {

    /**
     * 基于物品的推荐
     *
     * @param datafile
     * @param similarity  物品相关度
     * @param recommender 推荐算法
     * @throws IOException
     * @throws TasteException
     */
    public void recommender(String datafile, String similarity, String recommender) throws IOException, TasteException {
        DataModel model = new FileDataModel(new File(datafile));
        //设置相关度
        ItemSimilarity itemSimilarity = null;
        if (similarity == null) similarity = "";
        switch (similarity) {
            case "euclideanDistance":
                itemSimilarity = new EuclideanDistanceSimilarity(model);
                break;
            case "pearson":
                itemSimilarity = new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);
                break;
            case "uncenteredCosine":
                itemSimilarity = new UncenteredCosineSimilarity(model);
                break;
            //忽略偏好值 基于对数似然比计算相似度
            case "logLikelihood":
                model = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(model));
                itemSimilarity = new LogLikelihoodSimilarity(model);
                break;
            //忽略偏好值 基于谷本系数计算相似度
            case "tanimotoCoefficient":
                model = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(model));
                itemSimilarity = new TanimotoCoefficientSimilarity(model);
                break;
            case "cachingItem":
                itemSimilarity = new CachingItemSimilarity(new PearsonCorrelationSimilarity(model), model);
                break;
            default:
                itemSimilarity = new EuclideanDistanceSimilarity(model);
                break;
        }
        Recommender itemBasedRecommender = null;
        if (recommender == null) recommender = "";
        switch (recommender) {
            case "itemBased":
                itemBasedRecommender = new GenericItemBasedRecommender(model, itemSimilarity);
                break;
            case "booleanPrefItem":
                itemBasedRecommender = new GenericBooleanPrefItemBasedRecommender(model, itemSimilarity);
                break;
            case "random":
                itemBasedRecommender = new RandomRecommender(model);
                break;
            case "itemUserAverage":
                itemBasedRecommender = new ItemUserAverageRecommender(model);
                break;
            case "itemAverage":
                itemBasedRecommender = new ItemAverageRecommender(model);
                break;
            case "SVD":
                Factorizer factorizer = new ParallelSGDFactorizer(model, 2, 0.7, 2);
                itemBasedRecommender = new SVDRecommender(model, factorizer);
                break;
            case "caching":
                itemBasedRecommender = new CachingRecommender(new GenericItemBasedRecommender(model, itemSimilarity));
                break;
            default:
                itemBasedRecommender = new GenericItemBasedRecommender(model, itemSimilarity);
                break;
        }
        LongPrimitiveIterator iter = model.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = itemBasedRecommender.recommend(uid, 2);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem recommendedItem : list) {
                System.out.printf("(%s,%f)", recommendedItem.getItemID(), recommendedItem.getValue());
            }
            System.out.println();
        }
    }
}
