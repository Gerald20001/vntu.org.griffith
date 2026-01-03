package algo;

import java.util.Random;
import java.util.Stack;

public class lb4 {

public static void quickSort(int[] A) {
    Stack<Integer> stack = new Stack<>();
    stack.push(0);
    stack.push(A.length - 1);

    while (!stack.isEmpty()) {
        int high = stack.pop();
        int low = stack.pop();

        if (low < high) {
            int p = partition(A, low, high);

            // межі лівої -> в стек
            stack.push(low);
            stack.push(p - 1);

            // межі правої -> в стек
            stack.push(p + 1);
            stack.push(high);
        }
    }
}

private static int partition(int[] A, int low, int high) {
    int pivot = A[high]; // Беремо за опорний останній елемент
    int i = low;

    for (int j = low; j < high; j++) {
        if (A[j] <= pivot) {
            // Міняємо місцями
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
            i++;
        }
    }
    int temp = A[i];
    A[i] = A[high];
    A[high] = temp;

    return i;
}

    //Gnome Sort
    public static void gnomeSort(int[] A) {
        int n = A.length;
        int index = 0;
        while (index < n) {
            if (index == 0)
                index++;
            if (A[index] >= A[index - 1])
                index++;
            else {
                int temp = A[index];
                A[index] = A[index - 1];
                A[index - 1] = temp;
                index--;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10000;

        System.out.println("Генерування масиву на " + N + " елементів...");

        int[] arr1 = new int[N];
        int[] arr2 = new int[N];

        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            arr1[i] = rand.nextInt(10000);
            arr2[i] = arr1[i];
        }

        //Тест Quick Sort
        long startTime = System.nanoTime();
        quickSort(arr1);
        long endTime = System.nanoTime();

        double durationQuick = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Quick Sort час: " + durationQuick + " сек.");

        //Gnome Sort
        startTime = System.nanoTime();
        gnomeSort(arr2);
        endTime = System.nanoTime();

        double durationGnome = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Gnome Sort час: " + durationGnome + " сек.");
    }
}