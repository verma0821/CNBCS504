import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter File Name: ");
        String filename = sc.nextLine();
        sc.close();

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started. Waiting for client requests...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream din = new DataInputStream(socket.getInputStream());
                     DataOutputStream dout = new DataOutputStream(socket.getOutputStream())) {

                    System.out.println("Connected with " + socket.getInetAddress().toString());

                    String request = din.readUTF();
                    if (request.equals("start")) {
                        System.out.println("Received 'start' request.");

                        File file = new File(filename);
                        if (!file.exists()) {
                            System.out.println("File not found: " + filename);
                            dout.writeUTF("File not found");
                            dout.flush();
                            continue;
                        }

                        // Send filename & file size
                        dout.writeUTF(file.getName());
                        dout.flush();

                        long fileSize = file.length();
                        dout.writeUTF(Long.toString(fileSize));
                        dout.flush();

                        System.out.println("Sending file: " + filename);
                        System.out.println("File size: " + (fileSize / (1024 * 1024)) + " MB");

                        try (FileInputStream fin = new FileInputStream(file)) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = fin.read(buffer)) != -1) {
                                dout.write(buffer, 0, bytesRead);
                                dout.flush();
                            }
                        }

                        System.out.println("File sent successfully.");
                    } else {
                        dout.writeUTF("Invalid request");
                        dout.flush();
                    }

                } catch (IOException e) {
                    System.out.println("Error handling client request: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to start server: " + e.getMessage());
        }
    }
}
