package server;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import math.Vector3;

import env.Realm;

public class Account {
    public final int id;
    public final String username, password;
    public Realm realm;
    public Vector3 position;

    private static final AtomicInteger nextId;
    public static final Map<Integer, Account> byId;
    public static final Map<String, Account> byUsername;

    static {
        nextId = new AtomicInteger();
        byId = Collections.synchronizedMap(new HashMap<Integer, Account>());
        byUsername = Collections.synchronizedMap(new HashMap<String, Account>());
    }

    public Account(String username, String password) {
        id = nextId.getAndIncrement();
        this.username = username;
        this.password = password;

        byId.put(id, this);
        byUsername.put(username, this);
    }
}
