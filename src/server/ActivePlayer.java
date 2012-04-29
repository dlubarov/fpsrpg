package server;

import game.Player;

import java.util.*;

import math.Vector3;

public final class ActivePlayer extends Player {
    private final Account account;
    public Set<Account> neighbors;

    public ActivePlayer(Account account) {
        this.account = account;
        addNeighbors(null); // FIXME
    }

    public void addNeighbors(Collection<ActivePlayer> neighbors) {
        
    }

    @Override
    public Vector3 getPos() {
        return account.position;
    }
}
