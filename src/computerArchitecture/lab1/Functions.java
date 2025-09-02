package computerArchitecture.lab1;

public class Functions {

    // ДЕСЯТКОВА СИСТЕМА В ДВІЙКОВУ
    public static void decToBin(String number){
        String[] parts = number.split("\\.");

        String wholeNumberString = parts[0];
        int wholeNumber = Integer.parseInt(wholeNumberString);

        String fracNumberString = parts.length > 1 ? "0." + parts[1] : "0";
        double fracNumber = Double.parseDouble(fracNumberString);

        System.out.println("\n\nПОЧАТОК РОБОТИ 10 -> 2\n");

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

        System.out.println("Переведення дробової частини:\n");
        StringBuilder fracResult = new StringBuilder();
        int step2 = 1;
        for (int i = 0; i < 4; i++) {
            double temp = fracNumber * 2;
            int bit = (temp >= 1) ? 1 : 0;
            fracResult.append(bit);
            if (bit == 1) {
                temp -= 1;
                System.out.println("Число більше за 1. Віднімемо 1\n");
            }
            System.out.printf("Крок %d: %.4f*2=%.4f bit=%d%n%n", step2, fracNumber, temp, bit);
            fracNumber = temp;
            step2++;
        }

        System.out.println("FINAL RESULT: " + binResult.reverse()+"."+fracResult);
    }
}
