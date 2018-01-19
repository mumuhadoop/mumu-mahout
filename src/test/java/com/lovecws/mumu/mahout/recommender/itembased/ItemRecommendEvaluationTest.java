package com.lovecws.mumu.mahout.recommender.itembased;

import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 物品推荐评估测试
 * @date 2017-11-09 10:05
 */
public class ItemRecommendEvaluationTest {

    private ItemRecommendEvaluation recommendEvaluation = new ItemRecommendEvaluation();
    //private static String datafile = ItemBasedRecommenderTest.class.getResource("/grouplens/ratings.csv").getPath();
    private static String datafile = ItemBasedRecommenderTest.class.getResource("/ml/ratings.csv").getPath();

    /**
     * euclideanDistance 算法 基于平均差值
     * 测试结果 : evaluate:0.6658299429218175
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void euclideanDistanceWithItemBasedAndAverageAbsoluteDifference() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "euclideanDistance", "itemBased", "averageAbsoluteDifference", 0.7, 1.0);
    }

    /**
     * euclideanDistance 算法 基于方差
     * 测试结果 : evaluate:0.8678915714808663
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void euclideanDistanceWithItemBasedAndRMS() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "euclideanDistance", "itemBased", "RMS", 0.7, 1.0);
    }

    /**
     * euclideanDistance 算法 计算查准率和查全率
     * 测试结果 :
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void euclideanDistanceWithItemBasedAndIRStats() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "euclideanDistance", "itemBased", "IRStats", 200, 0.2);
    }

    /**
     * euclideanDistance 附加权重 算法 基于平均差值
     * 测试结果 : evaluate:0.6664752443233077
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void euclideanDistanceWeightWithItemBasedAndAverageAbsoluteDifference() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "euclideanDistance+", "itemBased", "averageAbsoluteDifference", 0.7, 1.0);
    }

    /**
     * euclideanDistance 附加权重 算法 基于方差
     * 测试结果 : evaluate:0.8688310736464505
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void euclideanDistanceWeightWithItemBasedAndRMS() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "euclideanDistance+", "itemBased", "RMS", 0.7, 1.0);
    }

    /**
     * euclideanDistance 附加权重 算法 计算查准率和查全率
     * 测试结果 :
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void euclideanDistanceWeightWithItemBasedAndIRStats() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "euclideanDistance+", "itemBased", "IRStats", 2, 1.0);
    }

    /**
     * pearson算法 基于平均差值
     * 测试结果 :evaluate:0.7502414476267038
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void pearsonWithItemBasedAndAverageAbsoluteDifference() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "pearson", "itemBased", "averageAbsoluteDifference", 0.7, 1.0);
    }

    /**
     * pearson算法 基于方差
     * 测试结果 :evaluate:1.113328221378138
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void pearsonWithItemBasedAndRMS() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "pearson", "itemBased", "RMS", 0.7, 1.0);
    }

    /**
     * pearson算法 计算查准率和查全率
     * 测试结果 :
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void pearsonWithItemBasedAndIRStats() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "pearson", "itemBased", "IRStats", 2, 1.0);
    }

    /**
     * pearson 附加权重 算法 基于平均差值
     * 测试结果 :evaluate:0.7450433303040959
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void pearsonWeightWithItemBasedAndAverageAbsoluteDifference() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "pearson+", "itemBased", "averageAbsoluteDifference", 0.7, 1.0);
    }

    /**
     * pearson 附加权重 算法 基于方差
     * 测试结果 :evaluate:1.1036398999539097
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void pearsonWeightWithItemBasedAndRMS() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "pearson+", "itemBased", "RMS", 0.7, 1.0);
    }

    /**
     * pearson 附加权重 算法 计算查准率和查全率
     * 测试结果 :
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void pearsonWeightWithItemBasedAndIRStats() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "pearson+", "itemBased", "IRStats", 2, 1.0);
    }

    /**
     * uncenteredCosine 算法 基于平均差值
     * 测试结果 :evaluate:0.7407141505316921
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void uncenteredCosineWithItemBasedAndAverageAbsoluteDifference() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "uncenteredCosine", "itemBased", "averageAbsoluteDifference", 0.7, 1.0);
    }

    /**
     * uncenteredCosine 算法 基于方差
     * 测试结果 :evaluate:0.951914678407429
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void uncenteredCosineWithItemBasedAndRMS() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "uncenteredCosine", "itemBased", "RMS", 0.7, 1.0);
    }

    /**
     * uncenteredCosine 算法 计算查准率和查全率
     * 测试结果 :
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void uncenteredCosineWithItemBasedAndIRStats() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "uncenteredCosine", "itemBased", "IRStats", 2, 1.0);
    }

    /**
     * cachingItem 算法 基于平均差值
     * 测试结果 :evaluate:0.7502414476267076
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void cachingItemWithItemBasedAndAverageAbsoluteDifference() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "cachingItem", "itemBased", "averageAbsoluteDifference", 0.7, 1.0);
    }

    /**
     * cachingItem 算法 基于方差
     * 测试结果 :evaluate:1.113328221378135
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void cachingItemWithItemBasedAndRMS() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "cachingItem", "itemBased", "RMS", 0.7, 1.0);
    }

    /**
     * cachingItem 算法 计算查准率和查全率
     * 测试结果 :
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void cachingItemWithItemBasedAndIRStats() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "cachingItem", "itemBased", "IRStats", 2, 1.0);
    }

    /**
     * logLikelihood无偏好 算法 基于平均差值
     * 测试结果 :evaluate:292.2096805613685
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void logLikelihoodWithItemBasedAndAverageAbsoluteDifference() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "logLikelihood", "booleanPrefItem", "averageAbsoluteDifference", 0.7, 1.0);
    }

    /**
     * logLikelihood无偏好 算法 基于方差
     * 测试结果 :evaluate:429.5679348893712
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void logLikelihoodWithItemBasedAndRMS() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "logLikelihood", "booleanPrefItem", "RMS", 0.7, 1.0);
    }

    /**
     * logLikelihood无偏好 算法 计算查准率和查全率
     * 测试结果 :
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void logLikelihoodWithItemBasedAndIRStats() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "logLikelihood", "booleanPrefItem", "IRStats", 2, 1.0);
    }

    /**
     * tanimotoCoefficient 无偏好 算法 基于平均差值
     * 测试结果 :evaluate:36.69416397692217
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void tanimotoCoefficientWithItemBasedAndAverageAbsoluteDifference() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "tanimotoCoefficient", "booleanPrefItem", "averageAbsoluteDifference", 0.7, 1.0);
    }

    /**
     * tanimotoCoefficient 无偏好 算法 基于方差
     * 测试结果 :evaluate:63.2964647534137
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void tanimotoCoefficientWithItemBasedAndRMS() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "tanimotoCoefficient", "booleanPrefItem", "RMS", 0.7, 1.0);
    }

    /**
     * tanimotoCoefficient 无偏好 算法 计算查准率和查全率
     * 测试结果 :
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void tanimotoCoefficientWithItemBasedAndIRStats() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "tanimotoCoefficient", "booleanPrefItem", "IRStats", 2, 1.0);
    }


    /**
     * euclideanDistance 基于SVD奇异值分解的推荐算法 基于平均差值
     * 测试结果 :evaluate:0.7415634765507289
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void euclideanDistanceWithSVDAndAverageAbsoluteDifference() throws IOException, TasteException {
        recommendEvaluation.evaluate(datafile, "euclideanDistance", "SVD", "averageAbsoluteDifference", 0.7, 1.0);
    }
}
