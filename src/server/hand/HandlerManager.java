package server.hand;

import java.net.InetAddress;
import java.util.*;

import msg.c2s.ClientMessage;

public class HandlerManager {
    public static final HandlerManager singleton = new HandlerManager();

    static {
        // Force static initializers to be run.
        @SuppressWarnings("unused")
        Object[] handlers = new Object[] {
                NewAccountHandler.singleton
        };
    }

    private final Map<Class<? extends ClientMessage>, ClientMessageHandler<? extends ClientMessage>> handlers;

    private HandlerManager() {
        handlers = new HashMap<Class<? extends ClientMessage>, ClientMessageHandler<? extends ClientMessage>>();
    }

    public <T extends ClientMessage> void register(Class<T> msgType, ClientMessageHandler<T> handler) {
        if (handlers.containsKey(msgType))
            throw new RuntimeException("Handler already registered for " + msgType);
        handlers.put(msgType, handler);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void forward(ClientMessage msg, InetAddress sender) {
        // Intentionally abusing type erasure here...
        ClientMessageHandler hand = handlers.get(msg.getClass());
        hand.handle(msg, sender);
    }
}
