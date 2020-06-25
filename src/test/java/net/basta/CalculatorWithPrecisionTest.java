package net.basta;


import net.basta.common.Util;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;


public class CalculatorWithPrecisionTest {

    private Calculator calculator;
    private List<List<String>> testSamples;

    @Before
    public void setUp() {
        calculator = new Calculator(5);
        this.testSamples = Util.getResourceSamples("testSamplesSmallPrecision");
    }

    @Test
    public void SamplesSmallPrecisionCalculatorTest() {
        for (List<String> testSample : testSamples) {
            try {
                BigDecimal expectedResult = new BigDecimal(testSample.get(0));

                String evaluationString = testSample.stream()
                        .skip(1)
                        .collect(Collectors.joining(System.lineSeparator()));

                File tempFile = File.createTempFile("calculate-", "-sample");
                Files.write(tempFile.toPath(), evaluationString.getBytes());
                BigDecimal result =  calculator.calculate(tempFile.toString());
                tempFile.deleteOnExit();
                assertEquals("Evaluation of \n"+ evaluationString+ "\n failed", expectedResult, result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
