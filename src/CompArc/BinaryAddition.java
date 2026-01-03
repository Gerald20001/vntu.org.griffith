package CompArc;
import java.util.Scanner;

public class BinaryAddition {

    private static final int BIT_WIDTH = 8;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== ДОДАВАННЯ У ДВІЙКОВІЙ СИСТЕМІ ===\n");

        System.out.print("Введіть перше число (9 біт, прямий код): ");
        String binary1 = sc.next();

        System.out.print("Введіть друге число (9 біт, прямий код): ");
        String binary2 = sc.next();

        System.out.print("\nОберіть режим (1 - прямий код, 2 - обернений код): ");
        int mode = sc.nextInt();

        int num1 = binaryToDecimal(binary1);
        int num2 = binaryToDecimal(binary2);

        System.out.println("\n" + "=".repeat(50));

        if (mode == 1) {
            if (binary1.charAt(0) == '1' || binary2.charAt(0) == '1') {
                System.out.println("ПОМИЛКА: прямий код тільки для додатних чисел");
                return;
            }
            addDirectCode(num1, num2, binary1, binary2);
        } else {
            addInverseCode(num1, num2);
        }
    }

    // ===== КОНВЕРТАЦІЯ =====
    static int binaryToDecimal(String binary) {
        boolean neg = binary.charAt(0) == '1';
        int val = Integer.parseInt(binary.substring(1), 2);
        return neg ? -val : val;
    }

    // ===== ПРЯМИЙ КОД =====
    static void addDirectCode(int n1, int n2, String b1, String b2) {
        System.out.println("РЕЖИМ: ПРЯМИЙ КОД");
        System.out.println("=".repeat(50));

        String m1 = b1.substring(1);
        String m2 = b2.substring(1);

        int carry = 0;
        StringBuilder sum = new StringBuilder();

        System.out.println("\nПОБІТОВЕ ДОДАВАННЯ:");
        System.out.println("i | b1 | b2 | перенос | результат | новий перенос");
        System.out.println("-".repeat(55));

        for (int i = BIT_WIDTH - 1; i >= 0; i--) {
            int bit1 = m1.charAt(i) - '0';
            int bit2 = m2.charAt(i) - '0';

            int s = bit1 + bit2 + carry;
            int bit = s % 2;
            int newCarry = s / 2;

            System.out.printf(
                    "%d |  %d |  %d |    %d     |     %d     |       %d%n",
                    i, bit1, bit2, carry, bit, newCarry
            );

            sum.insert(0, bit);
            carry = newCarry;
        }

        System.out.println("\nРЕЗУЛЬТАТ:");
        System.out.println("0" + sum + " = " + (n1 + n2));
        System.out.println("Переповнення: " + (carry == 1 ? "ТАК" : "НІ"));
    }

    // ===== ОБЕРНЕНИЙ КОД =====
    static void addInverseCode(int n1, int n2) {
        System.out.println("РЕЖИМ: ОБЕРНЕНИЙ КОД");
        System.out.println("=".repeat(50));

        String ic1 = toInverseCode(n1);
        String ic2 = toInverseCode(n2);

        System.out.println("\nОБЕРНЕНІ КОДИ:");
        System.out.println(ic1);
        System.out.println(ic2);

        int carry = 0;
        StringBuilder sum = new StringBuilder();

        System.out.println("\nПОБІТОВЕ ДОДАВАННЯ:");
        System.out.println("i | b1 | b2 | перенос | результат | новий перенос");
        System.out.println("-".repeat(55));

        for (int i = ic1.length() - 1; i >= 0; i--) {
            int bit1 = ic1.charAt(i) - '0';
            int bit2 = ic2.charAt(i) - '0';

            int s = bit1 + bit2 + carry;
            int bit = s % 2;
            int newCarry = s / 2;

            System.out.printf(
                    "%d |  %d |  %d |    %d     |     %d     |       %d%n",
                    i, bit1, bit2, carry, bit, newCarry
            );

            sum.insert(0, bit);
            carry = newCarry;
        }

        System.out.println("\nПОПЕРЕДНЯ СУМА:");
        System.out.println(sum + "  перенос = " + carry);

        if (carry == 1) {
            System.out.println("\nЦИКЛІЧНЕ ПЕРЕНЕСЕННЯ:");
            System.out.println("i | біт | перенос | результат | новий перенос");
            System.out.println("-".repeat(55));

            StringBuilder finalSum = new StringBuilder();
            int c = 1;

            for (int i = sum.length() - 1; i >= 0; i--) {
                int b = sum.charAt(i) - '0';
                int s = b + c;
                int bit = s % 2;
                int newCarry = s / 2;

                System.out.printf(
                        "%d |  %d  |    %d     |     %d     |       %d%n",
                        i, b, c, bit, newCarry
                );

                finalSum.insert(0, bit);
                c = newCarry;
            }
            sum = finalSum;
        }

        String dc = inverseToDirectCode(sum.toString());
        int result = binaryToDecimal(dc);

        System.out.println("\nКІНЦЕВИЙ РЕЗУЛЬТАТ:");
        System.out.println(dc + " = " + result);
    }

    // ===== ДОПОМІЖНІ =====
    static String toDirectCode(int n) {
        String sign = n < 0 ? "1" : "0";
        String bits = Integer.toBinaryString(Math.abs(n));
        bits = String.format("%" + BIT_WIDTH + "s", bits).replace(' ', '0');
        return sign + bits;
    }

    static String toInverseCode(int n) {
        String dc = toDirectCode(n);
        if (n >= 0) return dc;

        StringBuilder ic = new StringBuilder("1");
        for (int i = 1; i < dc.length(); i++) {
            ic.append(dc.charAt(i) == '0' ? '1' : '0');
        }
        return ic.toString();
    }

    static String inverseToDirectCode(String ic) {
        if (ic.charAt(0) == '0') return ic;

        StringBuilder dc = new StringBuilder("1");
        for (int i = 1; i < ic.length(); i++) {
            dc.append(ic.charAt(i) == '0' ? '1' : '0');
        }
        return dc.toString();
    }
}
