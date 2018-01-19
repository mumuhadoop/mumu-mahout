package com.lovecws.mumu.mahout.recommender.similarity;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 欧式距离相似度算法
 * @date 2018-01-19 15:23
 */
public class EuclideanDistanceSimilarityDemo extends AbstractSimilrityDemo {

    /**
     * 基于用户的欧式距离相似度算法
     *
     * @param dataFile
     * @param weighting
     */
    public void userBased(String dataFile, boolean weighting) throws IOException, TasteException {
        //构建datamodel
        DataModel dataModel = new FileDataModel(new File(dataFile));
        UserSimilarity userSimilarity = null;
        if (weighting) {
            userSimilarity = new EuclideanDistanceSimilarity(dataModel, Weighting.WEIGHTED);
        } else {
            userSimilarity = new EuclideanDistanceSimilarity(dataModel);
        }
        userBased(dataModel, userSimilarity, 5);
    }

    /**
     * 基于物品的欧式距离相似度算法
     *
     * @param dataFile
     * @param weighting
     */
    public void itemBased(String dataFile, boolean weighting) throws IOException, TasteException {
        //构建datamodel
        DataModel dataModel = new FileDataModel(new File(dataFile));
        ItemSimilarity itemSimilarity = null;
        if (weighting) {
            itemSimilarity = new EuclideanDistanceSimilarity(dataModel, Weighting.WEIGHTED);
        } else {
            itemSimilarity = new EuclideanDistanceSimilarity(dataModel);
        }
        itemBased(dataModel, itemSimilarity, 5);
    }
}
