package msg.s2c;

import java.net.*;

import msg.*;

import ser.Serializer;
import server.*;

public abstract class ServerMessage extends Message {
    protected ServerMessage(Serializer<? extends Message> serializer) {
        super(serializer);
    }

    public void sendTo(InetAddress clientAddr) {
        Server.sendClient(serializeWithType(), clientAddr);
    }

    public void sendTo(Account account) {
        sendTo(account.lastIP);
    }
}
