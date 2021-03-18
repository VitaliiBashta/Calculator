package net.basta;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Main implements AutoCloseable {

    public static void main(String[] args) {

        try (
                final DirectoryStream<Path> paths = Files.newDirectoryStream(Path.of("c:/users"))) {

            StreamSupport.stream(paths.spliterator(), false)
                    .sorted()
                    .map(p -> p.getFileName().toString())
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }


        final List<Integer> sizes = IntStream.iterate(1, a -> a < 200, (int a) -> (int) ((a + 1) * 1.5))
                .boxed()
                .collect(toList());
        new WeakHashMap<>();
        var collect = sizes.stream()
                .map(Main::generateMap)
                .collect(toList());
//        final var collect1 = collect.stream()
//                .map(m -> Pair.of(m.size(), getCapacity(m.get(m.size()))))
//                .collect(toList());
//        System.out.println(collect1);
    }

    private static int getCapacity(Map<Integer, Integer> map) {
        final Class<?> aClass = map.getClass();
        final Method capacityMethods;
        try {
            capacityMethods = aClass.getDeclaredMethod("capacity");
            capacityMethods.setAccessible(true);
            final Object capacity = capacityMethods.invoke(map);
            return (int) capacity;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return 0;
        }
    }

    private static Map<Integer, List<Integer>> generateMap(int size) {
        return IntStream.range(0, size)
                .boxed()
                .collect(groupingBy(a -> a));
    }

    @Override
    public void close() throws Exception {

    }
}
