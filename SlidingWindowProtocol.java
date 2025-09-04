import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowProtocol {

    private static final int WINDOW_SIZE = 4;   // Size of the sliding window
    private static final int TOTAL_PACKETS = 10; // Total number of packets to send

    // Simulates the sender's sliding window protocol
    public static void sender() {
        Queue<Integer> window = new LinkedList<>();

        // Initially send packets up to window size
        int nextPacketToSend = 0;
        for (int i = 0; i < WINDOW_SIZE && nextPacketToSend < TOTAL_PACKETS; i++) {
            window.add(nextPacketToSend);
            System.out.println("Sent packet: " + nextPacketToSend);
            nextPacketToSend++;
        }

        // Keep sending until all packets are acknowledged
        while (!window.isEmpty()) {
            // Simulate receiving acknowledgment for the packet at the start of the window
            int ack = window.poll();
            System.out.println("Acknowledgment received for packet: " + ack);

            // Slide the window: send the next packet if available
            if (nextPacketToSend < TOTAL_PACKETS) {
                window.add(nextPacketToSend);
                System.out.println("Sent packet: " + nextPacketToSend);
                nextPacketToSend++;
            }

            // Simulate a delay (for demo purposes)
            try {
                Thread.sleep(800); // 0.8 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("✅ All packets sent and acknowledged.");
    }

    public static void main(String[] args) {
        sender();
    }
}
