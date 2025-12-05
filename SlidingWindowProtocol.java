import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowProtocol {

    private static final int WINDOW_SIZE = 4;
    private static final int TOTAL_PACKETS = 10;

    public static void sender() {
        Queue<Integer> window = new LinkedList<>();

        for (int i = 0; i < WINDOW_SIZE; i++) {
            if (i < TOTAL_PACKETS) {
                window.add(i);
                System.out.println("Sent packet: " + i);
            }
        }

        int nextPacketToSend = WINDOW_SIZE;

        while (nextPacketToSend < TOTAL_PACKETS || !window.isEmpty()) {
            if (!window.isEmpty()) {
                int ack = window.poll();
                System.out.println("Acknowledgment received for packet: " + ack);
            }

            if (nextPacketToSend < TOTAL_PACKETS) {
                window.add(nextPacketToSend);
                System.out.println("Sent packet: " + nextPacketToSend);
                nextPacketToSend++;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All packets sent and acknowledged.");
    }

    public static void main(String[] args) {
        sender();
    }
}
