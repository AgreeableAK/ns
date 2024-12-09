import java.util.Scanner;

class Queue {
    int[] q; // Array to store packets
    int f = 0, r = 0, size; // f: front, r: rear, size of the queue

    // Constructor to initialize the queue
    Queue(int size) {
        this.size = size;
        q = new int[size];
    }

    // Method to insert packets into the queue
    void insert(int n) {
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter element " + (i + 1) + ": ");
            int ele = in.nextInt();

            if (r >= size) { // Check if the queue is full
                System.out.println("\nQueue is full. Lost Packet: " + ele);
                break;
            } else {
                q[r] = ele; // Add the packet to the queue
                r++; // Move the rear pointer
            }
        }
    }

    // Method to delete packets (leak packets)
    void delete() {
        if (r == f) { // Check if the queue is empty
            System.out.println("\nQueue empty. No packets to leak.");
        } else {
            for (int i = f; i < r; i++) { // Leak packets one by one
                try {
                    Thread.sleep(1000); // Simulate a 1-second delay between leaks
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("\nLeaked Packet: " + q[f]);
                f++; // Move the front pointer
            }
        }
    }
}

public class Leaky extends Thread {
    public static void main(String[] args) {
        Scanner src = new Scanner(System.in);

        // Input: Queue size and number of packets to send
        System.out.println("Enter the size of the queue:");
        int size = src.nextInt();
        System.out.println("Enter the number of packets to be sent:");
        int n = src.nextInt();

        // Create a queue of the specified size
        Queue q = new Queue(size);

        // Insert and delete packets
        q.insert(n);
        q.delete();

        src.close();
    }
}

