package algo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class lab5 {

    private static final int TABLE_SIZE = 10000;
    private static final int TEST_KEYS_COUNT = 1000;
    private static final double LOAD_FACTOR = 0.65;

    // --- Enum для методів хешування ---
    enum HashMethod {
        MULTIPLICATION, CRYPTO_SHA256
    }

    // -----------------------------------------------------------
    // ЧАСТИНА 1: ВЛАСНА ХЕШ-ФУНКЦІЯ (МЕТОД МНОЖЕННЯ)
    // -----------------------------------------------------------
    public static int multiplicationHash(String key, int tableSize) {
        long k = 0;
        int p = 31;
        long power = 1;

        for (int i = 0; i < key.length(); i++) {
            // Обмежуємо накопичення, щоб уникнути надто великих чисел, хоча long є великим
            k = (k + (key.charAt(i) * power));
            power = (power * p);
        }
        k = Math.abs(k);

        final double A = 0.6180339887; // Золотий перетин
        double fraction = (k * A) % 1;
        return (int) (tableSize * fraction);
    }

    // -----------------------------------------------------------
    // ЧАСТИНА 2: JH-256 (Використання SHA-256 як замінника)
    // -----------------------------------------------------------
    public static String hashWithSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Помилка: SHA-256 не знайдено.");
            return null;
        }
    }

    public static int cryptoHashIndex(String key, int tableSize) {
        String sha256 = hashWithSHA256(key);
        if (sha256 == null) return 0;
        return Math.abs(sha256.hashCode()) % tableSize;
    }

    // -----------------------------------------------------------
    // ЧАСТИНА 3: ХЕШ-ТАБЛИЦЯ ТА ЛОГІКА
    // -----------------------------------------------------------
    static class SymbolTable {
        private final int tableSize;
        private final List<String>[] table;
        private final HashMethod method;
        private int collisionCount = 0;

        public SymbolTable(int size, HashMethod method) {
            this.tableSize = size;
            this.method = method;
            this.table = new LinkedList[tableSize];
            for (int i = 0; i < tableSize; i++) {
                table[i] = new LinkedList<>();
            }
        }

        public int hash(String key) {
            if (method == HashMethod.MULTIPLICATION) {
                return multiplicationHash(key, tableSize);
            } else {
                return cryptoHashIndex(key, tableSize);
            }
        }

        public void insert(String identifier) {
            int index = hash(identifier);
            if (!table[index].isEmpty()) {
                collisionCount++;
            }
            if (!table[index].contains(identifier)) {
                table[index].add(identifier);
            }
        }

        public int getCollisionCount() {
            return collisionCount;
        }

        public String getMethodName() {
            return method == HashMethod.MULTIPLICATION ? "Метод Множення" : "SHA-256 (замінник JH-256)";
        }
    }

    // --- Допоміжна функція для генерації ключів ---
    private static List<String> generateRandomKeys(int count) {
        List<String> keys = new ArrayList<>(count);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int length = random.nextInt(8) + 5;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < length; j++) {
                char c = (char) ('a' + random.nextInt(26));
                sb.append(c);
            }
            sb.append("_").append(random.nextInt(100));
            keys.add(sb.toString());
        }
        return keys;
    }

    // --- Функція для виконання порівняння ---
    private static void runComparison(HashMethod method, List<String> insertionKeys, List<String> testKeys) {
        SymbolTable table = new SymbolTable(TABLE_SIZE, method);

        // 1. Заповнення таблиці
        for (String key : insertionKeys) {
            table.insert(key);
        }

        int initialCollisions = table.getCollisionCount();

        // 2. Обчислення колізій для додаткових ключів
        int collisionsFromTest = 0;
        for (String key : testKeys) {
            int index = table.hash(key);
            if (!table.table[index].isEmpty()) {
                collisionsFromTest++;
            }
        }

        System.out.printf("  > Колізії при початковому заповненні (%d ключів): %d\n", insertionKeys.size(), initialCollisions);
        System.out.printf("  > Колізії під час тестування (%d ключів): %d\n", testKeys.size(), collisionsFromTest);
    }

    // -----------------------------------------------------------
    // ЧАСТИНА 4: ІНТЕРАКТИВНЕ МЕНЮ (while(true) + switch)
    // -----------------------------------------------------------

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Лабораторна робота №5: Керування Хеш-Функціями (Варіант 1.20)");

        while (true) {
            System.out.println("\n--- ГЛАВНЕ МЕНЮ ---");
            System.out.println("1. Тестувати хешування (JH-256/Власне)");
            System.out.println("2. Виконати порівняння хеш-функцій (Пункт 5)");
            System.out.println("3. Вихід");
            System.out.print("Виберіть опцію (1-3): ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очищення буфера

                switch (choice) {
                    case 1:
                        testHashing(scanner);
                        break;
                    case 2:
                        performComparison();
                        break;
                    case 3:
                        System.out.println("Завершення роботи. До побачення!");
                        return; // Вихід з main, завершення програми
                    default:
                        System.out.println("❌ Некоректний вибір. Спробуйте ще раз.");
                }
            } else {
                System.out.println("❌ Некоректний ввід. Будь ласка, введіть число.");
                scanner.nextLine(); // Очищення некоректного вводу
            }
        }
    }

    private static void testHashing(Scanner scanner) {
        System.out.println("\n--- ТЕСТУВАННЯ ХЕШУВАННЯ ---");
        System.out.print("Введіть рядок для хешування: ");
        String input = scanner.nextLine();

        System.out.println("\n[A] Власна Хеш-Функція (Метод Множення):");
        int indexMulti = multiplicationHash(input, TABLE_SIZE);
        System.out.printf("  > Хеш-індекс (m=%d): %d\n", TABLE_SIZE, indexMulti);

        System.out.println("\n[B] Криптографічний Хеш (SHA-256 як замінник JH-256):");
        String shaHash = hashWithSHA256(input);
        int indexCrypto = cryptoHashIndex(input, TABLE_SIZE);
        System.out.println("  > Повний SHA-256 хеш: " + shaHash);
        System.out.printf("  > Хеш-індекс (m=%d): %d\n", TABLE_SIZE, indexCrypto);
    }

    private static void performComparison() {
        int keysToInsert = (int) (TABLE_SIZE * LOAD_FACTOR);

        System.out.println("\n--- ПОРІВНЯННЯ ХЕШ-ФУНКЦІЙ (Пункт 5) ---");
        System.out.printf("Параметри: Таблиця %d, Заповнення %d%% (%d ключів), Тест %d ключів.\n",
                TABLE_SIZE, (int)(LOAD_FACTOR * 100), keysToInsert, TEST_KEYS_COUNT);

        // Генерація унікальних ключів
        List<String> allKeys = generateRandomKeys(keysToInsert + TEST_KEYS_COUNT);
        List<String> insertionKeys = allKeys.subList(0, keysToInsert);
        List<String> testKeys = allKeys.subList(keysToInsert, allKeys.size());

        System.out.println("\n[A] Власна Хеш-Функція (Метод Множення)");
        runComparison(HashMethod.MULTIPLICATION, insertionKeys, testKeys);

        System.out.println("\n[B] Криптографічний Хеш (SHA-256 для індексації)");
        runComparison(HashMethod.CRYPTO_SHA256, insertionKeys, testKeys);
    }
}