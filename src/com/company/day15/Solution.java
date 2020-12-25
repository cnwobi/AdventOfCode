package com.company.day15;

import java.util.*;

public class Solution {

public static int getDiffLastTwoSpoken(List<Integer> turnsSpoken){
   return turnsSpoken.get(turnsSpoken.size() - 1) - turnsSpoken.get(turnsSpoken.size() - 2);
}
    public static void main(String[] args) {
        int[] startingNumbers = {1, 3, 2};
        int turns = 2020;
        getSpokenGiveStartingNumbersAndTurns(turns, startingNumbers);
    }
    private static int getSpokenStartingNumbers(Map<Integer, List<Integer>> map, int[] startNumbers, int spoken) {
        for (int i = 0; i < startNumbers.length; i++) {
            spoken = startNumbers[i];
            map.put(spoken, map.getOrDefault(spoken,
                    new ArrayList<>(Collections.singleton(i+1)))); }
        return spoken;
    }

    private static int getSpoken(Map<Integer, List<Integer>> map, int turnAfterStarting, int spoken, int turn) {
        for (int i = turnAfterStarting; i <= turn; i++) {
            List<Integer> timesSpoken = map.getOrDefault(spoken, new ArrayList<>());
            spoken = timesSpoken.size() == 1 ? 0 : getDiffLastTwoSpoken(timesSpoken);
            if (!map.containsKey(spoken)) {
                map.put(spoken, new ArrayList<>());
            }
            map.get(spoken).add(i);
        }
        return spoken;
    }

    private static String stringArray(int[] arr){
        return   Arrays.stream(arr)
                .mapToObj(i -> i+",")
                .reduce("",String::concat);
    }

    private static void getSpokenGiveStartingNumbersAndTurns(int turns, int[]startNumbers) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        var spoken = 0;

        spoken = getSpokenStartingNumbers(map, startNumbers, spoken);
        long start = System.currentTimeMillis();
        spoken = getSpoken(map, startNumbers.length + 1, spoken, turns);
        long end = System.currentTimeMillis();

        System.out.printf("\n\nRan in %s ms\n", (end - start) );
        System.out.printf("Given %s the %sth number spoken is %s.", stringArray(startNumbers), turns, spoken);
    }

}
