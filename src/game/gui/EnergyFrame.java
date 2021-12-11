package game.gui;

import static java.lang.Long.MAX_VALUE;

public class EnergyFrame extends GUIElement{
    public EnergyFrame(int x, int y){
        super(x,y,44,9,"energyFrame.png");
        int[]ph1={1};
        long[]ph2={MAX_VALUE};
        this.maxFrame=ph1;
        this.durations=ph2;
    }
}
