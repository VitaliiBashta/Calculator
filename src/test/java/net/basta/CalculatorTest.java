package net.basta;


import net.basta.common.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CalculatorTest {

    private Calculator calculator;
    private List<List<String>> testSamples;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
        this.testSamples = Util.getResourceSamples("testSamples");
    }

    @Test
    void nullInputCalculatorTest() {
        assertThrows(NullPointerException.class, () -> calculator.calculate(null));
    }

    @Test
    void allSamplesCalculatorTest() {
        for (List<String> testSample : testSamples) {
            String expectedString = testSample.get(0);
            BigDecimal expectedResult = expectedString.equals("null") ? null : new BigDecimal(expectedString);

            Path tempFile = Util.writeSampleToFile(testSample);
            BigDecimal result = calculator.calculate(tempFile.toString());
            assertEquals(Objects.requireNonNull(expectedResult).intValueExact(), result.intValueExact());
        }

    }
}
