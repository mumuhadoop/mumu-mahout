package com.lovecws.mumu.mahout.clustering.distancemeasure;

import org.apache.mahout.common.distance.*;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 距离测试
 * @date 2018-01-22 13:35
 */
public class DistanceMeasureDemo {

    public DistanceMeasure distanceMeasure(String measure) {
        if (measure == null) measure = "";
        DistanceMeasure distanceMeasure = null;
        switch (measure) {
            case "SquaredEuclideanDistanceMeasure"://平方欧式距离
                distanceMeasure = new SquaredEuclideanDistanceMeasure();
                break;
            case "EuclideanDistanceMeasure"://欧式距离
                distanceMeasure = new EuclideanDistanceMeasure();
                break;
            case "WeightedEuclideanDistanceMeasure"://加权欧式距离
                distanceMeasure = new WeightedEuclideanDistanceMeasure();
                break;
            case "MahalanobisDistanceMeasure":
                distanceMeasure = new MahalanobisDistanceMeasure();
                break;
            case "MinkowskiDistanceMeasure":
                distanceMeasure = new MinkowskiDistanceMeasure();
                break;
            case "ManhattanDistanceMeasure"://曼哈顿距离
                distanceMeasure = new ManhattanDistanceMeasure();
                break;
            case "WeightedManhattanDistanceMeasure"://加权曼哈顿距离
                distanceMeasure = new WeightedManhattanDistanceMeasure();
                break;
            case "TanimotoDistanceMeasure"://谷本距离算法
                distanceMeasure = new TanimotoDistanceMeasure();
                break;
            case "ChebyshevDistanceMeasure":
                distanceMeasure = new ChebyshevDistanceMeasure();
                break;
            case "CosineDistanceMeasure"://余弦距离
                distanceMeasure = new CosineDistanceMeasure();
                break;
            default:
                distanceMeasure = new EuclideanDistanceMeasure();
                break;
        }
        return distanceMeasure;
    }
}
