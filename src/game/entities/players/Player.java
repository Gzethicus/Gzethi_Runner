package game.entities.players;

import game.Camera;
import game.GameScene;
import game.entities.Creature;
import game.entities.projectiles.LaserProjectile;
import game.entities.projectiles.Projectile;
import game.entities.Shot;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Player extends Creature {
    //status
    private int energy;
    protected boolean isShooting =false;
    private boolean canShoot =true;
    private boolean onCooldown=false;

    //time handling
    private long energyLastRestored=0;
    private long startedShooting =0;

    //characteristics
    private int maxEnergy=40;
    private long energyRegenPeriod=300;
    private long shootCooldown =400;
    private long shootDuration =500;

    //listeners
    private final ArrayList<EnergyChanged> energyListeners=new ArrayList<>();

    public Player(int x, int y, int width, int height, int health, Rectangle2D hitBox, Camera cam, String spriteSheet) {
        super(x, y, width, height, true, 2, 5, 300, health, 4, 1,
                0, 1000, 1, hitBox, cam,"players\\"+spriteSheet);
        this.dashDistance=250;
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
        if(time-this.startedShooting>this.shootDuration){this.isShooting =false;}
    }

    public void gainEnergy(int amount){
        int newEnergy=Math.min(this.energy+amount,this.maxEnergy);
        for(EnergyChanged energyListener:this.energyListeners){energyListener.onEnergyChanged(this.energy,newEnergy);}
        this.energy=newEnergy;
    }

    public void shoot(long time){
        if(time-this.startedShooting>this.shootCooldown&this.energy>=10){
            this.startedShooting=time;
            this.gainEnergy(-10);
            this.facingRight=GameScene.getMouseX()>(this.hitBox.getMinX()+this.hitBox.getMaxY())/2;
            Projectile projectile=new LaserProjectile(this.x+(facingRight?76:1), this.y+45, (int)GameScene.getMouseX(),(int)GameScene.getMouseY(),0, this.team, this.cam);
            for(Shot listener:shotListeners){listener.onShot(projectile);}
        }
    }

    public int getMaxEnergy(){return this.maxEnergy;}
    public void addEnergyListener(EnergyChanged energyListener){this.energyListeners.add(energyListener);}
}
