package phys;

import java.util.*;

import math.*;

public final class PointOctree<T extends Positioned> {
    private static final int MIN_SIZE = 3, MAX_SIZE = 8;

    private final Vector3 center;
    private final double radius;
    private Collection<T> bodies;
    private PointOctree<T>[] children = null;
    private int size = 0;

    public PointOctree(Vector3 center, double radius) {
        this.center = center;
        this.radius = radius;
        this.bodies = new ArrayList<T>();
    }

    private boolean hasSplit() {
        return bodies == null;
    }

    private PointOctree<T> childFor(T b) {
        Vector3 p = b.getPos();
        int idx = 0;
        for (int d = 0; d < 3; ++d)
            if (p.get(d) > center.get(d))
                idx |= 1 << d;
        return children[idx];
    }

    @SuppressWarnings("unchecked")
    private void split() {
        assert !hasSplit();
        double x = center.x, y = center.y, z = center.z, r = radius/2;
        children = new PointOctree[] {
                new PointOctree<T>(new Vector3(x - r, y - r, z - r), r),
                new PointOctree<T>(new Vector3(x + r, y - r, z - r), r),
                new PointOctree<T>(new Vector3(x - r, y + r, z - r), r),
                new PointOctree<T>(new Vector3(x + r, y + r, z - r), r),
                new PointOctree<T>(new Vector3(x - r, y - r, z + r), r),
                new PointOctree<T>(new Vector3(x + r, y - r, z + r), r),
                new PointOctree<T>(new Vector3(x - r, y + r, z + r), r),
                new PointOctree<T>(new Vector3(x + r, y + r, z + r), r),
        };
        for (T b : bodies) {
            PointOctree<T> child = childFor(b);
            child.add(b);
        }
        bodies = null;
    }

    private void unsplit() {
        assert hasSplit();
        bodies = new ArrayList<T>();
        for (PointOctree<T> child : children)
            child.addBodiesTo(bodies);
        children = null;
    }

    private void addBodiesTo(Collection<T> result) {
        if (hasSplit())
            for (PointOctree<T> child : children)
                child.addBodiesTo(result);
        else
            result.addAll(bodies);
    }

    public void add(T b) {
        if (hasSplit())
            childFor(b).add(b);
        else
            bodies.add(b);

        if (++size > MAX_SIZE && !hasSplit())
            split();
    }

    public void remove(T b) {
        if (hasSplit())
            childFor(b).remove(b);
        else
            bodies.remove(b);

        if (--size < MIN_SIZE && hasSplit())
            unsplit();
    }

    private void addNeighborsTo(T source, double dist, Collection<T> result) {
        Vector3 pos = source.getPos();
        for (int d = 0; d < 3; ++d)
            if (Math.abs(center.get(d) - pos.get(d)) > radius + dist)
                return;

        if (hasSplit())
            for (PointOctree<T> child : children)
                child.addNeighborsTo(source, dist, result);
        else
            for (T b : bodies)
                if (b != source && pos.distanceTo(b.getPos()) <= dist)
                    result.add(b);
    }

    public Collection<T> getNeighbors(T source, double dist) {
        Collection<T> result = new ArrayList<T>();
        addNeighborsTo(source, dist, result);
        return result;
    }
}
