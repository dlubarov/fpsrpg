package math;

public abstract class Shape {
    public AAB boundingBox() {
        return boundingSphere().boundingBox();
    }

    public Sphere boundingSphere() {
        return boundingBox().boundingSphere();
    }
}
