package game;

import game.entities.Entity;
import game.entities.players.Player;
import javafx.geometry.Rectangle2D;

public class Camera {
    private int x;
    private int y;
    private double vX=0;
    private double vY=0;
    private int onScreenNumber=0;
    private Entity target;

    public Camera(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX(){
        return(x);
    }

    public int getY(){
        return(y);
    }

    public void update() {
        //horizontal tracking
        double a=5*((this.target.getHitBox().getMinX()+this.target.getHitBox().getMaxX())/2-this.x-(this.target.isFacingRight()?500:700))-10*this.vX;
        this.vX=this.vX+a*0.01;
        this.x=(int)(this.x+vX*0.1);

        //vertical tracking
        a=50*(this.target.getHitBox().getMinY()-this.y-150)-30*this.vY;
        this.vY=this.vY+a*0.01;
        this.y=(int)(this.y+vY*0.1);

        //looping
        if(this.x>=800*(this.onScreenNumber +1)){
            this.onScreenNumber +=1;
        }
    }

    public void setTarget(Entity target){this.target=target;}
}
