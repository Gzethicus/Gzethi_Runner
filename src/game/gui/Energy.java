package game.gui;

import javafx.geometry.Rectangle2D;

public class Energy extends GUIElement{


    public Energy(int x, int y){
        super(x,y,40,5,"energy.png");
    }

    public void setEnergy(int width){
        this.iv.setViewport(new Rectangle2D(this.bWidth*this.frame,this.bHeight*this.state,width,this.bHeight));
    }
}
