package game.environment.obstacles;

import game.AnimatedSprite;
import game.environment.Walkable;

public class Obstacle extends Walkable {
    public Obstacle(double x, double y, int width, int height, AnimatedSprite sprite) {
        super(x, y, width,height,true, sprite);
    }
    public Obstacle(double x, double y, int width, int height, double forcedSpeed,AnimatedSprite sprite) {
        super(x, y, width,height,true, forcedSpeed, sprite);
    }
}
