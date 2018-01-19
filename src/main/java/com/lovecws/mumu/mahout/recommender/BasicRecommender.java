package com.lovecws.mumu.mahout.recommender;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基本推荐程序
 * @date 2018-01-19 17:28
 */
public class BasicRecommender {

    public void recommend() throws TasteException, IOException {
        DataModel model = new FileDataModel(new File(MahoutConfiguration.class.getResource("/datafile/intro.csv").getPath()));//加载数据文件
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);  //建立推荐模型
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        LongPrimitiveIterator iter = model.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = recommender.recommend(uid, 5);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem recommendedItem : list) {
                System.out.printf("(%s,%f)", recommendedItem.getItemID(), recommendedItem.getValue());
            }
        }
    }

    public void userNeighborhood() throws IOException, TasteException {
        DataModel model = new FileDataModel(new File(MahoutConfiguration.class.getResource("/datafile/intro.csv").getPath()));//加载数据文件
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);  //建立推荐模型
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
        LongPrimitiveIterator iter = model.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            long[] userNeighborhoods = neighborhood.getUserNeighborhood(uid);
            System.out.printf("uid:%s", uid);
            for (long userNeighborhood : userNeighborhoods) {
                double userSimilarity = similarity.userSimilarity(uid, userNeighborhood);
                System.out.printf("(%s,%f)", userNeighborhood, userSimilarity);
            }
            System.out.println();
        }
    }

    public void evaluate() throws IOException, TasteException {
        RandomUtils.useTestSeed();
        DataModel model = new FileDataModel(new File(MahoutConfiguration.class.getResource("/datafile/intro.csv").getPath()));//加载数据文件
        RecommenderEvaluator evalutor = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = new RecommenderBuilder() {

            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);  //建立推荐模型
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };

        double score = evalutor.evaluate(builder, null, model, 0.9, 1);
        System.out.println(score);
    }
}
