package computerArchitecture;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lab1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter number: ");
        String number = in.nextLine();
        System.out.println(numberPredicator(number, in));


    }

    public static String numberPredicator(String number, Scanner in){
        List<String> possibleSystems = new ArrayList<>();
        if(number.matches("^[01]+(\\.[01]+)?$")) possibleSystems.add("bin");

        if(number.matches("[+-]?[0-7]+(\\.[0-7]+)?$")) possibleSystems.add("oct");

        if(number.matches("[+-]?[0-9]+(\\.[0-9]+)?$")) possibleSystems.add("dec");

        if(number.matches("[+-]?[0-9a-fA-F]+(\\.[0-9a-fA-F]+)?$")) possibleSystems.add("hex");

        if(possibleSystems.isEmpty()) return "unknown";

        if(possibleSystems.size() == 1) return possibleSystems.get(0);

        else if(possibleSystems.size() >1){
            System.out.println("Число " + number + " підходить для таких систем як: "
                    + possibleSystems + "." +
                    " \nВкажіть систему числення:");
            String system = in.nextLine();
            return system;
        }
        return "ERROR";
    }

}
