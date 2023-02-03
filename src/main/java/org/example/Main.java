package org.example;

import java.util.Map;
import java.util.Scanner;

import static java.util.Map.entry;

class CalculatorException extends RuntimeException{
    public CalculatorException(String message) {
        super(message);
    }
}

public class Main {

    private static final Map<String, Integer> romeSymbolsMap = Map.ofEntries(
            entry("I", 1),
            entry("II", 2),
            entry("III", 3),
            entry("IV", 4),
            entry("V", 5),
            entry("VI", 6),
            entry("VII", 7),
            entry("VIII", 8),
            entry("IX", 9),
            entry("X", 10),
            entry("XI", 11),
            entry("XII", 12),
            entry("XIII", 13),
            entry("XIV", 14),
            entry("XV", 15),
            entry("XVI", 16),
            entry("XVII", 17),
            entry("XVIII", 18),
            entry("XIX", 19),
            entry("XX", 20)
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String value = calc(scanner.nextLine());
        System.out.println(value);
    }

    public static String calc(String input){
        String[] values = input.split(" ");
        if (values.length != 3){
            throw new CalculatorException("Unsupported format");
        }

        String first, second;
        first = values[0];
        second = values[2];

        if (validateArabNumber(first) && validateArabNumber(second)){
            return String.valueOf(
                    makeCalculation(Integer.parseInt(first), Integer.parseInt(second), values[1])
            );
        }

        if (validateRomeNumber(first) && validateRomeNumber(second)){

            int value = makeCalculation(romeSymbolsMap.get(first), romeSymbolsMap.get(second), values[1]);
            if (value < 1){
                throw new CalculatorException("Wrong number in Rome");
            }
            return romeSymbolsMap.entrySet().stream().filter(x -> x.getValue().equals(value)).findFirst().get().getKey();
        }

        throw new CalculatorException("Unsupported numbers");

    }

    public static boolean validateRomeNumber(String number){
        if (romeSymbolsMap.containsKey(number)){
            int num = romeSymbolsMap.get(number);
            return num >= 1 && num <= 10;
        }
        return false;
    }

    public static boolean validateArabNumber(String number){
        try {
            int num = Integer.parseInt(number);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static int makeCalculation(int a, int b, String operation){
        return switch (operation) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new CalculatorException("Unsupported operation");
        };
    }
}