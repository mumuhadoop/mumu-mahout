package com.lovecws.mumu.mahout.recommender.userbased;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.RunningAverage;
import org.apache.mahout.cf.taste.impl.eval.*;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.CachingUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基于用户的评估推荐引擎的质量
 * @date 2017-11-07 15:43
 */
public class UserRecommendEvaluation {

    /**
     * 用户推荐评估
     *
     * @param datafile             数据文件路径
     * @param similarity           相似度算法 偏好度["euclideanDistance","euclideanDistance+","pearsonCorrelation","pearson+","uncenteredCosine","spearmanCorrelation","cachingItem","cityBlock"]  无偏好度["logLikelihood","tanimotoCoefficient"]
     * @param neighborhood         用户邻域 ["NearestN","Threshold","Caching"]
     * @param neighborhoodCount    用户邻域数量
     * @param recommender          用户推荐算法  偏好度["genericUserBased","random","itemUserAverage","itemAverage","SVD","caching"] 无偏好度["genericBooleanPref"]
     * @param evaluator            用户评估算法 ["RMS","averageAbsoluteDifference"] 计算查准率和查全率["IRStats"]
     * @param trainingPercentage   正式占所有数据的比例 当计算评估系数的时候该值为0-1,当计算查准率和查全率的时候该值为1-Integer.MAX_VALUE
     * @param evaluationPercentage 评估数据占所有的数据的比例
     * @throws IOException
     * @throws TasteException
     */
    public double evalute(String datafile, String similarity, String neighborhood, double neighborhoodCount, String recommender, String evaluator, double trainingPercentage, double evaluationPercentage) throws IOException, TasteException {
        RandomUtils.useTestSeed();
        DataModel model = new FileDataModel(new File(datafile));
        //推荐算法
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(final DataModel dataModel) throws TasteException {
                UserSimilarity userSimilarity = userSimilarity(similarity, dataModel);
                UserNeighborhood userNeighborhood = userNeighborhood(neighborhood, neighborhoodCount, userSimilarity, dataModel);
                return userRecommender(recommender, dataModel, userNeighborhood, userSimilarity);
            }
        };
        //数据模型
        DataModelBuilder dataModelBuilder = new DataModelBuilder() {
            @Override
            public DataModel buildDataModel(final FastByIDMap<PreferenceArray> fastByIDMap) {
                /*for (Map.Entry<Long, PreferenceArray> entry : fastByIDMap.entrySet()) {
                    System.out.println(entry.getKey() + "\t" + entry.getValue());
                }*/
                //忽略偏好值
                if (("logLikelihood".equalsIgnoreCase(similarity) || "tanimotoCoefficient".equalsIgnoreCase(similarity)) && ("genericBooleanPref".equalsIgnoreCase(recommender) || "null".equalsIgnoreCase(recommender))) {
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
        } else {
            //推荐评估
            RecommenderEvaluator recommenderEvaluator = recommenderEvaluator(evaluator);
            //评估
            double evaluate = recommenderEvaluator.evaluate(recommenderBuilder, dataModelBuilder, model, trainingPercentage, evaluationPercentage);
            System.out.println("evaluate:" + evaluate);
            if(Double.isNaN(evaluate)){
                return -1.0;
            }
            return evaluate;
        }
        return 0.0;
    }

    /**
     * 测试推荐花费时间
     *
     * @param datafile          数据文件路径
     * @param similarity        相似度算法 偏好度["euclideanDistance","euclideanDistance+","pearson","pearson+","uncenteredCosine","spearmanCorrelation","cachingItem","cityBlock"]  无偏好度["logLikelihood","tanimotoCoefficient"]
     * @param neighborhood      用户邻域 ["NearestN","Threshold","Caching"]
     * @param neighborhoodCount 用户邻域数量
     * @param recommender       用户推荐算法  偏好度["genericUserBased","random","itemUserAverage","itemAverage","SVD","caching"] 无偏好度["genericBooleanPref"]
     * @throws TasteException
     * @throws IOException
     */
    public void time(String datafile, String similarity, String neighborhood, double neighborhoodCount, String recommender) throws TasteException, IOException {
        DataModel model = new FileDataModel(new File(datafile));
        UserSimilarity userSimilarity = userSimilarity(similarity, model);
        UserNeighborhood userNeighborhood = userNeighborhood(neighborhood, neighborhoodCount, userSimilarity, model);
        Recommender userRecommender = userRecommender(recommender, model, userNeighborhood, userSimilarity);
        LoadStatistics loadStatistics = LoadEvaluator.runLoad(userRecommender);
        RunningAverage timing = loadStatistics.getTiming();
        System.out.println("average:" + timing.getAverage() + "\t" + "count:" + timing.getCount());
    }

    /**
     * 获取用户相似度算法
     *
     * @param similarity 用户相似度
     * @param model      数据模型
     * @return
     */
    public UserSimilarity userSimilarity(String similarity, DataModel model) throws TasteException {
        UserSimilarity userSimilarity = null;
        if (similarity == null) similarity = "";
        switch (similarity.toUpperCase()) {
            case "EUCLIDEANDISTANCE":
                userSimilarity = new EuclideanDistanceSimilarity(model);
                break;
            case "EUCLIDEANDISTANCE+":
                userSimilarity = new EuclideanDistanceSimilarity(model, Weighting.WEIGHTED);
                break;
            case "UNCENTEREDCOSINE":
                userSimilarity = new UncenteredCosineSimilarity(model);
                break;
            case "UNCENTEREDCOSINE+":
                userSimilarity = new UncenteredCosineSimilarity(model, Weighting.WEIGHTED);
                break;
            case "PEARSONCORRELATION":
                userSimilarity = new PearsonCorrelationSimilarity(model);
                break;
            case "PEARSONCORRELATION+":
                userSimilarity = new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);
                break;
            case "SPEARMANCORRELATION":
                model = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(model));
                userSimilarity = new SpearmanCorrelationSimilarity(model);
                break;
            case "LOGLIKELIHOOD":
                model = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(model));
                userSimilarity = new LogLikelihoodSimilarity(model);
                break;
            case "TANIMOTOCOEFFICIENT":
                userSimilarity = new TanimotoCoefficientSimilarity(model);
                break;
            case "CITYBLOCK":
                userSimilarity = new CityBlockSimilarity(model);
                break;
            case "CACHING":
                userSimilarity = new CachingUserSimilarity(new EuclideanDistanceSimilarity(model), model);
                break;
            default:
                userSimilarity = new PearsonCorrelationSimilarity(model);
                break;
        }
        return userSimilarity;
    }

    /**
     * 获取用户领域
     *
     * @param neighborhood      领域算法
     * @param neighborhoodCount 邻域数量
     * @param userSimilarity    用户相似度
     * @param model             数据模型
     * @return
     * @throws TasteException
     */
    public UserNeighborhood userNeighborhood(String neighborhood, double neighborhoodCount, UserSimilarity userSimilarity, DataModel model) throws TasteException {
        UserNeighborhood userNeighborhood = null;
        if (neighborhood == null) neighborhood = "";
        switch (neighborhood.toUpperCase()) {
            case "NEARESTN":
                userNeighborhood = new NearestNUserNeighborhood((int) neighborhoodCount, userSimilarity, model);
                break;
            case "THRESHOLD":
                userNeighborhood = new ThresholdUserNeighborhood(neighborhoodCount, userSimilarity, model);
                break;
            case "CACHING":
                userNeighborhood = new CachingUserNeighborhood(new NearestNUserNeighborhood((int) neighborhoodCount, userSimilarity, model), model);
                break;
            default:
                userNeighborhood = new NearestNUserNeighborhood((int) neighborhoodCount, userSimilarity, model);
                break;
        }
        return userNeighborhood;
    }

    /**
     * 推荐评估
     *
     * @param recommender  推荐算法
     * @param model        数据模型
     * @param neighborhood 用户领域
     * @param similarity   用户相似度
     * @return
     * @throws TasteException
     */
    public Recommender userRecommender(String recommender, DataModel model, UserNeighborhood neighborhood, UserSimilarity similarity) throws TasteException {
        Recommender userBasedRecommender = null;
        if (recommender == null) recommender = "";
        switch (recommender.toUpperCase()) {
            case "GENERICUSERBASED":
                userBasedRecommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
                break;
            case "ITEMUSERAVERAGE":
                userBasedRecommender = new ItemUserAverageRecommender(model);
                break;
            case "ITEMAVERAGE":
                userBasedRecommender = new ItemAverageRecommender(model);
                break;
            case "SVD":
                Factorizer factorizer = new ALSWRFactorizer(model, 10, 0.05, 10);
                userBasedRecommender = new SVDRecommender(model, factorizer);
                break;
            case "CACHING":
                userBasedRecommender = new CachingRecommender(new GenericUserBasedRecommender(model, neighborhood, similarity));
                break;
            case "RANDOM":
                userBasedRecommender = new RandomRecommender(model);
                break;
            case "GENERICBOOLEANPREF":
                userBasedRecommender = new GenericBooleanPrefUserBasedRecommender(model, neighborhood, similarity);
                break;
            default:
                userBasedRecommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
                break;
        }
        return userBasedRecommender;
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
        switch (evaluator.toUpperCase()) {
            case "RMS":
                recommenderEvaluator = new RMSRecommenderEvaluator();
                break;
            case "AVERAGEABSOLUTEDIFFERENCE":
                recommenderEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
                break;
            default:
                recommenderEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
                break;
        }
        return recommenderEvaluator;
    }
}
