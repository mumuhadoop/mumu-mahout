package com.lovecws.mumu.mahout.recommender.userbased;

import com.lovecws.mumu.mahout.recommender.itembased.ItemBasedRecommenderTest;
import com.lovecws.mumu.mahout.recommender.userbased.UserRecommendEvaluation;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 推荐评估测试
 * @date 2017-11-08 11:40
 */
public class UserRecommendEvaluationTest {

    private UserRecommendEvaluation recommenderEvaluator = new UserRecommendEvaluation();

    //private static final String datafile = ItemBasedRecommenderTest.class.getResource("/grouplens/ratings.csv").getPath();
    //private static final String datafile = ItemBasedRecommenderTest.class.getResource("/datafile/evaluation.csv").getPath();
    private static String datafile = ItemBasedRecommenderTest.class.getResource("/ml/ratings.csv").getPath();
    //private static String datafile = ItemBasedRecommenderTest.class.getResource("/datafile/intro.csv").getPath();

    /**
     * euclideanDistance 欧式距离算法 基于平均差值
     * 测试结果 :
     * trainingPercentage: 0.8 evaluationPercentage: 0.2 evaluate:01.0410958904109593
     * trainingPercentage: 0.7 evaluationPercentage: 0.2 evaluate:0.7666666666666663
     * trainingPercentage: 0.65 evaluationPercentage: 0.2 evaluate:0.7061151085997662
     * trainingPercentage: 0.6 evaluationPercentage: 0.2 evaluate:0.6763803680981592
     * trainingPercentage: 0.55 evaluationPercentage: 0.2 evaluate:0.7356020942408377
     * trainingPercentage: 0.5 evaluationPercentage: 0.2 evaluate:0.8601190476190478
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void euclideanDistance() throws IOException, TasteException {
        recommenderEvaluator.evalute(datafile, "euclideanDistance", "NearestN", 2, "genericUserBased", "averageAbsoluteDifference", 0.65, 0.2);
    }

    /**
     * pearson 皮尔逊关系算法 基于平均差值
     * 测试结果 : evaluate:0.8643375680580772
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void pearson() throws IOException, TasteException {
        recommenderEvaluator.evalute(datafile, "pearsonCorrelation", "NearestN", 2, "genericUserBased", "averageAbsoluteDifference", 0.9, 1.0);
    }

    /**
     * pearson 皮尔逊关系算法 计算评估花费的时间
     * 测试结果 : average:77.61621621621613	count:925
     *
     * @throws IOException
     * @throws TasteException
     */
    @Test
    public void pearsonTime() throws IOException, TasteException {
        recommenderEvaluator.time(datafile, "pearson", "NearestN", 100, "genericUserBased");
    }
}
