package phys;

import env.Realm;
import math.Shape;

public abstract class Body {
    public Realm realm;

    public abstract Shape getShape();

    public void show() {
        realm.broadphase.add(this);
    }

    public void hide() {
        realm.broadphase.remove(this);
    }
}
