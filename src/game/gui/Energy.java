package game.gui;

public class Energy extends GUIElement{
    public Energy(int x, int y){
        super(x,y,40,5,"energy.png");
        int[]ph1={5};
        long[]ph2={100};
        this.maxFrame=ph1;
        this.durations=ph2;
    }

    public void setEnergy(int width){
        this.width=width;
    }
}
