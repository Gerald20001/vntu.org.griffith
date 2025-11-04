package algo;

import java.util.Arrays;
import java.util.Random;

public class lb4 {

    // --- Гном’яче сортування ---
    public static void gnomeSort(int[] arr) {
        int i = 1;
        while (i < arr.length) {
            if (i == 0 || arr[i - 1] <= arr[i]) {
                i++;
            } else {
                int temp = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = temp;
                i--;
            }
        }
    }

    // --- Швидке сортування ---
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // --- Генерація випадкового масиву ---
    public static int[] generateArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1_000_000);
        }
        return arr;
    }

    // --- Глибока копія ---
    public static int[] copyArray(int[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }

    public static void main(String[] args) {
        int size = 20000; // можна змінити на 1_000_000 для кращої точності
        int[] original = generateArray(size);

        int[] arr1 = copyArray(original);
        int[] arr2 = copyArray(original);

        System.out.println("\n\n\nПорівняння GnomeSort та QuickSort на масиві розміром " + size);

        long start1 = System.nanoTime();
        gnomeSort(arr1);
        long end1 = System.nanoTime();

        long start2 = System.nanoTime();
        quickSort(arr2, 0, arr2.length - 1);
        long end2 = System.nanoTime();

        double timeGnome = (end1 - start1) / 1_000_000.0;
        double timeQuick = (end2 - start2) / 1_000_000.0;

        System.out.printf("⏱️ GnomeSort: %.2f мс%n", timeGnome);
        System.out.printf("⚡ QuickSort: %.2f мс%n", timeQuick);

        System.out.println("\nРезультат перевірки (відсортовано?):");
        System.out.println("GnomeSort -> " + isSorted(arr1));
        System.out.println("QuickSort -> " + isSorted(arr2));

        System.out.println("\nВисновок:");
        System.out.println("GnomeSort має часову складність O(n²) — повільний для великих даних.");
        System.out.println("QuickSort має середню складність O(n log n) — працює значно швидше.");
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }
}
