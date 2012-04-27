package math;

public class AAB extends Shape {
    public final Vector3 min, max;

    public AAB(Vector3 min, Vector3 max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public AAB boundingBox() {
        return this;
    }

    @Override
    public Sphere boundingSphere() {
        Vector3 center = min.averagedWith(max);
        double radius = max.minus(center).norm();
        return new Sphere(center, radius);
    }

    public boolean intersects(AAB that) {
        for (int d = 0; d < 3; ++d)
            if (min.get(d) > that.max.get(d) || max.get(d) < that.min.get(d))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("AAB(%s, %s)", min, max);
    }
}
