package game.environment;

import game.Camera;
import game.WorldElement;

public class Decor extends WorldElement {
    public Decor(int x, int y, int width, int height, Camera cam, String spriteName) {
        super(x, y, width, height, cam, "decor\\"+spriteName);
    }
}
