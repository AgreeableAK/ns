import java.util.Scanner;

public class BellmanFord {

    private int D[]; // Array to store the shortest distances from the source
    private int num_ver; // Number of vertices in the graph
    public static final int MAX_VALUE = 999; // Represents infinity (no edge)

    // Constructor
    public BellmanFord(int num_ver) {
        this.num_ver = num_ver;
        D = new int[num_ver + 1];
    }

    // Method to perform Bellman-Ford evaluation
    public void BellmanFordEvaluation(int source, int A[][]) {
        // Step 1: Initialize distances from source to all vertices
        for (int node = 1; node <= num_ver; node++) {
            D[node] = MAX_VALUE;
        }
        D[source] = 0; // Distance to the source itself is 0

        // Step 2: Relax edges repeatedly
        for (int node = 1; node <= num_ver - 1; node++) { // Repeat num_ver - 1 times
            for (int sn = 1; sn <= num_ver; sn++) { // Loop through all source vertices
                for (int dn = 1; dn <= num_ver; dn++) { // Loop through all destination vertices
                    if (A[sn][dn] != MAX_VALUE) { // If there's an edge
                        if (D[dn] > D[sn] + A[sn][dn]) { // Relax the edge
                            D[dn] = D[sn] + A[sn][dn];
                        }
                    }
                }
            }
        }

        // Step 3: Check for negative weight cycles
        for (int sn = 1; sn <= num_ver; sn++) {
            for (int dn = 1; dn <= num_ver; dn++) {
                if (A[sn][dn] != MAX_VALUE) {
                    if (D[dn] > D[sn] + A[sn][dn]) { // If we can still relax, there's a cycle
                        System.out.println("The Graph contains a negative edge cycle");
                    }
                }
            }
        }

        // Step 4: Print distances from the source to all vertices
        for (int vertex = 1; vertex <= num_ver; vertex++) {
            System.out.println("Distance of source " + source + " to " + vertex + " is " + D[vertex]);
        }
    }

    // Main method
    public static void main(String[] args) {
        int num_ver = 0; // Number of vertices
        int source; // Source vertex

        Scanner scanner = new Scanner(System.in);

        // Input: Number of vertices
        System.out.println("Enter the number of vertices:");
        num_ver = scanner.nextInt();

        int A[][] = new int[num_ver + 1][num_ver + 1]; // Adjacency matrix

        // Input: Adjacency matrix
        System.out.println("Enter the adjacency matrix:");
        for (int sn = 1; sn <= num_ver; sn++) {
            for (int dn = 1; dn <= num_ver; dn++) {
                A[sn][dn] = scanner.nextInt();
                if (sn == dn) { // Distance to itself is 0
                    A[sn][dn] = 0;
                    continue;
                }
                if (A[sn][dn] == 0) { // Replace 0 with MAX_VALUE for no edge
                    A[sn][dn] = MAX_VALUE;
                }
            }
        }

        // Input: Source vertex
        System.out.println("Enter the source vertex:");
        source = scanner.nextInt();

        // Perform Bellman-Ford algorithm
        BellmanFord b = new BellmanFord(num_ver);
        b.BellmanFordEvaluation(source, A);

        scanner.close(); // Close the scanner
    }
}

