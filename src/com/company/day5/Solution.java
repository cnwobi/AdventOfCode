package com.company.day5;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {

    public static void main(String[] args) {
        int part1answer = findHighestSeatId();
        int part2answer =  findMissingSeat();
        System.out.println(part1answer);
        System.out.println(part2answer);
    }

    private static Path getPath() {
     return Path.of(Solution.class.getResource("input.txt").getFile());
    }

    private static int findHighestSeatId() {
        try (Stream<String> inputStream = Files.lines(getPath())) {
            Optional<Integer> maximum = inputStream
                    .map(Solution::getSeatId)
                    .reduce(Integer::max);
            return maximum.orElse(-1);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the input file");
        }
    }

    private static int findMissingSeat() {
        try (Stream<String> inputStream = Files.lines(getPath())) {
            List<Integer> seatIDs = inputStream
                    .map(Solution::getSeatId)
                    .sorted()
                    .collect(Collectors.toList());

            OptionalInt number = IntStream.range(1,seatIDs.size()-1)
                    .filter(i -> seatIDs.get(i + 1) - seatIDs.get(i) == 2 )
                    .map(i -> seatIDs.get(i)+1)
                    .findFirst();
            return number.getAsInt();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the input file"); }
    }

    private static int getSeatId(String line) {
        String regex = "([BF]+)([RL]+)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        RowSeat rowSeat = m.results()
                .map(Solution::getRowSeat)
                .findFirst()
                .orElseThrow(Solution::throwInvalidSeatException );
        return calculateSeatId(rowSeat);
    }
    private static int calculateSeatId(RowSeat rowSeat) {
        return getRowNumber(rowSeat.getRowCode())* 8 + getColumnNumber(rowSeat.getColumnCode());
    }
    private static RowSeat getRowSeat (MatchResult matchResult) {
        return new RowSeat(matchResult.group(1), matchResult.group(2));
    }

    private static RuntimeException throwInvalidSeatException() {
        return new RuntimeException("Invalid Seat Code");
    }


    private static int getRowNumber(String rowCode) {
        return getRowNumber(rowCode, 0, 127);
    }

    private static int getRowNumber(String row, int startRow, int endRow) {
        if (row.length() == 1) {
            return row.charAt(0) == 'F' ? startRow : endRow;
        }
        int mid = ((endRow - startRow) / 2) + 1;
        return row.charAt(0) == 'F' ? getRowNumber(row.substring(1), startRow, endRow - mid)
                : getRowNumber(row.substring(1), startRow + mid, endRow);
    }

    private static int getColumnNumber(String oolumnCode) {
        return getColumnNumber(oolumnCode, 0, 7);
    }

    private static int getColumnNumber(String code, int start, int end) {
        if (code.length() == 1) {
            return code.charAt(0) == 'R' ? end : start;
        }
        int mid = ((end - start) / 2) + 1;
        return code.charAt(0) == 'R' ? getColumnNumber(code.substring(1), start + mid, end)
                : getColumnNumber(code.substring(1), start, end - mid);
    }

    private static class RowSeat {
      private final String rowCode;
      private final String columnCode;
      public RowSeat(String rowCode, String columnCode) {
            this.rowCode = rowCode;
            this.columnCode = columnCode;
        }
        public String getRowCode() {
            return rowCode;
        }
        public String getColumnCode() {
            return columnCode;
        }
    }
}


