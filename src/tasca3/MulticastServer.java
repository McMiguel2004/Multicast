package tasca3;

import java.net.*;
import java.util.Random;

public class MulticastServer {
    public static void main(String[] args) {
        try {
            MulticastSocket multicastSocket = new MulticastSocket();
            InetAddress group = InetAddress.getByName("230.0.0.1");
            multicastSocket.joinGroup(group);

            String[] words = {"apple", "banana", "orange", "grape", "melon"};
            Random random = new Random();

            while (true) {
                String randomWord = words[random.nextInt(words.length)];
                byte[] sendData = randomWord.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, 9876);
                multicastSocket.send(sendPacket);
                Thread.sleep(2000); // Wait for 2 seconds before sending the next word
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
