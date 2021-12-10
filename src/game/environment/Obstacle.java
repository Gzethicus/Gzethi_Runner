package game.environment;

import game.Camera;

import static java.lang.Long.MAX_VALUE;

public class Obstacle extends Walkable{

    public Obstacle(int x, int y, int width, int height, Camera cam,String spriteName) {
        super(x, y, width,height,true, cam, spriteName);
        int[]ph1={1};
        long[]ph2={MAX_VALUE};
        this.maxFrame=ph1;
        this.durations=ph2;
    }
    public Obstacle(int x, int y, int width, int height, double forcedSpeed, Camera cam,String spriteName) {
        super(x, y, width,height,true, forcedSpeed, cam, spriteName);
        int[]ph1={1};
        long[]ph2={MAX_VALUE};
        this.maxFrame=ph1;
        this.durations=ph2;
    }
}
