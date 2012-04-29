package server;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import math.Vector3;

import env.Realm;

public final class Account {
    public static final Realm startingRealm = null; // FIXME
    public static final Vector3 startingPos = Vector3.ZERO;

    private static final AtomicInteger nextID;
    public static final Map<Integer, Account> byID;
    public static final Map<String, Account> byUsername;
    public static final Map<InetAddress, Account> byLastIP;

    public final int id;
    public InetAddress lastIP;
    public final String username, password;
    public Realm realm;
    public Vector3 position;
    public ActivePlayer activeInfo;

    static {
        nextID = new AtomicInteger();
        byID = Collections.synchronizedMap(new HashMap<Integer, Account>());
        byUsername = Collections.synchronizedMap(new HashMap<String, Account>());
        byLastIP = Collections.synchronizedMap(new HashMap<InetAddress, Account>());
    }

    public Account(InetAddress lastIP, String username, String password, Realm realm, Vector3 position) {
        id = nextID.getAndIncrement();
        this.lastIP = lastIP;
        this.username = username;
        this.password = password;
        this.realm = realm;
        this.position = position;
        activeInfo = null;

        byID.put(id, this);
        byUsername.put(username, this);
        byLastIP.put(lastIP, this);
    }

    public Account(InetAddress lastIP, String username, String password) {
        this(lastIP, username, password, startingRealm, startingPos);
    }

    public void enterRealm() {
        activeInfo = new ActivePlayer(this);
        activeInfo.show();
    }

    public void leaveRealm() {
        activeInfo.hide();
        activeInfo = null;
    }
}
