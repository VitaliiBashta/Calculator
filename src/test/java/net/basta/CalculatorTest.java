package net.basta;


import net.basta.common.Util;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class CalculatorTest {

    private Calculator calculator;
    private List<List<String>> testSamples;

    @Before
    public void setUp() {
        calculator = new Calculator();
        this.testSamples = Util.getResourceSamples("testSamples");
    }

    @Test(expected = NullPointerException.class)
    public void nullInputCalculatorTest() {
        BigDecimal result = calculator.calculate(null);
        assertEquals(new BigDecimal(0), result);
    }

    @Test
    public void allSamplesCalculatorTest() {
        for (List<String> testSample : testSamples) {
            BigDecimal expectedResult = new BigDecimal(testSample.get(0));

            Path tempFile = Util.writeSampleToFile(testSample);
            BigDecimal result = calculator.calculate(tempFile.toString());
            assertEquals("Evaluation of \n" + testSample + "\n failed", expectedResult, result);
        }

    }




}
