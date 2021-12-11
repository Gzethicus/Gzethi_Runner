package game.gui;

import static java.lang.Integer.MAX_VALUE;

public class CharacterFrame extends GUIElement {
    public CharacterFrame(int x, int y) {
        super(x, y, 200, 250, "characterFrame.png");
        int[]ph1={1,1,1};
        long[]ph2={MAX_VALUE,MAX_VALUE,MAX_VALUE};
        this.maxFrame=ph1;
        this.durations=ph2;
    }
}