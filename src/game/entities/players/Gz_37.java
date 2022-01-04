package game.entities.players;

import game.environment.rooms.Room;
import javafx.geometry.Rectangle2D;

public class Gz_37 extends Player{
    public Gz_37(double x, double y, Room room, int health){
        super(x, y, room, health, new Rectangle2D(x+17,y+10,47,86), Sprites.GZ_37.get());
        this.sprite.setPreserveRatio(true);
        this.sprite.setFitHeight(100);
    }

    public void update(long time){
        super.update(time);

        //animation
        int animation;
        if(this.isGrounded&this.targetSpeed==0){
            animation=0;
        }else if(this.isGrounded){
            animation=1;
        }else if(this.vY<-1.5){
            animation=2;
        }else if(this.vY<1.5){
            animation=3;
        }else{
            animation=4;
        }
        if(this.isShooting){animation+=5;}
        if(((time-this.invulnStarted)/150)%2==0&&(time-this.invulnStarted)<this.invulnTimer){animation=10;}
        this.sprite.setDefaultAnimation(animation);
        this.sprite.update(time);
    }
}
