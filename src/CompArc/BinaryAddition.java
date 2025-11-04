package CompArc;

import java.util.Scanner;

import java.util.Scanner;

public class BinaryAddition {

    // Додаємо два двійкових числа у вигляді рядків
    public static void addBinary(String a, String b) {
        int n = Math.max(a.length(), b.length());
        a = String.format("%" + n + "s", a).replace(' ', '0');
        b = String.format("%" + n + "s", b).replace(' ', '0');

        int carry = 0;
        StringBuilder result = new StringBuilder();

        System.out.println("Покрокове додавання:");
        for (int i = n - 1; i >= 0; i--) {
            int bitA = a.charAt(i) - '0';
            int bitB = b.charAt(i) - '0';

            int sum = bitA + bitB + carry;
            int resBit = sum % 2;
            carry = sum / 2;

            System.out.printf("Розряд %d: %d + %d + переніс %d = %d (результат %d, новий переніс %d)\n",
                    i, bitA, bitB, carry, sum, resBit, carry);

            result.insert(0, resBit);
        }

        if (carry > 0) {
            System.out.println("Переповнення розрядної сітки! Переніс = " + carry);
            result.insert(0, carry);
        }

        System.out.println("Кінцевий результат додавання: " + result);
    }

    // Перетворення десяткового числа в двійковий рядок
    public static String decimalToBinary(int number) {
        if (number == 0) return "0";
        StringBuilder binary = new StringBuilder();
        while (number > 0) {
            binary.insert(0, number % 2);
            number /= 2;
        }
        return binary.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введіть перше додатне число: ");
        int num1 = sc.nextInt();
        System.out.print("Введіть друге додатне число: ");
        int num2 = sc.nextInt();

        if (num1 < 0 || num2 < 0) {
            System.out.println("Помилка: числа повинні бути додатними!");
            return;
        }

        String bin1 = decimalToBinary(num1);
        String bin2 = decimalToBinary(num2);

        System.out.println("Перше число у двійковій системі:  " + bin1);
        System.out.println("Друге число у двійковій системі: " + bin2);

        addBinary(bin1, bin2);

        sc.close();
    }
}
