package server.hand;

import java.net.InetAddress;

import msg.c2s.ClientMessage;

public abstract class ClientMessageHandler<T extends ClientMessage> {
    protected ClientMessageHandler(Class<T> msgType) {
        HandlerManager.singleton.register(msgType, this);
    }

    public abstract void handle(T msg, InetAddress sender);
}
