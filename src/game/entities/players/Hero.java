package game.entities.players;

import game.Camera;
import javafx.geometry.Rectangle2D;

import static java.lang.Long.MAX_VALUE;

public class Hero extends Player {
    public Hero(int x, int y, int health, Camera cam) {
        super(x, y, 77, 100, health, new Rectangle2D(x+15,y+10,47,90), cam, "hero.png");
        int[]ph1={1,6,1,1,1,6,1,1,6};
        long[]ph2={MAX_VALUE,150,MAX_VALUE,MAX_VALUE,MAX_VALUE,150,MAX_VALUE,MAX_VALUE,150};
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
        }else if(this.vY<0){
            this.state=2;
        }else{
            this.state=3;
        }
        if(this.isShooting){this.state+=4;}
        if(((time-this.invulnStarted)/150)%2==0&&(time-this.invulnStarted)<this.invulnTimer){this.state=8;}
    }
}
