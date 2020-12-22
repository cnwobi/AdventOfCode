//package com.company.day2;
//
//import java.io.IOException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.stream.Stream;
//
//public class Solution2 {
//    public static  boolean isValid (String input) {
//      String[] parsedInput = Solution.parseInput(input);
//        int lowerBound = Integer.parseInt(parsedInput[0]);
//        int upperBound = Integer.parseInt(parsedInput[1]);
//        String target = parsedInput[2];
//        char targetCharacter = target.charAt(0);
//        String password = parsedInput[3];
//        boolean charAtLowerBoundEqualsTarget = password.charAt(upperBound - 1) == targetCharacter;
//        boolean charAtUpperBoundEqualsTarget = password.charAt(lowerBound - 1) == targetCharacter;
//        boolean indexOutBounds = lowerBound - 1 >= password.length() || upperBound - 1 >= password.length();
//        boolean exactlyOneCharacterEqualsTarget = charAtLowerBoundEqualsTarget ^ charAtUpperBoundEqualsTarget;
//
//        return !indexOutBounds && exactlyOneCharacterEqualsTarget;
//
//    }
//    public static long countValidPasswords (String inputPath) {
//        URL path = Solution.class.getResource(inputPath);
//        long validPasswords = 0;
//        try(Stream<String> inputStream = Files.lines(Path.of(path.getFile()))){
//            validPasswords = inputStream
//                    .filter(Solution2::isValid)
//                    .count();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("An error occurred while reading the input file");
//        }
//        return validPasswords;
//    }
//    public static void main(String[] args) {
//    String test = "2-9 c: ccccccccc";
//
//        System.out.println(isValid(test));
//
//    }
//}
