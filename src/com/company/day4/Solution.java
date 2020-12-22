package com.company.day4;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Solution {

    public static long readLines (String inputPath) {
        URL path = Solution.class.getResource(inputPath);
        long validPasswords = 0;
        Set<String> holder = new HashSet<>();
        Map<String, String> map = new HashMap<>();
        AtomicInteger valid = new AtomicInteger();
       Set <String> validSet =  new HashSet<>(Arrays.asList(
               "byr",
                "iyr",
                "eyr",
                "hgt",
                "hcl",
                "ecl",
                "pid"));
/*
*
* byr (Birth Year)
iyr (Issue Year)
eyr (Expiration Year)
hgt (Height)
hcl (Hair Color)
ecl (Eye Color)
pid (Passport ID)
cid (Country ID)*/
        try(Stream<String> inputStream = Files.lines(Path.of(path.getFile()))){
            inputStream.forEach(line -> {
                if(!line.isBlank()){
                    Arrays.stream(line.split("\\s+")).forEach(value -> {
                        String[] entry = value.split(":");
                        map.put(entry[0],entry[1]);
                    });
//                    Arrays.stream(result)
//                            .map(String::strip)
//                            .forEach(holder::add);
                }
                else{
                    incrementIfValid(map, valid, validSet);
                    map.clear();
                }

            });
            incrementIfValid(map, valid, validSet);
            System.out.println(valid);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while reading the input file");
        }
        return validPasswords;
    }

    private static void incrementIfValid(Map<String, String> map, AtomicInteger valid, Set<String> validSet) {
        if (map.keySet().containsAll(validSet)) {

            valid.getAndIncrement();
        }
    }
    private static boolean isValidByr(Map<String, String> passport){
        return isValidNumericalValue(passport,"byr", 1920, 2002);
    }
    private static boolean isValidIyr(Map<String,String> passport){
        return isValidNumericalValue(passport,"iyr",2010, 2020);
    }


    private static boolean isValidNumericalValue(Map<String, String> passport, String key, int lowerLimit, int upperLimit){
        int byr =  Integer.parseInt(passport.get(key));
        return byr >= lowerLimit && byr <= upperLimit;
    }
    /*byr (Birth Year) - four digits; at least 1920 and at most 2002.
iyr (Issue Year) - four digits; at least 2010 and at most 2020.
eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
hgt (Height) - a number followed by either cm or in:
If cm, the number must be at least 150 and at most 193.
If in, the number must be at least 59 and at most 76.
hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
pid (Passport ID) - a nine-digit number, including leading zeroes.
cid (Country ID) - ignored, missing or not.*/

    private static boolean isValidHgt(String passport){
        String regex = "([0-9]+)(in|cm)$";
        if(!passport.matches(regex)) return false;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(passport);
        String unit  = "";
        int value = 0;
        while (m.find()) {
            unit = m.group(2);
            value = Integer.parseInt(m.group(1));
        }
        return unit.equalsIgnoreCase("in") ? isValidInches(value) : isValidCm(value);
    }
    private static boolean isValidHcl(String color){
        String regex = "#([0-9a-f]{6})";
        return color.matches(regex);
    }
    public static boolean isValidCm(int value) {
        return value >= 150 && value <= 193;
    }
    public static boolean isValidInches(int value){
        return value >=59 && value <=76;
    }
    public static boolean isValidEyeColor(String eyeColor){
        Set<String> eyeColors = new HashSet<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl","oth"));
        return eyeColors.contains(eyeColor);
    }
    private static boolean isValidPid(String pid){
        return  pid.matches("\\d{9}");
    }
    public static void main(String[] args) {
        System.out.println(isValidEyeColor("blus"));
      //  readLines("input.txt");
    }
}
