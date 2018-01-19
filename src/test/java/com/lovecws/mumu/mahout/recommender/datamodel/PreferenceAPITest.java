package com.lovecws.mumu.mahout.recommender.datamodel;

import com.lovecws.mumu.mahout.recommender.datamodel.PreferenceAPI;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: preference
 * @date 2017-11-08 14:21
 */
public class PreferenceAPITest {

    private PreferenceAPI preferenceAPI = new PreferenceAPI();

    @Test
    public void datamodel() throws TasteException {
        preferenceAPI.dataModel();
    }

    @Test
    public void booleanPrefDataModel() throws TasteException {
        preferenceAPI.booleanPrefDataModel();
    }

    @Test
    public void itemPreferenceArray() throws TasteException {
        preferenceAPI.itemPreferenceArray();
    }
}
