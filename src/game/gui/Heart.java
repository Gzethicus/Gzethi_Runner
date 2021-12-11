package game.gui;

import static java.lang.Long.MAX_VALUE;

public class Heart extends GUIElement{
    public Heart(int x, int y){
        super(x,y,9,9,"heart.png");
        int[]ph1={1,1};
        long[]ph2={MAX_VALUE,MAX_VALUE};
        this.maxFrame=ph1;
        this.durations=ph2;
    }
}
