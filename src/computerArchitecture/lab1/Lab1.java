package computerArchitecture.lab1;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static computerArchitecture.lab1.Functions.decToBin;
import static computerArchitecture.lab1.NumberUtils.numberPredicator;

public class Lab1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter number: ");
        String number = in.nextLine();
        numberPredicator(number, in);
        printSystems();
        decToBin(number);
    }


    public static void printSystems(){
        System.out.println("""
                
                Enter the system to which the number should be converted
                1: 2
                2: 8
                3: 10
                4: 16""");
    }

}
