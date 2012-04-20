package server;

import java.io.IOException;
import java.net.*;

import server.hand.NewAccountHandler;

import msg.*;

public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket sock = new DatagramSocket(1337);
        byte[] receiveData = new byte[512];
        for (;;) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            sock.receive(receivePacket);
            receivePacket.getAddress();
            Message msg = MessageSerializer.singleton.deserialize(
                    receivePacket.getData(),
                    receivePacket.getOffset(),
                    receivePacket.getLength());

            switch (msg.type) {
                case NEW_ACCOUNT:
                    NewAccountHandler.singleton.handle((NewAccountMessage) msg);
                    break;
                default:
                    throw new AssertionError("Forgot a case?");
            }
        }
    }
}
