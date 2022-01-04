package game.environment.platforms;

import game.environment.Walkable;

public class Platform extends Walkable {
    public Platform(double x, double y) {
        super(x, y, 100, 20, false, Sprites.PLATFORM.get());
    }
}
