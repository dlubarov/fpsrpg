package server.hand;

import java.net.InetAddress;

import net.NetConfig;
import server.Account;
import msg.c2s.NewAccountMessage;
import msg.s2c.*;

public class NewAccountHandler extends ClientMessageHandler<NewAccountMessage> {
    public static final NewAccountHandler singleton = new NewAccountHandler();

    private NewAccountHandler() {
        super(NewAccountMessage.class);
    }

    private static void err(NewAccountErrorMessage.Cause cause, InetAddress clientAddr) {
        NewAccountErrorMessage msg = new NewAccountErrorMessage(cause);
        msg.sendTo(clientAddr);
    }

    @Override
    public void handle(NewAccountMessage msg, InetAddress sender) {
        if (msg.username.length() < NetConfig.USERNAME_MIN_LEN)
            err(NewAccountErrorMessage.Cause.USERNAME_SHORT, sender);
        else if (msg.username.length() > NetConfig.USERNAME_MAX_LEN)
            err(NewAccountErrorMessage.Cause.USERNAME_LONG, sender);
        else if (msg.password.length() < NetConfig.PASSWORD_MIN_LEN)
            err(NewAccountErrorMessage.Cause.PASSWORD_SHORT, sender);
        else if (msg.password.length() > NetConfig.PASSWORD_MAX_LEN)
            err(NewAccountErrorMessage.Cause.PASSWORD_LONG, sender);
        else if (Account.byUsername.containsKey(msg.username))
            err(NewAccountErrorMessage.Cause.USERNAME_TAKEN, sender);
        else {
            Account acc = new Account(sender, msg.username, msg.password);
            new WelcomeMessage(acc.realm).sendTo(acc);
        }
    }
}
