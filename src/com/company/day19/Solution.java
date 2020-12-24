package com.company.day19;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {
    public static void main(String[] args) {
        parseInput("input.txt");
    }

    private static void parseInput(String inputPath){
        Map<String,String[]> rules = new HashMap<>();
        URL path = Solution.class.getResource(inputPath);
        try (Stream<String> inputStream = Files.lines(Path.of(path.getFile()))) {
            inputStream.forEach(line -> {
               if(line.contains(":")){
                   var split = line.split(": ");
                   var key = split[0].trim();
                   var rules2 = split[1].split("\\|");
                   System.out.printf("key:%s    Rules:%s\n",key,Arrays.toString(rules2));
                   matchRules(key,rules);
               }
                 else{
                //   System.out.println(line);
               }
            });
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the input file");
        }
    }

    private static void matchRules(String line, Map<String,String[]> rules){

    }
}
