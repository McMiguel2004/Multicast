package tasca3;

import java.net.*;

public class MulticastClient {
    public static void main(String[] args) {
        try {
            MulticastSocket multicastSocket = new MulticastSocket(9876);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            multicastSocket.joinGroup(group);

            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                multicastSocket.receive(receivePacket);

                String word = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received word: " + word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
