package msg.s2c;

import java.net.*;

import msg.*;

import server.*;

public abstract class ServerMessage extends Message {
    protected ServerMessage(MessageType type) {
        super(type);
    }

    public void sendTo(InetAddress clientAddr) {
        Server.sendClient(serialize(), clientAddr);
    }

    public void sendTo(Account account) {
        sendTo(account.lastIP);
    }
}
