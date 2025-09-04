import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

class Client {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Server Address: ");
        String address = sc.nextLine();

        try (Socket s = new Socket(address, 5000);
             DataInputStream din = new DataInputStream(s.getInputStream());
             DataOutputStream dout = new DataOutputStream(s.getOutputStream());
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Type 'start' to begin file transfer...");
            String str = "";
            while (!str.equals("start")) {
                str = br.readLine();
            }

            // Send request to server
            dout.writeUTF(str);
            dout.flush();

            // Receive file details
            String filename = din.readUTF();
            if (filename.equals("File not found") || filename.equals("Invalid request")) {
                System.out.println("Server response: " + filename);
                return;
            }

            System.out.println("Receiving file: " + filename);
            filename = "client_" + filename;
            System.out.println("Saving as: " + filename);

            long sz = Long.parseLong(din.readUTF());
            System.out.println("File Size: " + (sz / (1024 * 1024)) + " MB");

            byte[] buffer = new byte[1024];
            try (FileOutputStream fos = new FileOutputStream(new File(filename), true)) {
                int bytesRead;
                System.out.println("Receiving file...");
                while ((bytesRead = din.read(buffer, 0, buffer.length)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                System.out.println("File received successfully.");
            }
        } catch (EOFException e) {
            System.out.println("Connection closed unexpectedly.");
        }
    }
}
