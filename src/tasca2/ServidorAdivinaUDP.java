package tasca2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;


public class ServidorAdivinaUDP {


    DatagramSocket socket;
    int port, fi;
    SecretNum ns;
    boolean acabat;

    public ServidorAdivinaUDP(int port, int max) {
        try {
            socket = new DatagramSocket(port);
            System.out.printf("Servidor obert pel port %d%n",port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.port = port;
        ns = new SecretNum(max);
        acabat = false;
        fi=-1;
    }

    public void runServer() throws IOException{
        byte [] receivingData = new byte[4];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        //el servidor atén el port indefinidament
        while(!acabat){

            //creació del paquet per rebre les dades
            DatagramPacket packet = new DatagramPacket(receivingData, 4);
            //espera de les dades
            socket.receive(packet);
            //processament de les dades rebudes i obtenció de la resposta
            sendingData = processData(packet.getData(), packet.getLength());
            //obtenció de l'adreça del client
            clientIP = packet.getAddress();
            //obtenció del port del client
            clientPort = packet.getPort();
            //creació del paquet per enviar la resposta
            packet = new DatagramPacket(sendingData, sendingData.length,
                    clientIP, clientPort);
            //enviament de la resposta
            socket.send(packet);
        }
        socket.close();
    }

    private byte[] processData(byte[] data, int length) {
        int nombre = ByteBuffer.wrap(data).getInt();
        System.out.println("rebut->"+nombre);
        fi = ns.comprova(nombre);
        if(fi==0) acabat=true;
        byte[] resposta = ByteBuffer.allocate(4).putInt(fi).array();
        return resposta;
    }

    public static void main(String[] args) throws SocketException, IOException {
        ServidorAdivinaUDP sAdivina = new ServidorAdivinaUDP(5556, 100);

        try {
            sAdivina.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Fi Servidor");



    }

}