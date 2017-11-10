package com.lovecws.mumu.mahout.recommender.statistics;

import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 评估统计
 * @date 2017-11-09 17:12
 */
public class RecommendEvaluationStatisticsTest {

    private RecommendEvaluationStatistics recommendEvaluationStatistics = new RecommendEvaluationStatistics();

    @Test
    public void statistics() throws IOException, TasteException {
        recommendEvaluationStatistics.statictics("user",
                RecommendEvaluationStatistics.class.getResource("/datafile/evaluation.csv").getPath(),
                "euclideanDistance",
                "NearestN",
                2,
                "genericUserBased",
                "averageAbsoluteDifference",
                0.1);
    }
}
