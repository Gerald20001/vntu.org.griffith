package computerArchitecture.lab1;

import java.util.Scanner;

import static computerArchitecture.lab1.NumberUtils.isValid;


public class Lab1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            Functions.printVariants();
            int variant = Functions.enterVariant(in);

            switch (variant) {
                case 1 -> {
                    String number = Functions.enterValue(in);
                    if (isValid(number, "dec"))
                        Functions.decToBin(number);
                     else
                        System.out.println("NOT GOOD");
                     break;

                }
                case 2 ->{
                    String number = Functions.enterValue(in);
                    if(isValid(number, "bin"))
                    Functions.binToDec(number);
                    else
                    System.out.println("NOT GOOD");
                    break;
                }
                case 3 -> {
                    String number = Functions.enterValue(in);
                    if(isValid(number, "oct"))
                        Functions.binToOct(number);
                    else
                    System.out.println("NOT GOOD");
                }
                case 0 -> {
                    System.out.println("EXIT...");
                    return;
                }
                default -> System.out.println("WRONG VARIANT!");
            }
        }
    }
}
