package net.basta.internal;

import java.math.BigDecimal;
import java.math.MathContext;

public class Command {
    private final String operation;
    private final BigDecimal value;
    private final MathContext mathContext;

    public Command(String operation, BigDecimal value, int dividePrecision) {
        this.operation = operation;
        this.value = value;
        this.mathContext = new MathContext(dividePrecision);
    }

    public Command(String operation, BigDecimal value) {
        this(operation, value, 100);
    }

    public BigDecimal apply(BigDecimal leftOperand) {
        return switch (operation) {
            case "add" -> leftOperand.add(value);
            case "subtract" -> leftOperand.subtract(value);
            case "multiply" -> leftOperand.multiply(value);
            case "divide" -> leftOperand.divide(value, mathContext);
            case "pow" -> leftOperand.pow(value.intValue(), mathContext);
            case "apply" -> value;
            default -> throw new IllegalArgumentException("Unknown operation: " + operation);
        };
    }
}
