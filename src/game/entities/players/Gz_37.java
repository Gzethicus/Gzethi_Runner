package game.entities.players;

import game.Camera;
import javafx.geometry.Rectangle2D;

import static java.lang.Long.MAX_VALUE;

public class Gz_37 extends Player{
    public Gz_37(int x, int y, int health, Camera cam) {
        super(x, y, 163, 200, health, new Rectangle2D(x+17,y+10,47,86), cam, "gz-37.png");
        this.iv.setPreserveRatio(true);
        this.iv.setFitHeight(100);
        int[]ph1={1,8,1,1,1,1,8,1,1,1,8};
        long[]ph2={MAX_VALUE,100,MAX_VALUE,MAX_VALUE,MAX_VALUE,MAX_VALUE,100,MAX_VALUE,MAX_VALUE,MAX_VALUE,100};
        this.maxFrame=ph1;
        this.durations=ph2;
    }

    public void update(long time){
        super.update(time);

        //state
        if(this.isGrounded&this.targetSpeed==0){
            this.state=0;
            this.resetFrame(time);
        }else if(this.isGrounded){
            this.state=1;
        }else if(this.vY<-1.5){
            this.state=2;
        }else if(this.vY<1.5){
            this.state=3;
        }else{
            this.state=4;
        }
        if(this.isShooting){this.state+=5;}
        if(((time-this.invulnStarted)/150)%2==0&&(time-this.invulnStarted)<this.invulnTimer){this.state=10;}
    }
}
