import java.util.Scanner;

public class BellmanFord {
    private int[] D; // Distance array
    private int n;   // Number of vertices
    public static final int MAX_VALUE = 999; // Representing infinity

    public BellmanFord(int n) {
        this.n = n;
        D = new int[n + 1];
    }

    public void shortest(int s, int A[][]) {
        // Step 1: Initialize distances
        for (int i = 1; i <= n; i++) {
            D[i] = MAX_VALUE;
        }
        D[s] = 0;

        // Step 2: Relax edges (n-1) times
        for (int k = 1; k <= n - 1; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (A[i][j] != MAX_VALUE && D[i] != MAX_VALUE) {
                        if (D[j] > D[i] + A[i][j]) {
                            D[j] = D[i] + A[i][j];
                        }
                    }
                }
            }
        }

        // Step 3: Check for negative-weight cycles
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (A[i][j] != MAX_VALUE && D[i] != MAX_VALUE) {
                    if (D[j] > D[i] + A[i][j]) {
                        System.out.println("❌ The Graph contains a negative edge cycle");
                        return;
                    }
                }
            }
        }

        // Step 4: Print shortest paths
        for (int i = 1; i <= n; i++) {
            System.out.println("Distance of source " + s + " to " + i + " is " + D[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of vertices:");
        int n = sc.nextInt();

        int[][] A = new int[n + 1][n + 1];

        System.out.println("Enter the Weighted Adjacency Matrix:");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                A[i][j] = sc.nextInt();

                if (i == j) {
                    A[i][j] = 0; // Distance to itself is 0
                } else if (A[i][j] == 0) {
                    A[i][j] = MAX_VALUE; // No edge → infinity
                }
            }
        }

        System.out.println("Enter the source vertex:");
        int s = sc.nextInt();

        BellmanFord b = new BellmanFord(n);
        b.shortest(s, A);

        sc.close();
    }
}
