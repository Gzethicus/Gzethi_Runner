package game.entities;

import game.Camera;
import javafx.geometry.Rectangle2D;

import static java.lang.Math.*;

public class Projectile extends Entity{
    private final int damage;
    private int piercing;


    public Projectile(int x, int y, int width, int height,double speed, int targetX, int targetY, int damage, int team, Rectangle2D hitBox, Camera cam, String spriteName) {
        super(x, y, width, height, team, true, hitBox, cam, "projectiles\\"+spriteName);
        double A=sqrt((speed*speed)/((targetX-x)*(targetX-x)+(targetY-y)*(targetY-y)));
        this.targetSpeed=A*(targetX-x);
        this.vY=A*(targetY-y);
        this.iv.setRotate(atan2(this.vY,this.targetSpeed)*180/PI);
        this.damage=damage;
    }

    public int getDamage(){return this.damage;}

    public void pierce(int amount){
        this.piercing-=amount;
        if(this.piercing<=0){
            for(Removal listener:this.removalListener){listener.onRemoval();}
        }
    }
}
