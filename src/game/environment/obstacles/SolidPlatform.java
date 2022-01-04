package game.environment.obstacles;

public class SolidPlatform extends Obstacle {
    public SolidPlatform(double x, double y) {
        super(x, y, 100, 20, Sprites.PLATFORM.get());
    }
}
