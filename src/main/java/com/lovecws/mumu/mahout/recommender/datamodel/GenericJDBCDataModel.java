package com.lovecws.mumu.mahout.recommender.datamodel;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: jdbc数据模型
 * @date 2018-01-19 11:05
 */
public class GenericJDBCDataModel implements JDBCDataModel {
    @Override
    public DataSource getDataSource() {
        return null;
    }

    @Override
    public FastByIDMap<PreferenceArray> exportWithPrefs() throws TasteException {
        return null;
    }

    @Override
    public FastByIDMap<FastIDSet> exportWithIDsOnly() throws TasteException {
        return null;
    }

    @Override
    public LongPrimitiveIterator getUserIDs() throws TasteException {
        return null;
    }

    @Override
    public PreferenceArray getPreferencesFromUser(final long l) throws TasteException {
        return null;
    }

    @Override
    public FastIDSet getItemIDsFromUser(final long l) throws TasteException {
        return null;
    }

    @Override
    public LongPrimitiveIterator getItemIDs() throws TasteException {
        return null;
    }

    @Override
    public PreferenceArray getPreferencesForItem(final long l) throws TasteException {
        return null;
    }

    @Override
    public Float getPreferenceValue(final long l, final long l1) throws TasteException {
        return null;
    }

    @Override
    public Long getPreferenceTime(final long l, final long l1) throws TasteException {
        return null;
    }

    @Override
    public int getNumItems() throws TasteException {
        return 0;
    }

    @Override
    public int getNumUsers() throws TasteException {
        return 0;
    }

    @Override
    public int getNumUsersWithPreferenceFor(final long l) throws TasteException {
        return 0;
    }

    @Override
    public int getNumUsersWithPreferenceFor(final long l, final long l1) throws TasteException {
        return 0;
    }

    @Override
    public void setPreference(final long l, final long l1, final float v) throws TasteException {

    }

    @Override
    public void removePreference(final long l, final long l1) throws TasteException {

    }

    @Override
    public boolean hasPreferenceValues() {
        return false;
    }

    @Override
    public float getMaxPreference() {
        return 0;
    }

    @Override
    public float getMinPreference() {
        return 0;
    }

    @Override
    public void refresh(final Collection<Refreshable> collection) {

    }
}
