package algo;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class lab5 {

    // Розмір хеш-таблиці
    static final int TABLE_SIZE = 1000;
    static SymbolTable tableKnown;
    static SymbolTable tableCustom;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Ініціалізація таблиць
        tableKnown = new SymbolTable(TABLE_SIZE, "Known (SHA-256)");
        tableCustom = new SymbolTable(TABLE_SIZE, "Custom (Multiplication)");

        // Генеруємо початкові дані
        String[] keys = generateKeys(600); // 60% заповнення

        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   Лабораторна робота №5: Хеш-функції           ║");
        System.out.println("║   Таблиці символів (ідентифікаторів)           ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        // Заповнюємо таблиці початковими даними
        System.out.println("⏳ Завантаження початкових даних...");
        for (String key : keys) {
            int hash1 = getKnownHashIndex(key, TABLE_SIZE);
            tableKnown.put(key, hash1, "variable");

            int hash2 = getCustomHashMultiplication(key, TABLE_SIZE);
            tableCustom.put(key, hash2, "variable");
        }
        System.out.println("✓ Завантажено " + keys.length + " ідентифікаторів\n");

        // Головне меню
        showMainMenu();
    }

    // --- ГОЛОВНЕ МЕНЮ ---
    public static void showMainMenu() {
        while (true) {
            System.out.println("\n╔═══════════════════════════════════════════════╗");
            System.out.println("║              ГОЛОВНЕ МЕНЮ                     ║");
            System.out.println("╠═══════════════════════════════════════════════╣");
            System.out.println("║ 1. Додати новий ідентифікатор                 ║");
            System.out.println("║ 2. Знайти ідентифікатор                       ║");
            System.out.println("║ 3. Видалити ідентифікатор                     ║");
            System.out.println("║ 4. Показати всі ідентифікатори                ║");
            System.out.println("║ 5. Показати статистику таблиць                ║");
            System.out.println("║ 6. Порівняти хеш-функції                      ║");
            System.out.println("║ 7. Генерувати тестові дані                    ║");
            System.out.println("║ 8. Очистити таблиці                           ║");
            System.out.println("║ 0. Вихід                                      ║");
            System.out.println("╚═══════════════════════════════════════════════╝");
            System.out.print("Оберіть опцію: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addIdentifier();
                    break;
                case 2:
                    findIdentifier();
                    break;
                case 3:
                    deleteIdentifier();
                    break;
                case 4:
                    showAllIdentifiers();
                    break;
                case 5:
                    showStatistics();
                    break;
                case 6:
                    compareHashFunctions();
                    break;
                case 7:
                    generateTestData();
                    break;
                case 8:
                    clearTables();
                    break;
                case 0:
                    System.out.println("\n👋 До побачення!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Невірний вибір! Спробуйте ще раз.");
            }
        }
    }

    // --- 1. ДОДАТИ ІДЕНТИФІКАТОР ---
    public static void addIdentifier() {
        System.out.println("\n--- Додавання ідентифікатора ---");
        System.out.print("Введіть ім'я ідентифікатора: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("❌ Ім'я не може бути порожнім!");
            return;
        }

        System.out.print("Введіть тип (int/string/float/boolean): ");
        String type = scanner.nextLine().trim();

        // Додаємо в обидві таблиці
        int hash1 = getKnownHashIndex(name, TABLE_SIZE);
        int hash2 = getCustomHashMultiplication(name, TABLE_SIZE);

        tableKnown.put(name, hash1, type);
        tableCustom.put(name, hash2, type);

        System.out.println("✓ Ідентифікатор '" + name + "' типу '" + type + "' успішно додано!");
        System.out.println("  SHA-256 хеш-індекс: " + hash1);
        System.out.println("  Custom хеш-індекс: " + hash2);
    }

    // --- 2. ЗНАЙТИ ІДЕНТИФІКАТОР ---
    public static void findIdentifier() {
        System.out.println("\n--- Пошук ідентифікатора ---");
        System.out.print("Введіть ім'я ідентифікатора: ");
        String name = scanner.nextLine().trim();

        System.out.println("\nОберіть таблицю для пошуку:");
        System.out.println("1. SHA-256 таблиця");
        System.out.println("2. Custom таблиця");
        System.out.println("3. Обидві таблиці");
        System.out.print("Ваш вибір: ");
        int choice = getIntInput();

        switch (choice) {
            case 1:
                searchInTable(name, tableKnown, getKnownHashIndex(name, TABLE_SIZE));
                break;
            case 2:
                searchInTable(name, tableCustom, getCustomHashMultiplication(name, TABLE_SIZE));
                break;
            case 3:
                System.out.println("\n[SHA-256 таблиця]");
                searchInTable(name, tableKnown, getKnownHashIndex(name, TABLE_SIZE));
                System.out.println("\n[Custom таблиця]");
                searchInTable(name, tableCustom, getCustomHashMultiplication(name, TABLE_SIZE));
                break;
            default:
                System.out.println("❌ Невірний вибір!");
        }
    }

    private static void searchInTable(String name, SymbolTable table, int hash) {
        Symbol symbol = table.find(name, hash);
        if (symbol != null) {
            System.out.println("✓ Знайдено!");
            System.out.println("  Ім'я: " + symbol.name);
            System.out.println("  Тип: " + symbol.type);
            System.out.println("  Хеш-індекс: " + hash);
        } else {
            System.out.println("❌ Ідентифікатор '" + name + "' не знайдено");
        }
    }

    // --- 3. ВИДАЛИТИ ІДЕНТИФІКАТОР ---
    public static void deleteIdentifier() {
        System.out.println("\n--- Видалення ідентифікатора ---");
        System.out.print("Введіть ім'я ідентифікатора: ");
        String name = scanner.nextLine().trim();

        int hash1 = getKnownHashIndex(name, TABLE_SIZE);
        int hash2 = getCustomHashMultiplication(name, TABLE_SIZE);

        boolean deleted1 = tableKnown.remove(name, hash1);
        boolean deleted2 = tableCustom.remove(name, hash2);

        if (deleted1 || deleted2) {
            System.out.println("✓ Ідентифікатор '" + name + "' видалено!");
        } else {
            System.out.println("❌ Ідентифікатор '" + name + "' не знайдено");
        }
    }

    // --- 4. ПОКАЗАТИ ВСІ ІДЕНТИФІКАТОРИ ---
    public static void showAllIdentifiers() {
        System.out.println("\n--- Всі ідентифікатори ---");
        System.out.println("Оберіть таблицю:");
        System.out.println("1. SHA-256 таблиця");
        System.out.println("2. Custom таблиця");
        System.out.print("Ваш вибір: ");
        int choice = getIntInput();

        SymbolTable table = (choice == 1) ? tableKnown : tableCustom;
        ArrayList<Symbol> symbols = table.getAllSymbols();

        if (symbols.isEmpty()) {
            System.out.println("❌ Таблиця порожня");
            return;
        }

        System.out.println("\nЗнайдено " + symbols.size() + " ідентифікаторів:");
        System.out.println("┌────────────────────────┬────────────┐");
        System.out.println("│ Ім'я                   │ Тип        │");
        System.out.println("├────────────────────────┼────────────┤");

        int count = 0;
        for (Symbol s : symbols) {
            System.out.printf("│ %-22s │ %-10s │%n", s.name, s.type);
            count++;
            if (count >= 20) {
                System.out.println("└────────────────────────┴────────────┘");
                System.out.println("... та ще " + (symbols.size() - 20) + " ідентифікаторів");
                return;
            }
        }
        System.out.println("└────────────────────────┴────────────┘");
    }

    // --- 5. ПОКАЗАТИ СТАТИСТИКУ ---
    public static void showStatistics() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  ║           СТАТИСТИКА ТАБЛИЦЬ                 ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");

        System.out.println("\n[1] " + tableKnown.name);
        tableKnown.printStats();

        System.out.println("\n[2] " + tableCustom.name);
        tableCustom.printStats();

        System.out.println("\n--- Порівняння ---");
        int diff = tableKnown.getCollisions() - tableCustom.getCollisions();
        if (diff > 0) {
            System.out.println("✓ Custom хеш-функція краща на " + diff + " колізій");
        } else if (diff < 0) {
            System.out.println("✓ SHA-256 хеш-функція краща на " + Math.abs(diff) + " колізій");
        } else {
            System.out.println("= Обидві функції показали однаковий результат");
        }
    }

    // --- 6. ПОРІВНЯННЯ ХЕШ-ФУНКЦІЙ ---
    public static void compareHashFunctions() {
        System.out.println("\n--- Порівняння хеш-функцій ---");
        System.out.print("Введіть кількість тестових ключів (100-10000): ");
        int count = getIntInput();

        if (count < 100 || count > 10000) {
            System.out.println("❌ Кількість повинна бути від 100 до 10000");
            return;
        }

        System.out.println("\n⏳ Генерація тестових ключів...");
        String[] testKeys = generateKeys(count);

        // Тестуємо SHA-256
        SymbolTable test1 = new SymbolTable(TABLE_SIZE, "Test SHA-256");
        long start1 = System.nanoTime();
        for (String key : testKeys) {
            int hash = getKnownHashIndex(key, TABLE_SIZE);
            test1.put(key, hash, "test");
        }
        long time1 = System.nanoTime() - start1;

        // Тестуємо Custom
        SymbolTable test2 = new SymbolTable(TABLE_SIZE, "Test Custom");
        long start2 = System.nanoTime();
        for (String key : testKeys) {
            int hash = getCustomHashMultiplication(key, TABLE_SIZE);
            test2.put(key, hash, "test");
        }
        long time2 = System.nanoTime() - start2;

        // Виводимо результати
        System.out.println("\n  ╔════════════════════════════════════════════════╗");
        System.out.println("  ║         РЕЗУЛЬТАТИ ПОРІВНЯННЯ                  ║");
        System.out.println("  ╚════════════════════════════════════════════════╝");
        System.out.println("\nКількість тестових ключів: " + count);
        System.out.println("Розмір таблиці: " + TABLE_SIZE);
        System.out.println("\n[SHA-256]");
        System.out.println("  Колізії: " + test1.getCollisions());
        System.out.println("  Час: " + (time1 / 1_000_000.0) + " мс");
        System.out.println("\n[Custom Multiplication]");
        System.out.println("  Колізії: " + test2.getCollisions());
        System.out.println("  Час: " + (time2 / 1_000_000.0) + " мс");

        System.out.println("\n--- Висновок ---");
        if (test1.getCollisions() < test2.getCollisions()) {
            System.out.println("✓ SHA-256 показав кращий розподіл (-" +
                    (test2.getCollisions() - test1.getCollisions()) + " колізій)");
        } else if (test1.getCollisions() > test2.getCollisions()) {
            System.out.println("✓ Custom показав кращий розподіл (-" +
                    (test1.getCollisions() - test2.getCollisions()) + " колізій)");
        } else {
            System.out.println("= Однакова кількість колізій");
        }

        if (time1 < time2) {
            System.out.println("⚡ SHA-256 швидший на " +
                    String.format("%.2f", (time2 - time1) / 1_000_000.0) + " мс");
        } else {
            System.out.println("⚡ Custom швидший на " +
                    String.format("%.2f", (time1 - time2) / 1_000_000.0) + " мс");
        }
    }

    // --- 7. ГЕНЕРУВАТИ ТЕСТОВІ ДАНІ ---
    public static void generateTestData() {
        System.out.println("\n--- Генерація тестових даних ---");
        System.out.print("Введіть кількість ідентифікаторів (10-1000): ");
        int count = getIntInput();

        if (count < 10 || count > 1000) {
            System.out.println("❌ Кількість повинна бути від 10 до 1000");
            return;
        }

        String[] keys = generateKeys(count);
        String[] types = {"int", "float", "string", "boolean"};
        Random rand = new Random();

        for (String key : keys) {
            String type = types[rand.nextInt(types.length)];
            int hash1 = getKnownHashIndex(key, TABLE_SIZE);
            int hash2 = getCustomHashMultiplication(key, TABLE_SIZE);
            tableKnown.put(key, hash1, type);
            tableCustom.put(key, hash2, type);
        }

        System.out.println("✓ Згенеровано та додано " + count + " ідентифікаторів!");
    }

    // --- 8. ОЧИСТИТИ ТАБЛИЦІ ---
    public static void clearTables() {
        System.out.println("\n--- Очищення таблиць ---");
        System.out.print("Ви впевнені? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            tableKnown = new SymbolTable(TABLE_SIZE, "Known (SHA-256)");
            tableCustom = new SymbolTable(TABLE_SIZE, "Custom (Multiplication)");
            System.out.println("✓ Таблиці очищено!");
        } else {
            System.out.println("❌ Скасовано");
        }
    }

    // --- ДОПОМІЖНІ МЕТОДИ ---
    private static int getIntInput() {
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (Exception e) {
            return -1;
        }
    }

    // --- ХЕШ-ФУНКЦІЇ ---
    public static int getKnownHashIndex(String key, int tableSize) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(key.getBytes(StandardCharsets.UTF_8));

            long hashVal = 0;
            for (int i = 0; i < 4; i++) {
                hashVal = (hashVal << 8) + (encodedhash[i] & 0xff);
            }
            return Math.abs((int)(hashVal % tableSize));
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getCustomHashMultiplication(String key, int tableSize) {
        int k = 0;
        for (char c : key.toCharArray()) {
            k = 31 * k + c;
        }
        k = Math.abs(k);

        double A = 0.6180339887;
        double val = k * A;
        double fractionalPart = val - (int)val;

        return (int) (tableSize * fractionalPart);
    }

    public static String[] generateKeys(int count) {
        String[] keys = new String[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            keys[i] = "var_" + random.nextInt(100000) + "_" + i;
        }
        return keys;
    }
}

