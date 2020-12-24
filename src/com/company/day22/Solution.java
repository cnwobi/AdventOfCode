package com.company.day22;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Solution {
    public static void main(String[] args) {
      parseInput("input.txt");
    }

    private static void parseInput(String inputPath){
        URL path = Solution.class.getResource(inputPath);
        Queue<Integer> player1 = new LinkedList<>();
        Queue<Integer> player2 = new LinkedList<>();
        AtomicReference<Queue<Integer>> queueToFill = new AtomicReference<>(player1);

        try (Stream<String> inputStream = Files.lines(Path.of(path.getFile()))) {
            inputStream.forEach(line -> {
                String regex = "\\d+";
                if(line.matches(regex)) {
                    queueToFill.get().add(Integer.parseInt(line));
                }
                if(line.isBlank()){
                    queueToFill.set(player2);
                }
            });
            while (!player1.isEmpty() && !player2.isEmpty()){
                int player1Card = player1.remove();
                int player2Card = player2.remove();

                if(player1Card > player2Card) {
                    player1.add(player1Card);
                    player1.add(player2Card);
                }else{
                    player2.add(player2Card);
                    player2.add(player1Card);
                }
            }
            Queue<Integer> toCalculateResult = !player1.isEmpty() ? player1 : player2;
            int sum = 0;
            for(int i = toCalculateResult.size() ; i > 0; i--){
                sum += toCalculateResult.remove() * i;
            }
            System.out.println(sum);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the input file");
        }
    }
}
