package game.entities.assembly;

import game.entities.Creature;

public interface Attachable{
    OriginPoint getOrigin();
    void update(long time);
    void setMirror(boolean mirror);
    void setViewOrder(double v);
    void setOwner(Creature owner);
    void setFacingRight(boolean facingRight);
}
