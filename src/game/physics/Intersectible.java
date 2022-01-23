package game.physics;

import game.entities.assembly.OriginPoint;
import javafx.geometry.Bounds;

public interface Intersectible{
    boolean intersects(Intersectible intersectible);
    Bounds getBounds();
    void setOrigin(OriginPoint origin);
    void setAngle(double angle);
    void refreshBounds();
    void refreshVisual();
    Intersectible clone();
    void addSiblingBox(Intersectible sibling);
    void setMaster(HitBox master);
    void setVisible(boolean visible);
    void setScaleX(double x);
    void setScaleY(double y);
}
