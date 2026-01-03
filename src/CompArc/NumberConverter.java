package CompArc;

import java.util.Scanner;

public class NumberConverter {

    // Перетворення дробового десяткового числа в іншу систему
    public static String decimalToBase(double number, int base) {
        long integerPart = (long) number;
        double fractionalPart = number - integerPart;

        // Частина для цілих чисел
        StringBuilder intResult = new StringBuilder();
        if (integerPart == 0) intResult = new StringBuilder("0");
        else {
            while (integerPart > 0) {
                long remainder = integerPart % base;
                intResult.insert(0, digitToChar(remainder));
                integerPart /= base;
            }
        }

        // Частина для дробових чисел
        StringBuilder fracResult = new StringBuilder();
        int precision = 10; // кількість знаків після коми
        while (fractionalPart > 0 && precision-- > 0) {
            fractionalPart *= base;
            int digit = (int) fractionalPart;
            fracResult.append(digitToChar(digit));
            fractionalPart -= digit;
        }

        return (fracResult.isEmpty()) ? intResult.toString() : intResult + "." + fracResult;
    }

    // Перетворення символу у число для шістнадцяткової системи
    private static int charToDigit(char c) {
        if (Character.isDigit(c)) return c - '0';
        else return Character.toUpperCase(c) - 'A' + 10;
    }

    private static char digitToChar(long digit) {
        if (digit < 10) return (char) ('0' + digit);
        else return (char) ('A' + digit - 10);
    }

    // Перетворення числа з будь-якої системи у десяткову
    public static double baseToDecimal(String number, int base) {
        number = number.toUpperCase();
        String[] parts = number.split("\\.");
        String intPart = parts[0];
        String fracPart = parts.length > 1 ? parts[1] : "";

        // Ціла частина
        double result = 0;
        int power = 0;
        for (int i = intPart.length() - 1; i >= 0; i--) {
            result += charToDigit(intPart.charAt(i)) * Math.pow(base, power++);
        }

        // Дробова частина
        for (int i = 0; i < fracPart.length(); i++) {
            result += charToDigit(fracPart.charAt(i)) * Math.pow(base, -(i + 1));
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nВиберіть операцію:");
            System.out.println("1. Десяткова → Двійкова");
            System.out.println("2. Двійкова → Десяткова");
            System.out.println("3. Десяткова → Вісімкова");
            System.out.println("4. Вісімкова → Десяткова");
            System.out.println("5. Десяткова → Шістнадцяткова");
            System.out.println("6. Шістнадцяткова → Десяткова");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) break;

            switch (choice) {
                case 1 -> {
                    System.out.print("Введіть десяткове число: ");
                    double number = sc.nextDouble();
                    System.out.println("Двійкове: " + decimalToBase(number, 2));
                }
                case 2 -> {
                    System.out.print("Введіть двійкове число: ");
                    String number = sc.nextLine();
                    System.out.println("Десяткове: " + baseToDecimal(number, 2));
                }
                case 3 -> {
                    System.out.print("Введіть десяткове число: ");
                    double number = sc.nextDouble();
                    System.out.println("Вісімкове: " + decimalToBase(number, 8));
                }
                case 4 -> {
                    System.out.print("Введіть вісімкове число: ");
                    String number = sc.nextLine();
                    System.out.println("Десяткове: " + baseToDecimal(number, 8));
                }
                case 5 -> {
                    System.out.print("Введіть десяткове число: ");
                    double number = sc.nextDouble();
                    System.out.println("Шістнадцяткове: " + decimalToBase(number, 16));
                }
                case 6 -> {
                    System.out.print("Введіть шістнадцяткове число: ");
                    String number = sc.nextLine();
                    System.out.println("Десяткове: " + baseToDecimal(number, 16));
                }
                default -> System.out.println("Невірний вибір!");
            }
        }

        sc.close();
        System.out.println("Програма завершена.");
    }
}
