package game.entities.players;

import game.AnimatedSprite;
import game.GameScene;
import game.State;
import game.entities.Creature;
import game.entities.projectiles.LaserProjectile;
import game.entities.projectiles.Projectile;
import game.entities.Shot;
import game.environment.rooms.Room;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;

public class Player extends Creature {
    //status
    protected boolean isShooting =false;

    //time handling
    private long energyLastRestored=0;
    private long startedShooting =0;

    //characteristics
    private long energyRegenPeriod=300;
    private long shootCooldown =400;
    private long shootDuration =500;

    public Player(double x, double y, Room room, int health, Rectangle2D hitBox, Node sprite) {
        super(x, y, room, true, 2, 5, 300, health, 4, 1,
                0, 1000, 1, hitBox,sprite);
        this.dashDistance=5;
        this.dashDuration=150;
    }

    public void update(long time){
        super.update(time);

        //passive energy restoration
        if((time-this.energyLastRestored)/this.energyRegenPeriod>0) {
            this.gainEnergy((int) ((time-this.energyLastRestored)/this.energyRegenPeriod));
            this.energyLastRestored = (time/this.energyRegenPeriod)*this.energyRegenPeriod;
        }

        //attack handling
        this.isShooting=time-this.startedShooting<this.shootDuration;
    }

    public void mainAction(long time){}
    public void secondaryAction(long time){}
    public void detach(int attach, long time){}

    public void shoot(long time){
        if(time-this.startedShooting>this.shootCooldown&this.energy>=10){
            this.startedShooting=time;
            this.gainEnergy(-10);
            this.setFacingRight(GameScene.getMouseX()>(this.hitBox.getMinX()+this.hitBox.getMaxY())/2);
            Projectile projectile=new LaserProjectile(this.x+(this.getFacingRight()?76:1), this.y+45, this.inRoom, GameScene.getMouseX(),GameScene.getMouseY(), State.CYAN, this.team);
            for(Shot listener:shotListeners){listener.onShot(projectile);}
        }
    }
}
