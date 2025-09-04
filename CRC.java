import java.util.*;

class CRC {
    void div(int a[], int k) {
        int gp[] = {1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1}; // Generator Polynomial
        int gpLen = gp.length;

        for (int i = 0; i < k; i++) {
            if (a[i] == 1) {  // Only divide if leading bit is 1
                for (int j = 0; j < gpLen; j++) {
                    a[i + j] = a[i + j] ^ gp[j];
                }
            }
        }
    }

    public static void main(String args[]) {
        int a[] = new int[100];
        int b[] = new int[100];
        int len, k;
        CRC ob = new CRC();

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the length of Data Frame:");
        len = sc.nextInt();

        System.out.println("Enter the Message (bit by bit):");
        for (int i = 0; i < len; i++) {
            a[i] = sc.nextInt();
        }

        // Append 16 zeros (degree of generator - 1)
        for (int i = 0; i < 16; i++) {
            a[len + i] = 0;
        }
        len += 16;  
        k = len - 16;

        // Copy message for later XOR
        for (int i = 0; i < len; i++) {
            b[i] = a[i];
        }

        // Perform division
        ob.div(a, k);

        // XOR to get transmitted data
        for (int i = 0; i < len; i++) {
            a[i] = a[i] ^ b[i];
        }

        System.out.println("Data to be transmitted:");
        for (int i = 0; i < len; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        // Receiver side
        System.out.println("Enter the Received Data:");
        for (int i = 0; i < len; i++) {
            a[i] = sc.nextInt();
        }

        ob.div(a, k);

        boolean error = false;
        for (int i = 0; i < len; i++) {
            if (a[i] != 0) {
                error = true;
                break;
            }
        }

        if (error)
            System.out.println("ERROR in data");
        else
            System.out.println("NO ERROR");
    }
}
