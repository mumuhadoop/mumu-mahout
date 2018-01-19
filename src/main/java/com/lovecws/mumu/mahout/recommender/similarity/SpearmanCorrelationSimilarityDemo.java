package com.lovecws.mumu.mahout.recommender.similarity;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基于斯皮尔曼相关系数相似度算法
 * @date 2018-01-19 15:40
 */
public class SpearmanCorrelationSimilarityDemo extends AbstractSimilrityDemo {

    /**
     * 基于用户的斯皮尔曼相似度算法
     *
     * @param dataFile
     */
    public void userBased(String dataFile) throws IOException, TasteException {
        //构建datamodel
        DataModel dataModel = new FileDataModel(new File(dataFile));
        UserSimilarity userSimilarity = new SpearmanCorrelationSimilarity(dataModel);
        userBased(dataModel, userSimilarity, 5);
    }
}
