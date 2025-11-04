package algo;

public class lab1 {
    public static void main(String[] args) {
        double[] a = {1.2, 3.4, 5.6, 7.8, 9.0, 2.3, 4.5, 6.7, 8.9, 10.1,
                -1.1, 0.0, 2.2, 4.4, 6.6, 8.8, 10.0, 12.2, 14.4, 16.6};

        for (int i = 0; i < 10; i++) {
            double x = a[i];
            double y = a[10 + i];

            a[i] = Math.max(x, y);
            a[10 + i] = Math.min(x, y);
        }

        System.out.println("Після перетворення:");
        for (double value : a) {
            System.out.print(value + " ");
        }
    }
}
