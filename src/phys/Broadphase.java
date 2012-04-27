package phys;

import java.util.Collection;

import math.Vector3;

public abstract class Broadphase {
    public static final Broadphase globalBroadphase = new Octree(Vector3.ZERO, 1000);

    public abstract void add(Body b);

    public abstract void remove(Body b);

    public abstract Collection<Body> getNeighbors(Body src);
}
