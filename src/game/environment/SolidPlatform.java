package game.environment;

import game.Camera;

import static java.lang.Long.MAX_VALUE;

public class SolidPlatform extends Obstacle{
    public SolidPlatform(int x, int y, Camera cam) {
        super(x, y, 100, 20, cam, "platform.png");
        int[]ph1={1};
        long[]ph2={MAX_VALUE};
        this.maxFrame=ph1;
        this.durations=ph2;
    }
}
