package net.basta;


import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;


public class CalculatorTest {

    private Calculator calculator;
    private List<List<String>> testSamples;

    @Before
    public void setUp() {
        calculator = new Calculator();
        this.testSamples = this.getResourceSamples();
    }

    @Test
    public void allSamplesCalculatorTest() {
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


    @Test(expected = NullPointerException.class)
    public void nullInputCalculatorTest() {
        BigDecimal result = calculator.calculate(null);
        assertEquals(new BigDecimal(0), result);
    }


    private List<List<String>> getResourceSamples() {
        String folder = Objects.requireNonNull(getClass().getClassLoader().getResource("testSamples")).getFile();
        return Stream.of(Objects.requireNonNull(new File(folder).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::toPath)
                .map(path1 -> {
                    try {
                        return Files.lines(path1).collect(Collectors.toList());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new ArrayList<String>();
                })
                .collect(Collectors.toList());
    }


}
