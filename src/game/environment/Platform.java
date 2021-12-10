package game.environment;

import game.Camera;

import static java.lang.Long.MAX_VALUE;

public class Platform extends Walkable{
    public Platform(int x, int y, Camera cam) {
        super(x, y, 100, 20, false, cam, "platform.png");
        int[]ph1={1};
        long[]ph2={MAX_VALUE};
        this.maxFrame=ph1;
        this.durations=ph2;
    }
}
