package phys;

import java.util.ArrayList;
import java.util.Collection;

import math.*;

public final class Octree extends Broadphase {
    private static final int MIN_SIZE = 3, MAX_SIZE = 8;

    public final Vector3 center;
    public final double radius;
    private Octree[] children = null;
    private final Collection<Body> bodies;
    private int size = 0;

    public Octree(Vector3 center, double radius) {
        this.center = center;
        this.radius = radius;
        bodies = new ArrayList<Body>();
    }

    private boolean hasSplit() {
        return children != null;
    }

    private void split() {
        double x = center.x, y = center.y, z = center.z, r = radius/2;
        children = new Octree[] {
                new Octree(new Vector3(x - r, y - r, z - r), r),
                new Octree(new Vector3(x + r, y - r, z - r), r),
                new Octree(new Vector3(x - r, y + r, z - r), r),
                new Octree(new Vector3(x + r, y + r, z - r), r),
                new Octree(new Vector3(x - r, y - r, z + r), r),
                new Octree(new Vector3(x + r, y - r, z + r), r),
                new Octree(new Vector3(x - r, y + r, z + r), r),
                new Octree(new Vector3(x + r, y + r, z + r), r),
        };
        for (Body b : bodies) {
            Octree child = childFor(b);
            if (child != null)
                child.add(b);
        }
    }

    private void unsplit() {
        for (Octree child : children)
            child.addBodiesTo(bodies);
        children = null;
    }

    private void addBodiesTo(Collection<Body> result) {
        result.addAll(bodies);
        if (hasSplit())
            for (Octree child : children)
                child.addBodiesTo(result);
    }

    private boolean onBoundary(Body b) {
        AAB bb = b.getShape().boundingBox();
        for (int d = 0; d < 3; ++d)
            if (bb.min.get(d) <= center.get(d) && center.get(d) <= bb.max.get(d))
                return true;
        return false;
    }

    private Octree childFor(Body b) {
        if (!hasSplit() || onBoundary(b))
            return null;

        AAB bb = b.getShape().boundingBox();
        int idx = 0;
        for (int d = 0; d < 3; ++d)
            if (bb.min.get(d) > center.get(d))
                idx |= 1 << d;
        return children[idx];
    }

    @Override
    public void add(Body b) {
        Octree child = childFor(b);
        if (child == null)
            bodies.add(b);
        else
            child.add(b);

        if (++size > MAX_SIZE && !hasSplit())
            split();
    }

    @Override
    public void remove(Body b) {
        Octree child = childFor(b);
        if (child == null)
            bodies.remove(b);
        else
            child.remove(b);

        if (--size < MIN_SIZE && hasSplit())
            unsplit();
    }

    private AAB boundingBox() {
        Vector3 rrr = new Vector3(radius, radius, radius);
        return new AAB(center.minus(rrr), center.plus(rrr));
    }

    public void addNeighborsTo(Body src, Collection<Body> result) {
        AAB srcBB = src.getShape().boundingBox();
        if (!boundingBox().intersects(srcBB))
            return;

        if (hasSplit())
            for (Octree child : children)
                child.addNeighborsTo(src, result);
        else
            for (Body b : bodies)
                if (b != src && b.getShape().boundingBox().intersects(srcBB))
                    result.add(b);
    }

    @Override
    public Collection<Body> getNeighbors(Body src) {
        Collection<Body> result = new ArrayList<Body>();
        addNeighborsTo(src, result);
        return result;
    }
}
