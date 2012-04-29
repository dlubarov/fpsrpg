package server;

import java.io.IOException;
import java.net.*;

import net.NetConfig;

import server.hand.NewAccountHandler;

import msg.*;
import msg.c2s.NewAccountMessage;

public class Server {
    private final static DatagramSocket sock;

    static {
        try {
            sock = new DatagramSocket(NetConfig.PORT_C2S);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendClient(byte[] data, InetAddress clientAddr) {
        try {
            sock.send(new DatagramPacket(data, data.length, clientAddr, NetConfig.PORT_S2C));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        byte[] receiveData = new byte[512];

        for (;;) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            sock.receive(receivePacket);
            InetAddress clientAddr = receivePacket.getAddress();
            Message msg = MessageSerializer.singleton.deserialize(
                    receivePacket.getData(),
                    receivePacket.getOffset(),
                    receivePacket.getLength());

            switch (msg.type) {
                case NEW_ACCOUNT:
                    NewAccountHandler.singleton.handle((NewAccountMessage) msg, clientAddr);
                    break;
                default:
                    throw new AssertionError("Forgot a case?");
            }
        }
    }
}
