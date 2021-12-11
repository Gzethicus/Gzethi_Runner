package game.entities.projectiles;

import game.Camera;
import game.entities.projectiles.Projectile;
import javafx.geometry.Rectangle2D;

import static java.lang.Long.MAX_VALUE;

public class LaserProjectile extends Projectile {
    public LaserProjectile(int x, int y, int targetX, int targetY, int color, int team, Camera cam) {
        super(x, y, 23, 8, 15, targetX,targetY, 1, team, new Rectangle2D(x,y,23,8), cam, "laser.png");
        int[]ph1={1,1};
        long[]ph2={MAX_VALUE,MAX_VALUE};
        this.maxFrame=ph1;
        this.durations=ph2;
        this.state=color;
    }
}
