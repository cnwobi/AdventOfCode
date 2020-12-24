package com.company.day2;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Solution {
    public static boolean isValid (String input) {
        String[] parsedInput = parseInput(input);
        int lowerBound = Integer.parseInt(parsedInput[0]);
        int upperBound = Integer.parseInt(parsedInput[1]);
        String targetCharacter = parsedInput[2];
        String password = parsedInput[3];
        long occurrence = password.chars().filter(c -> c == targetCharacter.charAt(0)).count();
        return occurrence>=lowerBound && occurrence <= upperBound;
    }

    public static String[] parseInput (String input){
        String regex = "[\\-: ]+";
        String[] parsedInput = input.split(regex);
        return new String[]{parsedInput[0],parsedInput[1],parsedInput[2],parsedInput[3]};
    }

    public static  boolean isValid2 (String input) {
        String[] parsedInput = Solution.parseInput(input);
        int lowerBound = Integer.parseInt(parsedInput[0]);
        int upperBound = Integer.parseInt(parsedInput[1]);
        String target = parsedInput[2];
        char targetCharacter = target.charAt(0);
        String password = parsedInput[3];
        boolean charAtLowerBoundEqualsTarget = password.charAt(upperBound - 1) == targetCharacter;
        boolean charAtUpperBoundEqualsTarget = password.charAt(lowerBound - 1) == targetCharacter;
        boolean indexOutBounds = lowerBound - 1 >= password.length() || upperBound - 1 >= password.length();
        boolean exactlyOneCharacterEqualsTarget = charAtLowerBoundEqualsTarget ^ charAtUpperBoundEqualsTarget;
        return !indexOutBounds && exactlyOneCharacterEqualsTarget;
    }

    public static long countValidPasswords2(String inputPath) {
        URL path = Solution.class.getResource(inputPath);
        long validPasswords;
        try(Stream<String> inputStream = Files.lines(Path.of(path.getFile()))){
            validPasswords = inputStream
                    .filter(Solution::isValid2)
                    .count();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while reading the input file");
        }
        return validPasswords;
    }

    public static long countValidPasswords(String inputPath) {
        URL path = Solution.class.getResource(inputPath);
        long validPasswords;
        try(Stream<String> inputStream = Files.lines(Path.of(path.getFile()))){
            validPasswords = inputStream
                    .filter(Solution::isValid)
                    .count();
        }
        catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the input file");
        }
        return validPasswords;
    }

    public static void main(String[] args) {
        System.out.println(countValidPasswords("input.txt"));
        System.out.println(countValidPasswords2("input.txt"));
    }
}
