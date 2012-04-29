package net;

import java.net.*;

public final class NetConfig {
    private NetConfig() {}

    public static final InetAddress serverAddr;

    static {
        try {
            serverAddr = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static final int
            PORT_C2S = 1337, PORT_S2C = 1338,
            USERNAME_MIN_LEN = 3, USERNAME_MAX_LEN = 32,
            PASSWORD_MIN_LEN = 6, PASSWORD_MAX_LEN = 256;
}
