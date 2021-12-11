package game.gui;

public class LoadingLogo extends GUIElement {
    public LoadingLogo(int x, int y) {
        super(x, y, 51, 50, "loading.png");
        int[]ph1={14};
        long[]ph2={100};
        this.maxFrame=ph1;
        this.durations=ph2;
    }
}
