package game.entities.projectiles;

import game.AnimatedSprite;
import game.GameScene;
import game.entities.Entity;
import game.entities.Removal;
import game.environment.Walkable;
import game.environment.rooms.Room;
import javafx.geometry.Rectangle2D;

import static java.lang.Math.*;

public class Projectile extends Entity {
    private final int damage;
    private int piercing;


    public Projectile(double x, double y, Room room, double speed, double targetX, double targetY, int damage, int piercing, int team, Rectangle2D hitBox, AnimatedSprite sprite) {
        super(x, y, room, team, true, hitBox, sprite);
        double A=sqrt((speed*speed)/((targetX-x)*(targetX-x)+(targetY-y)*(targetY-y)));
        this.targetSpeed=A*(targetX-x);
        this.vY=A*(targetY-y);
        this.sprite.setRotate(atan2(this.vY,this.targetSpeed)*180/PI);
        this.damage=damage;
        this.piercing=piercing;
    }

    public void update(long time){
        super.update(time);

        for(Walkable walkable:GameScene.getWalkables()){
            if(walkable.isSolid()){
                if(this.hitBox.intersects(walkable.getHitBox())){
                    GameScene.requestDelete(this);
                    for(Removal listener:this.removalListener){listener.onRemoval();}
                }
            }
        }
    }

    public int getDamage(){return this.damage;}

    public void pierce(int amount){
        this.piercing-=amount;
        if(this.piercing<=0){
            GameScene.requestDelete(this);
            for(Removal listener:this.removalListener){listener.onRemoval();}
        }
    }
}
