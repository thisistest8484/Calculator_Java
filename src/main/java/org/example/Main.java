package org.example;

import java.util.*;
import java.util.stream.Collectors;

class CalculatorException extends RuntimeException{
    public CalculatorException(String message) {
        super(message);
    }
}

enum RomeSymbol{
    I(1),
    IV(4),
    V(5),
    IX(9),
    X(10),
    XL(40),
    L(50),
    XC(90),
    C(100);

    private final int value;
    RomeSymbol(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List<RomeSymbol> getAsReversedList(){
        return Arrays.stream(RomeSymbol.values())
                .sorted(Comparator.comparingInt(RomeSymbol::getValue).reversed())
                .collect(Collectors.toList());
    }
}

class Converter {
    public static String arabToRome(int number){
        List<RomeSymbol> romeSymbols = RomeSymbol.getAsReversedList();
        RomeSymbol symbol = romeSymbols.get(0);
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (number > 0){
            if (symbol.getValue() <= number){
                result.append(symbol);
                number -= symbol.getValue();
            } else {
                i++;
                symbol = romeSymbols.get(i);
            }
        }
        return result.toString();
    }
}

public class Main {

    private static Map<String, Integer> romeSymbolsMap;

    public static void main(String[] args) {
        romeSymbolsMap = new HashMap<>();
        for (int i = 1; i <= 100; i++){
            romeSymbolsMap.put(Converter.arabToRome(i), i);
        }
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