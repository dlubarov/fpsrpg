package msg.c2s;

import ser.Serializer;
import client.Client;

import msg.*;

public abstract class ClientMessage extends Message {
    protected ClientMessage(Serializer<? extends Message> serializer) {
        super(serializer);
    }

    public void send() {
        Client.sendToServer(serializeWithType());
    }
}
