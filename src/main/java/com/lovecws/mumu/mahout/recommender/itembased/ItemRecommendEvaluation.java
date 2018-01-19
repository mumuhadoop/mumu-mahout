package com.lovecws.mumu.mahout.recommender.itembased;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 物品推荐评估
 * @date 2017-11-09 9:10
 */
public class ItemRecommendEvaluation {

    /**
     * 评估 根据评估系数来判断推荐准确率
     *
     * @param datafile             数据文件路径
     * @param similarity           相似度算法 偏好度["euclideanDistance","euclideanDistance+","pearson","pearson+","uncenteredCosine","cachingItem"]  无偏好度["logLikelihood","tanimotoCoefficient"]
     * @param recommender          推荐算法  偏好度["itemBased","random","itemUserAverage","itemAverage","SVD","caching"] 无偏好度["booleanPrefItem"]
     * @param evaluator            评估算法 ["RMS","averageAbsoluteDifference"] 计算查准率和查全率["IRStats"]
     * @param trainingPercentage   正式占所有数据的比例 当计算评估系数的时候该值为0-1,当计算查准率和查全率的时候该值为1-Integer.MAX_VALUE
     * @param evaluationPercentage 评估数据占所有的数据的比例
     * @throws TasteException
     * @throws IOException
     */
    public double evaluate(String datafile, String similarity, String recommender, String evaluator, double trainingPercentage, double evaluationPercentage) throws TasteException, IOException {
        RandomUtils.useTestSeed();
        DataModel model = new FileDataModel(new File(datafile));
        //推荐算法构造器
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(final DataModel dataModel) throws TasteException {
                //获取物品相关度算法
                ItemSimilarity itemSimilarity = itemSimilarity(similarity, dataModel);
                return recommender(recommender, itemSimilarity, dataModel);
            }
        };
        //数据模型构造器
        DataModelBuilder dataModelBuilder = new DataModelBuilder() {
            @Override
            public DataModel buildDataModel(final FastByIDMap<PreferenceArray> fastByIDMap) {
                for (Map.Entry<Long, PreferenceArray> entry : fastByIDMap.entrySet()) {
                    System.out.println(entry.getKey() + "\t" + entry.getValue());
                }
                //忽略偏好值
                if (("logLikelihood".equalsIgnoreCase(similarity) || "tanimotoCoefficient".equalsIgnoreCase(similarity)) && ("booleanPrefItem".equalsIgnoreCase(recommender) || "null".equalsIgnoreCase(recommender))) {
                    return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(fastByIDMap));
                }
                return new GenericDataModel(fastByIDMap);
            }
        };
        //查询数据的查准率和查全率
        if ("IRStats".equalsIgnoreCase(evaluator)) {
            RecommenderIRStatsEvaluator irStatsEvaluator = new GenericRecommenderIRStatsEvaluator();
            IRStatistics irStatistics = irStatsEvaluator.evaluate(recommenderBuilder, dataModelBuilder, model, null, (int) trainingPercentage, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, evaluationPercentage);
            System.out.println("查准率:" + irStatistics.getPrecision());
            System.out.println("查全率:" + irStatistics.getRecall());
            double precision = irStatistics.getPrecision();
            if (Double.isNaN(precision)) {
                return -1.0;
            }
            return precision;
        } else {
            //推荐评估
            RecommenderEvaluator recommenderEvaluator = recommenderEvaluator(evaluator);
            //评估
            double evaluate = recommenderEvaluator.evaluate(recommenderBuilder, dataModelBuilder, model, trainingPercentage, evaluationPercentage);
            System.out.println("evaluate:" + evaluate);
            if (Double.isNaN(evaluate)) {
                return -1.0;
            }
            return evaluate;
        }
    }

    /**
     * 设置相关度度量标准
     *
     * @param similarity 相关度算法
     * @param model      数据模型
     * @return
     * @throws TasteException
     */
    private ItemSimilarity itemSimilarity(String similarity, DataModel model) throws TasteException {
        //设置相关度
        ItemSimilarity itemSimilarity = null;
        if (similarity == null) similarity = "";
        switch (similarity) {
            //基于欧式距离算法计算相似度
            case "euclideanDistance":
                itemSimilarity = new EuclideanDistanceSimilarity(model);
                break;
            //基于加权欧式距离算法计算相似度
            case "euclideanDistance+":
                itemSimilarity = new EuclideanDistanceSimilarity(model, Weighting.WEIGHTED);
                break;
            case "pearson":
                itemSimilarity = new PearsonCorrelationSimilarity(model);
                break;
            case "pearson+":
                itemSimilarity = new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);
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
            case "uncenteredCosine":
                itemSimilarity = new UncenteredCosineSimilarity(model);
                break;
            case "cachingItem":
                itemSimilarity = new CachingItemSimilarity(new PearsonCorrelationSimilarity(model), model);
                break;
            default:
                itemSimilarity = new EuclideanDistanceSimilarity(model);
                break;
        }
        return itemSimilarity;
    }

    /**
     * 获取评估推荐
     *
     * @param recommender    评估算法
     * @param itemSimilarity 物品相似度
     * @param model          数据模型
     * @return
     * @throws TasteException
     */
    private Recommender recommender(String recommender, ItemSimilarity itemSimilarity, DataModel model) throws TasteException {
        //recommender 推荐
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
            //基于奇异值分解的推荐算法
            case "SVD":
                //Factorizer factorizer = new ParallelSGDFactorizer(model, 2, 0.7, 2);
                Factorizer factorizer = new ALSWRFactorizer(model, 10, 0.05, 10);
                itemBasedRecommender = new SVDRecommender(model, factorizer);
                break;
            case "caching":
                itemBasedRecommender = new CachingRecommender(new GenericItemBasedRecommender(model, itemSimilarity));
                break;
            default:
                itemBasedRecommender = new GenericItemBasedRecommender(model, itemSimilarity);
                break;
        }
        return itemBasedRecommender;
    }

    /**
     * 获取推荐评估算法
     *
     * @param evaluator
     * @return
     */
    private RecommenderEvaluator recommenderEvaluator(String evaluator) {
        //设置推荐评估
        RecommenderEvaluator recommenderEvaluator = null;
        if (evaluator == null) evaluator = "";
        switch (evaluator) {
            case "RMS":
                recommenderEvaluator = new RMSRecommenderEvaluator();
                break;
            case "averageAbsoluteDifference":
                recommenderEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
                break;
            default:
                recommenderEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
                break;
        }
        return recommenderEvaluator;
    }
}
