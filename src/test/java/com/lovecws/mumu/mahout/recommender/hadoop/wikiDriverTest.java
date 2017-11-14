package com.lovecws.mumu.mahout.recommender.hadoop;

import com.lovecws.mumu.mahout.MahoutConfiguration;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 维基百科大数据推荐
 * @date 2017-11-10 11:08
 */
public class wikiDriverTest {

    @Test
    public void wikiDriver() throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        String prefix = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        WikiDriver.main(new String[]{MahoutConfiguration.HADOOP_ADDRESS+"/mumu/mahout/recommender/wiki/input",
                MahoutConfiguration.HADOOP_ADDRESS+"/mumu/mahout/recommender/wiki/temp/" + prefix,
                MahoutConfiguration.HADOOP_ADDRESS+"/mumu/mahout/recommender/wiki/output/" + prefix});
    }
}
