package computerArchitecture.lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.Math.pow;

public class Functions {

    // ДЕСЯТКОВА СИСТЕМА В ДВІЙКОВУ
    public static void decToBin(String number) {
        int[][] parts = splitNumber(number);
        int[] wholeDigits = parts[0];
        int[] fracDigits = parts[1];

        System.out.println("\n\nПОЧАТОК РОБОТИ 10 -> 2\n");

        int wholeNumber = 0;
        for (int digit : wholeDigits) {
            wholeNumber = wholeNumber * 10 + digit;
        }

        StringBuilder binResult = new StringBuilder();
        int step1 = 1;
        System.out.println("Переведення цілої частини:\n");
        while (wholeNumber > 0) {
            int temp = wholeNumber % 2;
            System.out.printf("Крок %d: %d %% 2 = %d\n", step1, wholeNumber, temp);
            binResult.append(temp);
            wholeNumber /= 2;
            System.out.printf("Next number: %d\n\n", wholeNumber);
            step1++;
        }

        double fracNumber = 0;
        double factor = 0.1;
        for (int digit : fracDigits) {
            fracNumber += digit * factor;
            factor /= 10;
        }

        StringBuilder fracResult = new StringBuilder();
        int step2 = 1;
        for (int i = 0; i < 4; i++) {
            double temp = fracNumber * 2;
            int bit = (temp >= 1) ? 1 : 0;
            fracResult.append(bit);
            if (bit == 1) temp -= 1;
            System.out.printf("Крок %d: %.4f*2=%.4f bit=%d%n%n", step2, fracNumber, temp, bit);
            fracNumber = temp;
            step2++;
        }

        System.out.println("FINAL RESULT: " + binResult.reverse() + "." + fracResult);
    }

    // ДВІЙКОВА СИСТЕМА В ДЕСЯТКОВУ
    public static void binToDec(String number) {
        int[][] parts = splitNumber(number);
        int[] wholeDigits = parts[0];
        int[] fracDigits = parts[1];

        System.out.println("\n\nПОЧАТОК РОБОТИ 2 -> 10\n");

        int wholeNumber = 0;
        int wholeLenght = wholeDigits.length;

        System.out.println("ПЕРЕВЕДЕННЯ ЦІЛОЇ ЧАСТИНИ:\n");
        for(int i =0; i < wholeLenght; i++) {
            wholeNumber = (int) (wholeNumber + wholeDigits[i]*pow(2,wholeLenght-i-1));
            System.out.printf("Крок %d: %d*2^%d. Сума: %d%n",  i+1, wholeDigits[i], wholeLenght-i-1, wholeNumber);
        }
        System.out.println("\nЦІЛА ЧАСТИНА = "+wholeNumber);

        System.out.println("\nПЕРЕВЕДЕННЯ ДРОБОВОЇ ЧАСТИНИ:");
        double fracNumber = 0;
        int fracLenght = fracDigits.length;
        for (int i = 0; i < fracLenght; i++) {
            fracNumber += fracDigits[i] * pow(2, -(i + 1));
            System.out.printf("Крок %d: %d*2-%d. Сума: %f%n",  i+1, fracDigits[i], fracLenght-i-1, fracNumber);
        }
        System.out.println("\nДРОБОВА ЧАСТИНА = "+fracNumber);

        System.out.println("\nВАШЕ ЧИСЛО В ДЕСЯТКОВІЙ СИСТЕМІ: "+(wholeNumber+fracNumber));

    }

    //ДВІЙКОВА В ВІСІМКОВУ
    public static void binToOct(String number) {
        int[][] parts = splitNumber(number);
        int[] wholeDigits = parts[0];
        int[] fracDigits = parts[1];
        ArrayList<Integer> wholeNumbers = new ArrayList<>();
        ArrayList<Integer> fracNumbers = new ArrayList<>();
        ArrayList<Integer> group = new ArrayList<>();
        int count=0;
        int value=0;

        for( int i = wholeDigits.length - 1; i >= 0; i--) {
            group.add(wholeDigits[i]);
            count ++;

            if(count==3) {
                Collections.reverse(group);
                value = group.get(0) * 4 + group.get(1) * 2 + group.get(2);
                count = 0;
                group.clear();
                wholeNumbers.add(0, value);
            }
        }
        if (!group.isEmpty()) {
            while (group.size() < 3) {
                group.add(0, 0);
            }
            value = group.get(0)*4 + group.get(1)*2 + group.get(2);
            wholeNumbers.add(0, value);
        }

        StringBuilder wholeResult = new StringBuilder();
        for(int digit: wholeNumbers) {
            wholeResult.append(digit);
        }

        System.out.println("RESULT: " +  wholeResult);
    }


    public static int[][] splitNumber(String number) {
        String[] parts = number.split("\\.");

        String wholeStr = parts[0];
        int[] wholeDigits = new int[wholeStr.length()];
        for (int i = 0; i < wholeStr.length(); i++) {
            wholeDigits[i] = wholeStr.charAt(i) - '0';
        }

        int[] fracDigits = new int[0];
        if (parts.length > 1) {
            String fracStr = parts[1];
            fracDigits = new int[fracStr.length()];
            for (int i = 0; i < fracStr.length(); i++) {
                fracDigits[i] = fracStr.charAt(i) - '0';
            }
        }

        return new int[][]{wholeDigits, fracDigits};
    }


    public static String enterValue(Scanner in){
        System.out.println("Enter your number: ");
        String number = in.nextLine();
        return number;
    }

    public static int enterVariant(Scanner in){
        System.out.println("Enter your variant: ");
        int variant = in.nextInt();
        in.nextLine();
        return variant;
    }

    public static void printVariants(){
        System.out.println("""
                
                Enter the variant
                1: 10 -> 2
                2: 2 -> 10
                3: 2 -> 8 -> 10
                4: 10 -> 8 -> 2
                5: 10 -> 16 -> 2
                6: 2 -> 16 -> 10
                """);
    }
}
