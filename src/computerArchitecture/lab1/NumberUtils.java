package computerArchitecture.lab1;


public class NumberUtils {
    public static boolean isValid(String number, String system) {

        switch (system) {
            case "bin":
                return number.matches("^[01]+(\\.[01]+)?$");
            case "oct":
                return number.matches("[+-]?[0-7]+(\\.[0-7]+)?$");
            case "dec":
                return number.matches("[+-]?[0-9]+(\\.[0-9]+)?$");
            case "hex":
                return number.matches("[+-]?[0-9a-fA-F]+(\\.[0-9a-fA-F]+)?$");
            default:
                return false;
        }

    }
}
