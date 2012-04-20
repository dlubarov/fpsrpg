package msg;

public enum MessageType {
    // Client to server.
    // Request a new account registration.
    NEW_ACCOUNT,

    // Server to client.
    // New account request was denied.
    NEW_ACCOUNT_ERROR,
    // Login succeeded, introduce the user's realm
    WELCOME,
    // Introduce a new peer with username, etc.
    PEER_INRODUCTION,
    // Provide a motion update for an already-introduced peer
    PEER_UPDATE,
    // Peer signed out or is no longer in user's proximity
    PEER_GOODBYE;

    private MessageType() {
        if (ordinal() != (byte) ordinal())
            throw new AssertionError("Message type doesn't fit into byte.");
    }
}
