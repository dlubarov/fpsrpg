package server;

import java.io.IOException;
import java.net.*;

import net.NetConfig;

import ser.ByteSource;
import server.hand.*;

import msg.*;
import msg.c2s.*;

public class Server {
    private static final DatagramSocket sock;

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
            ByteSource data = new ByteSource(receivePacket.getData(),
                    receivePacket.getOffset(), receivePacket.getLength());
            Message msg = MessageSerializer.singleton.deserialize(data);

            HandlerManager.singleton.forward((ClientMessage) msg, clientAddr);
        }
    }
}
