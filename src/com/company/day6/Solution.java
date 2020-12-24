package com.company.day6;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {
    private static int parseInput(String inputPath){
        URL path = Solution.class.getResource(inputPath);
        try (Stream<String> inputStream = Files.lines(Path.of(path.getFile()))) {
            return Arrays.stream(inputStream.map(Solution::replaceBlankLineWithPipe)
                        .reduce("",String::concat)
                        .split("\\|"))
                        .map(Solution::countUniqueCharacters)
                        .reduce(0,Integer::sum);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the input file");
        }
    }
    private static String replaceBlankLineWithPipe(String line) {
        return line.isBlank()? "|" : line;
    }
    private static int countUniqueCharacters(String answer) {
        return answer.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()).size();
    }
    public static void main(String[] args) {
        System.out.println( parseInput("input.txt"));
    }


}
