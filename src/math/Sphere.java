package math;

public class Sphere extends Shape {
    public final Vector3 center;
    public final double radius;

    public Sphere(Vector3 center, double radius) {
        assert radius > 0;
        this.center = center;
        this.radius = radius;
    }

    @Override
    public AAB boundingBox() {
        Vector3 rrr = new Vector3(radius, radius, radius);
        return new AAB(center.minus(rrr), center.plus(rrr));
    }

    @Override
    public Sphere boundingSphere() {
        return this;
    }

    public boolean intersects(Sphere that) {
        return center.distanceTo(that.center) <= radius + that.radius;
    }

    @Override
    public String toString() {
        return String.format("Sphere(center=%s, radius=%f)", center, radius);
    }
}
