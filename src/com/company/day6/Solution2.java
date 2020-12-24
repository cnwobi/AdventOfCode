package com.company.day6;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution2 {
    private static Long parseInput() {
        URL path = Solution2.class.getResource("input.txt");
        try (Stream<String> inputStream = Files.lines(Path.of(path.getFile()))) {
            return Arrays.stream(inputStream
                    .map(Solution2::addDelimiters)
                    .reduce("", String::concat)
                    .split("\\|"))
                    .map(Solution2::countQuestionsAnsweredByAllInEachGroup)
                    .reduce(0L, Long::sum);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the input file");
        }
    }

    private static String addDelimiters(String line) {
        // join all the input seperating groups by | and answers with a group by %
        String groupDelimiters = "|";
        String individualDelimiters = "%";
        return line.isBlank() ? groupDelimiters : line + individualDelimiters;
    }

    private static long countQuestionsAnsweredByAllInEachGroup(String group) {
        long membersInGroup = group.chars().filter(c -> c == '%').count();
        return Arrays.stream(group.split("%"))
                .flatMap(answer -> answer.chars().mapToObj(e -> (char) e))
                .collect(Collectors.toMap(c -> c, c -> 1L, Long::sum))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == membersInGroup)
                .map(Map.Entry::getKey)
                .count();
    }

    public static void main(String[] args) {
        System.out.println(parseInput());
    }


}
