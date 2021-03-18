package net.basta.internal;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;

public class Command implements Function<BigDecimal, BigDecimal> {
    private final String operation;
    private final BigDecimal value;
    private final MathContext mathContext;

    private Command(String operation, BigDecimal value, int dividePrecision) {
        this.operation = operation;
        this.value = value;
        this.mathContext = new MathContext(dividePrecision);
    }

    public static Command of(String line, int dividePrecision) {
        String[] pair = line.split(" ");
        String operation = pair[0];
        BigDecimal value = new BigDecimal(pair[1]);
        return new Command(operation, value, dividePrecision);
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal apply(BigDecimal leftOperand) {
        return switch (operation) {
            case "add" -> leftOperand.add(value);
            case "subtract" -> leftOperand.subtract(value);
            case "multiply" -> leftOperand.multiply(value);
            case "divide" -> leftOperand.divide(value, mathContext);
            case "pow" -> leftOperand.pow(value.intValue(), mathContext);
            case "apply" -> leftOperand;
            default -> throw new IllegalArgumentException("Unknown operation: " + operation);
        };
    }
}
