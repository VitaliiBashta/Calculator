package net.basta;

import net.basta.internal.Command;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calculator {
    private static final Logger LOG = Logger.getLogger(Calculator.class.getName());
    private final int dividePrecision;

    public Calculator(int dividePrecision) {
        this.dividePrecision = dividePrecision;
    }

    public Calculator() {
        this(100);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            LOG.log(Level.SEVERE, "Command usage:  java Calculator <filename of input data> [dividePrecision]");
            return;
        }
        String inputFilename = args[0];
        int dividePrecision = 100;
        if (args.length == 2) {
            dividePrecision = Integer.parseInt(args[1]);
        }
        LOG.info("Reading input data from" + inputFilename);
        Calculator calculator = new Calculator(dividePrecision);
        if (Files.exists(Paths.get(inputFilename))) {
            System.out.println(calculator.calculate(inputFilename));

        } else {
            LOG.log(Level.SEVERE, "File not found: " + inputFilename);
        }
    }

    public BigDecimal calculate(String fileName)  {
        Stream<String> inputLines;
        try {
            inputLines = Files.lines(Paths.get(fileName));
        } catch (IOException e) {
            throw new IllegalArgumentException("File " + fileName + "not found");
        }
        List<Command> commands = inputLines
                .map(this::parseCommand)
                .collect(Collectors.toList());
        List<Command> commandsOrdered = moveApplyCommandToFirstOperation(commands);
        BigDecimal result = new BigDecimal(0);
        for (Command command : commandsOrdered) {
            result = command.apply(result);
        }
        return result;
    }

    private Command parseCommand(String line) {
        String[] pair = line.split(" ");
        BigDecimal value;
        try {
            value = new BigDecimal(pair[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal input", e);
        }
        return new Command(pair[0], value, dividePrecision);
    }

    private List<Command> moveApplyCommandToFirstOperation(List<Command> commands) {
        List<Command> result = new ArrayList<>(commands);
        if (!result.isEmpty()) {
            Command lastItem = result.remove(result.size() - 1);
            result.add(0, lastItem);
        }
        return result;
    }
}