// --- КЛАС СИМВОЛУ ---
class Symbol {
    String name;
    String type;

    public Symbol(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

// --- КЛАС ХЕШ-ТАБЛИЦІ ---
class SymbolTable {
    private LinkedList<Symbol>[] table;
    String name;
    private int size;

    public SymbolTable(int size, String name) {
        this.size = size;
        this.name = name;
        this.table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public void put(String key, int index, String type) {
        table[index].add(new Symbol(key, type));
    }

    public Symbol find(String key, int index) {
        for (Symbol s : table[index]) {
            if (s.name.equals(key)) {
                return s;
            }
        }
        return null;
    }

    public boolean remove(String key, int index) {
        for (Symbol s : table[index]) {
            if (s.name.equals(key)) {
                table[index].remove(s);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Symbol> getAllSymbols() {
        ArrayList<Symbol> result = new ArrayList<>();
        for (LinkedList<Symbol> list : table) {
            result.addAll(list);
        }
        return result;
    }

    public int getCollisions() {
        int collisions = 0;
        for (LinkedList<Symbol> list : table) {
            if (list.size() > 1) {
                collisions += (list.size() - 1);
            }
        }
        return collisions;
    }

    public void printStats() {
        int occupiedCells = 0;
        int collisions = 0;
        int maxChain = 0;

        for (LinkedList<Symbol> list : table) {
            if (!list.isEmpty()) {
                occupiedCells++;
                if (list.size() > 1) {
                    collisions += (list.size() - 1);
                }
                if (list.size() > maxChain) maxChain = list.size();
            }
        }

        System.out.println(" - Зайнятих комірок: " + occupiedCells);
        System.out.println(" - Кількість колізій: " + collisions);
        System.out.println(" - Макс. довжина ланцюжка: " + maxChain);
        System.out.println(" - Коефіцієнт заповнення: " +
                String.format("%.2f%%", (occupiedCells * 100.0 / size)));
    }
}