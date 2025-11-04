package algo;

import java.util.*;

class Procedure {
    private final String name;
    private final int paramCount;
    private final short[] parameters;

    public Procedure(String name, short[] parameters) {
        this.name = name;
        this.paramCount = parameters.length;
        this.parameters = parameters;
    }

    public int getParamCount() {
        return paramCount;
    }

    public short[] getParameters() {
        return parameters;
    }

    public String getName() {
        return name;
    }

    public int getMemorySize() {
        // Ім'я: по 2 байти на символ (char = 2 байти)
        // + 4 байти на paramCount (int)
        // + по 2 байти на кожен параметр
        return name.length() * 2 + 4 + paramCount * 2;
    }

    @Override
    public String toString() {
        return String.format("Ім'я: %s | К-ть параметрів: %d | Параметри: %s",
                name, paramCount, Arrays.toString(parameters));
    }
}

class ProcedureStack {
    private final Stack<Procedure> stack = new Stack<>();

    public void push(Procedure p) {
        stack.push(p);
        System.out.println("Процедуру додано у стек!");
    }

    public void pop() {
        if (!stack.isEmpty()) {
            Procedure removed = stack.pop();
            System.out.println("Видалено: " + removed.getName());
        } else {
            System.out.println("Стек порожній!");
        }
    }

    public void clear() {
        stack.clear();
        System.out.println("Стек очищено!");
    }

    public void print() {
        if (stack.isEmpty()) {
            System.out.println("Стек порожній!");
            return;
        }
        System.out.println("\n--- Вміст стеку ---");
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.println(stack.get(i));
        }
    }

    public int getMemorySize() {
        int total = 0;
        for (Procedure p : stack) {
            total += p.getMemorySize();
        }
        return total;
    }

    public int getProcedureCount() {
        return stack.size();
    }
}

public class lb3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProcedureStack ps = new ProcedureStack();

        while (true) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Додати процедуру");
            System.out.println("2. Видалити верхню процедуру");
            System.out.println("3. Очистити стек");
            System.out.println("4. Показати стек");
            System.out.println("5. Показати зайняту пам'ять");
            System.out.println("6. Показати кількість процедур");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Введіть ім'я процедури: ");
                    String name = sc.nextLine();

                    System.out.print("Введіть кількість параметрів: ");
                    int n = sc.nextInt();

                    short[] params = new short[n];
                    for (int i = 0; i < n; i++) {
                        System.out.print("Параметр " + (i + 1) + ": ");
                        params[i] = sc.nextShort();
                    }

                    ps.push(new Procedure(name, params));
                }
                case 2 -> ps.pop();
                case 3 -> ps.clear();
                case 4 -> ps.print();
                case 5 -> System.out.println("Зайнято пам'яті: " + ps.getMemorySize() + " байт");
                case 6 -> System.out.println("Кількість процедур у стеці: " + ps.getProcedureCount());
                case 0 -> {
                    System.out.println("Вихід...");
                    return;
                }
                default -> System.out.println("Невірний вибір!");
            }
        }
    }
}
