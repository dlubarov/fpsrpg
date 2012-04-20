package math;

import static java.lang.Math.*;

import java.util.Arrays;

public class Vector3 {
    public final double x, y, z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double[] components() {
        return new double[] {x, y, z};
    }

    public Vector3 withX(double x) {
        return new Vector3(x, y, z);
    }

    public Vector3 withY(double y) {
        return new Vector3(x, y, z);
    }

    public Vector3 withZ(double z) {
        return new Vector3(x, y, z);
    }

    public Vector3 neg() {
        return new Vector3(-x, -y, -z);
    }

    public Vector3 plus(Vector3 that) {
        return new Vector3(x + that.x, y + that.y, z + that.z);
    }

    public Vector3 minus(Vector3 that) {
        return new Vector3(x - that.x, y - that.y, z - that.z);
    }

    public Vector3 scaled(double s) {
        return new Vector3(x*s, y*s, z*s);
    }

    public double dot(Vector3 that) {
        return x*that.x + y*that.y + z*that.z;
    }

    public double normSquared() {
        return x*x + y*y + z*z;
    }

    public double norm() {
        return sqrt(normSquared());
    }

    public NormalizedVector3 normalized() {
        return NormalizedVector3.from(this);
    }

    @Override
    public boolean equals(Object o) {
        try {
            Vector3 that = (Vector3) o;
            return x == that.x && y == that.y && z == that.z;
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(components());
    }

    @Override
    public String toString() {
        return String.format("[%.2f, %.2f, %.2f]", x, y, z);
    }
}
