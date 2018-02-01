package com.lovecws.mumu.mahout.clustering.vector;

import com.lovecws.mumu.mahout.clustering.util.SequenceFileUtil;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 向量
 * @date 2018-01-22 14:28
 */
public class BasicVectororization {

    public void vectororization(String sequenceFile) {
        List<Vector> namedVectors = new ArrayList<Vector>();
        NamedVector namedVector = null;

        namedVector = new NamedVector(new DenseVector(new double[]{0.11, 510, 1}), "small");
        namedVectors.add(namedVector);

        namedVector = new NamedVector(new DenseVector(new double[]{0.23, 650, 3}), "small");
        namedVectors.add(namedVector);

        namedVector = new NamedVector(new DenseVector(new double[]{0.09, 630, 1}), "small");
        namedVectors.add(namedVector);

        namedVector = new NamedVector(new DenseVector(new double[]{0.25, 590, 3}), "big");
        namedVectors.add(namedVector);

        namedVector = new NamedVector(new DenseVector(new double[]{0.18, 520, 2}), "small");
        namedVectors.add(namedVector);

        SequenceFileUtil.writePointToSequenceFile(namedVectors, sequenceFile);

        SequenceFileUtil.readeSequenceFile(sequenceFile,"SequenceFile");
    }
}
