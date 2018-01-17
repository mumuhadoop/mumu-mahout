package com.lovecws.mumu.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基本推荐测试
 * @date 2017-11-08 10:10
 */
public class MahoutConfigurationTest {

    @Test
    public void recommend() throws IOException, TasteException {
        new MahoutConfiguration().recommend();
    }

    @Test
    public void userNeighborhood() throws IOException, TasteException {
        new MahoutConfiguration().userNeighborhood();
    }

    @Test
    public void evalute() throws IOException, TasteException {
        new MahoutConfiguration().evalute();
    }
}
