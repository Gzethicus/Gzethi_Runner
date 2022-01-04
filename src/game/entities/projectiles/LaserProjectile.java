package game.entities.projectiles;

import game.State;
import game.environment.rooms.Room;
import javafx.geometry.Rectangle2D;

public class LaserProjectile extends Projectile {
    public LaserProjectile(double x, double y, Room room, double targetX, double targetY, State color, int team) {
        super(x, y, room, 15, targetX, targetY, 1, 1, team, new Rectangle2D(x,y,23,8), color.getDef()==0?Sprites.CYAN_LASER.get():Sprites.RED_LASER.get());
    }
}
