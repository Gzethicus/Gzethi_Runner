package game.entities.players;

import game.AnimatedSprite;
import game.environment.rooms.Room;
import javafx.geometry.Rectangle2D;

public class Hero extends Player {
    public Hero(double x, double y, Room room, int health) {
        super(x, y, room, health, new Rectangle2D(x+15,y+10,47,90), Sprites.HERO.get());
    }

    public void update(long time){
        super.update(time);

        //animation
        int animation;
        if(this.isGrounded&this.targetSpeed==0){
            animation=0;
        }else if(this.isGrounded){
            animation=1;
        }else if(this.vY<0){
            animation=2;
        }else{
            animation=3;
        }
        if(this.isShooting){animation+=4;System.out.println("isShooting");}
        if(((time-this.invulnStarted)/150)%2==0&&(time-this.invulnStarted)<this.invulnTimer){animation=8;}
        ((AnimatedSprite)this.sprite).setDefaultAnimation(animation);
        ((AnimatedSprite)this.sprite).update(time);
    }
}
