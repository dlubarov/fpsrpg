package phys;

import math.Shape;

public abstract class Body {
    public abstract Shape getShape();

    public void show() {
        Broadphase.globalBroadphase.add(this);
    }

    public void hide() {
        Broadphase.globalBroadphase.remove(this);
    }
}
