package game;

import math.*;
import phys.*;

public abstract class Player extends Body implements Positioned {
    @Override
    public abstract Vector3 getPos();

    @Override
    public Shape getShape() {
        return new Sphere(getPos(), 10);
    }
}
