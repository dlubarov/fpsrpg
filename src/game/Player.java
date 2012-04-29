package game;

import math.*;
import phys.Body;

public abstract class Player extends Body {
    public abstract Vector3 getPos();

    @Override
    public Shape getShape() {
        return new Sphere(getPos(), 10);
    }
}
