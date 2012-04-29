package msg.c2s;

import client.Client;

import msg.*;

public abstract class ClientMessage extends Message {
    protected ClientMessage(MessageType type) {
        super(type);
    }

    public void send() {
        Client.sendToServer(serialize());
    }
}
