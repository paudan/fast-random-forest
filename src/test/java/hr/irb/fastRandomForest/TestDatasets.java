package hr.irb.fastRandomForest.data;

import weka.core.Instances;

import java.io.InputStream;
import weka.core.converters.ConverterUtils;

/**
 * Some utility methods for loading the testing datasets.
 *
 * @author Santi Villalba
 * @version $Id: TestDatasets.java 41 2009-06-24 10:35:50Z sdvillal $
 */
public class TestDatasets {

    private final static String DIABETES = "hr/irb/fastRandomForest/data/diabetes.arff";
    private final static String VOTE = "hr/irb/fastRandomForest/data/vote.arff";

    private static Instances _diabetes;
    private static Instances _vote;

    private TestDatasets() {}

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

    private static Instances loadData(String location) throws Exception {
         ClassLoader classLoader = TestDatasets.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(location);
        Instances data = new ConverterUtils.DataSource(is).getDataSet();
        if (-1 == data.classIndex())
            data.setClassIndex(data.numAttributes() - 1);

        return data;
    }
}
