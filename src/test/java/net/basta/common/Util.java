package net.basta.common;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

    public static List<List<String>> getResourceSamples(String path) {
        String folder = Objects.requireNonNull(Util.class.getClassLoader().getResource(path)).getFile();
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

    public static Path writeSampleToFile(List<String> testSampleWithResult) {
        try {
            //first item is expected result, so skip it
            String evaluationString = testSampleWithResult.stream()
                    .skip(1)
                    .collect(Collectors.joining(System.lineSeparator()));

            Path tempFile = Files.createTempFile("calculate-", "-sample");
            OutputStream outputStream = Files.newOutputStream(tempFile, StandardOpenOption.DELETE_ON_CLOSE);
            outputStream.write(evaluationString.getBytes());
            return tempFile;
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't save file ", e);
        }
    }
}
