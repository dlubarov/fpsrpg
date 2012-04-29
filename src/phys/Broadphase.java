package phys;

import java.util.Collection;

public abstract class Broadphase {
    public abstract void add(Body b);

    public abstract void remove(Body b);

    public abstract Collection<Body> getNeighbors(Body src);
}
