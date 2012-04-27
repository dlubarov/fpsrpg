package server.hand;

import net.NetConfig;
import server.Account;
import msg.*;

public class NewAccountHandler {
    public static final NewAccountHandler singleton = new NewAccountHandler();
    private NewAccountHandler() {}

    private static void err(NewAccountErrorMessage.Cause cause) {
        NewAccountErrorMessage msg = new NewAccountErrorMessage(cause);
        // FIXME send error
    }

    public void handle(NewAccountMessage msg) {
        if (msg.username.length() < NetConfig.USERNAME_MIN_LEN)
            err(NewAccountErrorMessage.Cause.USERNAME_SHORT);
        else if (msg.username.length() > NetConfig.USERNAME_MAX_LEN)
            err(NewAccountErrorMessage.Cause.USERNAME_LONG);
        else if (msg.password.length() < NetConfig.PASSWORD_MIN_LEN)
            err(NewAccountErrorMessage.Cause.PASSWORD_SHORT);
        else if (msg.password.length() > NetConfig.PASSWORD_MAX_LEN)
            err(NewAccountErrorMessage.Cause.PASSWORD_LONG);
        else if (Account.byUsername.containsKey(msg.username))
            err(NewAccountErrorMessage.Cause.USERNAME_TAKEN);
        else {
            // FIXME send welcome
            new Account(msg.username, msg.password);
        }
    }
}
