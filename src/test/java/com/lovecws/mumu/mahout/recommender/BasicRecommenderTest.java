package com.lovecws.mumu.mahout.recommender;

import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基本推荐程序测试
 * @date 2018-01-19 17:30
 */
public class BasicRecommenderTest {

    @Test
    public void recommend() throws IOException, TasteException {
        new BasicRecommender().recommend();
    }

    @Test
    public void userNeighborhood() throws IOException, TasteException {
        new BasicRecommender().userNeighborhood();
    }

    @Test
    public void evaluate() throws IOException, TasteException {
        new BasicRecommender().evaluate();
    }
}
