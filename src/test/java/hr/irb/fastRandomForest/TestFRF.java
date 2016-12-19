/*
 * Test.java
 * (C) 2009 the class authors / UCD / ...
 */

 /*
 * License comes here.
 */
package hr.irb.fastRandomForest;

import java.io.InputStream;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Some tests.
 *
 * @author Santi Villalba
 * @version $Id: TestFRF.java 44 2009-06-24 11:09:43Z sdvillal $
 */
public class TestFRF {

    private final static String DIABETES = "hr/irb/fastRandomForest/diabetes.arff";
    private final static String VOTE = "hr/irb/fastRandomForest/vote.arff";
    private final static String CAL_HOUSING = "hr/irb/fastRandomForest/cal_housing.arff"; 

    private static Instances _diabetes;
    private static Instances _vote;
    private static Instances _cal_housing;

    public static Instances diabetes() throws Exception {
        if (null == _diabetes)
            _diabetes = loadData(DIABETES);
        return _diabetes;
    }

    public static Instances vote() throws Exception {
        if (null == _vote)
            _vote = loadData(VOTE);
        return _vote;
    }
    
    public static Instances cal_housing() throws Exception {
        if (null == _cal_housing)
            _cal_housing = loadData(CAL_HOUSING);
        return _cal_housing;
    }

    private static Instances loadData(String location) throws Exception {
        ClassLoader classLoader = TestFRF.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(location);
        Instances data = new ConverterUtils.DataSource(is).getDataSet();
        if (-1 == data.classIndex())
            data.setClassIndex(data.numAttributes() - 1);
        return data;
    }

    private static double[] importances(FastRandomForest frf, Instances data) throws Exception {
        frf.buildClassifier(data);
        return frf.getFeatureImportances();
    }

    private static FastRandomForest frf() {
        FastRandomForest rf = new FastRandomForest();
        rf.setNumTrees(100);
        rf.setComputeImportances(true);
        return rf;
    }

    private static void checkMostImportantIsMostImportant(double[] importances, int mostImportant) {
        double mostImportantImportance = importances[mostImportant];
        for (double importance : importances)
            Assert.assertTrue(mostImportantImportance >= importance);
    }

    @Test
    public void testVariableImportancesSameAsBreiman() throws Exception {
        //Breiman's paper, vote
        checkMostImportantIsMostImportant(importances(frf(), vote()), 3);
        //Breiman's paper, diabetes
        checkMostImportantIsMostImportant(importances(frf(), diabetes()), 1);
    }

    // Regression is not implemented yet!
    /*
    @Test
    public void testVariableImportancesRegression() throws Exception {
        double[] importances = importances(frf(), cal_housing());
        System.out.println(Arrays.toString(importances));
    }
    */
}
