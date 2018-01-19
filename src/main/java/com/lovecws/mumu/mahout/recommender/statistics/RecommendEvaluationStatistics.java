package com.lovecws.mumu.mahout.recommender.statistics;

import com.lovecws.mumu.mahout.recommender.itembased.ItemRecommendEvaluation;
import com.lovecws.mumu.mahout.recommender.userbased.UserRecommendEvaluation;
import org.apache.commons.lang3.StringUtils;
import org.apache.mahout.cf.taste.common.TasteException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 评估统计
 * @date 2017-11-09 17:12
 */
public class RecommendEvaluationStatistics {

    private UserRecommendEvaluation userRecommendEvaluation = new UserRecommendEvaluation();
    private ItemRecommendEvaluation itemRecommendEvaluation = new ItemRecommendEvaluation();

    /**
     * @param recommenderType
     * @param datafile
     * @param similarity
     * @param neighborhood
     * @param neighborhoodCount
     * @param recommender
     * @param evaluator
     * @param step
     * @throws IOException
     * @throws TasteException
     */
    public void statictics(String recommenderType, final String datafile, final String similarity, final String neighborhood, final int neighborhoodCount, final String recommender, String evaluator, double step) throws IOException, TasteException {
        List<EvaluationStatistics> statistics = new ArrayList<EvaluationStatistics>();
        double maxtrainingPercentage = 1.0, maxevaluationPercentage = 1.0;
        //递增评估数据量
        for (double evaluationPercentage = 0.0; evaluationPercentage <= maxevaluationPercentage; evaluationPercentage = (evaluationPercentage * 100 + step * 100) / 100) {
            if ("IRStats".equalsIgnoreCase(evaluator)) {
                if (step < 1) step = 1;
                maxtrainingPercentage = 100;
            }
            for (double trainingPercentage = 0.0; trainingPercentage <= maxtrainingPercentage; trainingPercentage = (trainingPercentage * 100 + step * 100) / 100) {
                System.out.println("\t");
                double evalute = 0;
                long start = System.currentTimeMillis();
                if ("user".equalsIgnoreCase(recommenderType)) {
                    evalute = userRecommendEvaluation.evalute(datafile, similarity, neighborhood, neighborhoodCount, recommender, evaluator, trainingPercentage, evaluationPercentage);
                } else if ("itembased".equalsIgnoreCase(recommenderType)) {
                    evalute = itemRecommendEvaluation.evaluate(datafile, similarity, recommender, evaluator, trainingPercentage, evaluationPercentage);
                }
                long end = System.currentTimeMillis();
                EvaluationStatistics evaluationStatistics = new EvaluationStatistics(recommenderType, datafile, similarity, neighborhood, neighborhoodCount, recommender, evaluator, trainingPercentage, evaluationPercentage, (end - start) / 1000, evalute, step);
                statistics.add(evaluationStatistics);
            }
        }
        //排序
        Collections.sort(statistics, new Comparator<EvaluationStatistics>() {
            @Override
            public int compare(final EvaluationStatistics o1, final EvaluationStatistics o2) {
                if (StringUtils.isEmpty(String.valueOf(o1.getEvalute())) || StringUtils.isEmpty(String.valueOf(o2.getEvalute()))) {
                    return 0;
                }
                return Double.compare(o1.getEvalute(), o2.getEvalute());
            }
        });
        System.out.println("全部测试结果");
        for (EvaluationStatistics evaluationStatistics : statistics) {
            System.out.println(evaluationStatistics);
        }
    }
}
