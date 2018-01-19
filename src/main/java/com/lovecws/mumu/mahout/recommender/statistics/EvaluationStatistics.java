package com.lovecws.mumu.mahout.recommender.statistics;

import com.alibaba.fastjson.JSON;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 统计实体
 * @date 2017-11-09 17:13
 */
public class EvaluationStatistics {

    private String recommenderType;//推荐类型 user、itembased

    private String datafile;//测试数据地址
    private String similarity;//相似度
    private String neighborhood;//邻域

    private int neighborhoodCount;//邻域数量
    private String recommender;//推荐算法
    private String evaluator;//评估算法

    private double trainingPercentage;//测试数据集
    private double evaluationPercentage;//评估数据集
    private long time;//花费时间
    private double evalute;//测试结果
    private double step;//测试结果

    public EvaluationStatistics(final String recommenderType, final String datafile, final String similarity, final String neighborhood, final int neighborhoodCount, final String recommender, final String evaluator, final double trainingPercentage, final double evaluationPercentage, final long time, double evalute, double step) {
        this.recommenderType = recommenderType;
        this.datafile = datafile;
        this.similarity = similarity;
        this.neighborhood = neighborhood;
        this.neighborhoodCount = neighborhoodCount;
        this.recommender = recommender;
        this.evaluator = evaluator;
        this.trainingPercentage = trainingPercentage;
        this.evaluationPercentage = evaluationPercentage;
        this.time = time;
        this.evalute = evalute;
        this.step = step;
    }

    public String getRecommenderType() {
        return recommenderType;
    }

    public void setRecommenderType(final String recommenderType) {
        this.recommenderType = recommenderType;
    }

    public String getDatafile() {
        return datafile;
    }

    public void setDatafile(final String datafile) {
        this.datafile = datafile;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(final String similarity) {
        this.similarity = similarity;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(final String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public int getNeighborhoodCount() {
        return neighborhoodCount;
    }

    public void setNeighborhoodCount(final int neighborhoodCount) {
        this.neighborhoodCount = neighborhoodCount;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(final String recommender) {
        this.recommender = recommender;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(final String evaluator) {
        this.evaluator = evaluator;
    }

    public double getTrainingPercentage() {
        return trainingPercentage;
    }

    public void setTrainingPercentage(final double trainingPercentage) {
        this.trainingPercentage = trainingPercentage;
    }

    public double getEvaluationPercentage() {
        return evaluationPercentage;
    }

    public void setEvaluationPercentage(final double evaluationPercentage) {
        this.evaluationPercentage = evaluationPercentage;
    }

    public long getTime() {
        return time;
    }

    public void setTime(final long time) {
        this.time = time;
    }

    public double getEvalute() {
        return evalute;
    }

    public void setEvalute(final double evalute) {
        this.evalute = evalute;
    }

    public double getStep() {
        return step;
    }

    public void setStep(final double step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
