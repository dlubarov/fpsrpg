package math;

public class NormalizedVector3 extends Vector3 {
    public static final NormalizedVector3
            UNIT_X = new NormalizedVector3(1, 0, 0),
            UNIT_Y = new NormalizedVector3(0, 1, 0),
            UNIT_Z = new NormalizedVector3(0, 0, 1);

    private NormalizedVector3(double x, double y, double z) {
        super(x, y, z);
    }

    public static NormalizedVector3 from(Vector3 v) {
        double ni = 1.0 / v.norm(); // inverse of norm
        return new NormalizedVector3(v.x*ni, v.y*ni, v.z*ni);
    }

    @Override
    public double normSquared() {
        return 1.0;
    }

    @Override
    public double norm() {
        return 1.0;
    }

    @Override
    public NormalizedVector3 normalized() {
        return this;
    }
}
