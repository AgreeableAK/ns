import java.util.Scanner;

class Queue {
    int[] q;
    int f = 0, r = 0, size;

    Queue(int size) {
        this.size = size;
        q = new int[size];
    }

    void insert(int n) {
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter element " + (i + 1) + ": ");
            int ele = in.nextInt();

            if (r >= size) {
                System.out.println("\nQueue is full. Lost Packet: " + ele);
                break;
            } else {
                q[r] = ele;
                r++;
            }
        }
    }

    void delete() {
        if (r == f) {
            System.out.println("\nQueue empty. No packets to leak.");
        } else {
            for (int i = f; i < r; i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("\nLeaked Packet: " + q[f]);
                f++;
            }
        }
    }
}

public class Leaky extends Thread {
    public static void main(String[] args) {
        Scanner src = new Scanner(System.in);

        System.out.println("Enter the size of the queue:");
        int size = src.nextInt();
        System.out.println("Enter the number of packets to be sent:");
        int n = src.nextInt();

        Queue q = new Queue(size);
        q.insert(n);
        q.delete();

        src.close();
    }
}
