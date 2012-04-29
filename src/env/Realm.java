package env;

import java.util.concurrent.atomic.AtomicInteger;

import phys.*;
import math.Vector3;

public abstract class Realm {
    private static final AtomicInteger nextID = new AtomicInteger();

    public static final Realm[] allRealms = {
            new BareRealm(1000)
    };

    public final int id;
    public final Broadphase broadphase;

    protected Realm(double size) {
        id = nextID.getAndIncrement();
        broadphase = new Octree(Vector3.ZERO, size);
    }
}
