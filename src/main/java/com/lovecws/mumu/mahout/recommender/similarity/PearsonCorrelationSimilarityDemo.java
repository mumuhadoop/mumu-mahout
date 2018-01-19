package com.lovecws.mumu.mahout.recommender.similarity;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 皮尔逊相关系数相似度
 * @date 2018-01-19 14:08
 * TODO 1.皮尔孙相关系数相似度可以用于基于用户的相似度和基于物品的相似度。2、皮尔逊相关系数相似度只能用作与有偏好值的数据。
 */
public class PearsonCorrelationSimilarityDemo extends AbstractSimilrityDemo {

    private static final Logger log = Logger.getLogger(PearsonCorrelationSimilarityDemo.class);

    /**
     * 基于用户的皮尔逊相似度算法
     *
     * @param dataFile
     * @param weighting
     */
    public void userBasedPearson(String dataFile, boolean weighting) throws IOException, TasteException {
        //构建datamodel
        DataModel dataModel = new FileDataModel(new File(dataFile));
        UserSimilarity userSimilarity = null;
        if (weighting) {
            userSimilarity = new PearsonCorrelationSimilarity(dataModel, Weighting.WEIGHTED);
        } else {
            userSimilarity = new PearsonCorrelationSimilarity(dataModel);
        }
        userBased(dataModel, userSimilarity, 5);
    }

    /**
     * 基于物品的皮尔逊相似度算法
     *
     * @param dataFile
     * @param weighting
     */
    public void itemBasedPearson(String dataFile, boolean weighting) throws IOException, TasteException {
        //构建datamodel
        DataModel dataModel = new FileDataModel(new File(dataFile));
        ItemSimilarity itemSimilarity = null;
        if (weighting) {
            itemSimilarity = new PearsonCorrelationSimilarity(dataModel, Weighting.WEIGHTED);
        } else {
            itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        }
        itemBased(dataModel, itemSimilarity, 5);
    }
}
