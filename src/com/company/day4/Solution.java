package com.company.day4;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    public static long countDocumentsWithRequiredFields(String inputPath) {
        URL path = Solution.class.getResource(inputPath);
        try (Stream<String> inputStream = Files.lines(Path.of(path.getFile()))) {
            return Arrays.stream(inputStream.map(Solution::addDelimeters)
                    .reduce("", String::concat)
                    .split("\\|"))
                    .map(Solution::parsePassportAsMap)
                    .filter(Solution::containsRequiredField)
                    .count();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the input file");
        }
    }

    public static long countValidPassports(String inputPath) {
        URL path = Solution.class.getResource(inputPath);
        try (Stream<String> inputStream = Files.lines(Path.of(path.getFile()))) {
            return Arrays.stream(inputStream.map(Solution::addDelimeters)
                    .reduce("", String::concat)
                    .split("\\|"))
                    .map(Solution::parsePassportAsMap)
                    .filter(Solution::containsRequiredField)
                    .filter(Solution::allFieldsValid)
                    .count();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the input file");
        }
    }

    private static boolean containsRequiredField(Map<String, String> passport) {
        Set<String> requiredFields = new HashSet<>(Arrays.asList(
                "byr",
                "iyr",
                "eyr",
                "hgt",
                "hcl",
                "ecl",
                "pid"));
        Set<String> document = passport.keySet();
        return document.containsAll(requiredFields);
    }

    private static String addDelimeters(String line) {
        return line.isBlank() ? "|" : line + " ";
    }

    private static Map<String, String> parsePassportAsMap(String passport) {
        String regex = "\\b(\\w{3}):([a-z0-9#]+)\\b";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(passport);
        return m.results()
                .collect(Collectors.toMap(matchResult -> matchResult.group(1), matchResult -> matchResult.group(2)));
    }

    public static boolean allFieldsValid(Map<String, String> passport) {
        return hasValidHcl(passport) && hasValidByr(passport) && hasValidEyr(passport)
                && hasValidHgt(passport) && hasValidEyeColor(passport)
                && hasValidPid(passport) && hasValidIyr(passport);
    }

    private static boolean hasValidByr(Map<String, String> passport) {
        return isBetweenBounds(passport, "byr", 1920, 2002);
    }

    private static boolean hasValidIyr(Map<String, String> passport) {
        return isBetweenBounds(passport, "iyr", 2010, 2020);
    }

    private static boolean hasValidEyr(Map<String, String> passport) {
        return isBetweenBounds(passport, "eyr", 2020, 2030);
    }

    private static boolean isBetweenBounds(Map<String, String> passport, String key, int lowerLimit, int upperLimit) {
        int value = Integer.parseInt(passport.get(key));
        return value >= lowerLimit && value <= upperLimit;
    }


    private static boolean hasValidHgt(Map<String, String> passport) {
        String height = passport.get("hgt");
        String regex = "([0-9]+)(in|cm)$";

        if (!height.matches(regex)) return false;

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(height);

        Height parsedHeight = m.results()
                .map(Solution::mapMatchToHeight)
                .findFirst()
                .orElseThrow(Solution::invalidHeightException);
        int value = parsedHeight.getUnitValue();
        return parsedHeight.getUnit().equalsIgnoreCase("in") ? isValidInches(value) : isValidCm(value);
    }

    private static RuntimeException invalidHeightException(){
        return new RuntimeException("Invalid Height");
    }
    private static Height mapMatchToHeight(MatchResult matchResult){
        return new Height(matchResult.group(2),Integer.parseInt(matchResult.group(1)));
    }
    private static boolean hasValidHcl(Map<String, String> passport) {
        String regex = "#([0-9a-f]{6})";
        return passport.get("hcl").matches(regex);
    }

    public static boolean isValidCm(int value) {
        return value >= 150 && value <= 193;
    }

    public static boolean isValidInches(int value) {
        return value >= 59 && value <= 76;
    }

    public static boolean hasValidEyeColor(Map<String, String> passport) {
        Set<String> eyeColors = new HashSet<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));
        return eyeColors.contains(passport.get("ecl"));
    }

    private static boolean hasValidPid(Map<String, String> passport) {
        return passport.get("pid").matches("\\d{9}");
    }

    public static void main(String[] args) {
        System.out.println(countDocumentsWithRequiredFields("input.txt"));
        System.out.println(countValidPassports("input.txt"));
    }

}
