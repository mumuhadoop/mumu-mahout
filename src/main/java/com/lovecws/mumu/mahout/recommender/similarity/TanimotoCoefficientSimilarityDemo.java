package com.lovecws.mumu.mahout.recommender.similarity;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 谷本系数相似度算法
 * @date 2018-01-19 15:42
 */
public class TanimotoCoefficientSimilarityDemo extends AbstractSimilrityDemo {

    /**
     * 基于用户的谷本系数相似度算法
     *
     * @param dataFile
     */
    public void userBasedBooleanPref(String dataFile) throws IOException, TasteException {
        //构建datamodel
        DataModel dataModel = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(new FileDataModel(new File(dataFile))));
        UserSimilarity userSimilarity = new TanimotoCoefficientSimilarity(dataModel);
        userBasedBooleanPref(dataModel, userSimilarity, 5, true);
    }

    /**
     * 基于物品的谷本系数相似度算法
     *
     * @param dataFile
     */
    public void itemBasedBooleanPref(String dataFile) throws IOException, TasteException {
        //构建datamodel
        DataModel dataModel = new FileDataModel(new File(dataFile));
        ItemSimilarity itemSimilarity = new TanimotoCoefficientSimilarity(dataModel);
        itemBasedBooleanPref(dataModel, itemSimilarity, 5, true);
    }

}
