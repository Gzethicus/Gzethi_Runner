package game.entities;

import game.GameScene;
import game.entities.players.EnergyChanged;
import game.entities.projectiles.Projectile;
import game.environment.Walkable;
import game.environment.rooms.Room;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;

import java.util.ArrayList;

public class Creature extends Entity{
    //status
    private int health;
    protected int energy;
    protected boolean isGrounded=true;
    private boolean running=false;
    private boolean walking=false;
    private boolean autoRun=false;
    protected boolean freeFall=false;
    protected boolean isInvulnerable=false;

    //time handling
    private long jumpStarted;
    private long lastDashed=0;
    protected long invulnStarted=0;

    //characteristics
    private long jumpDuration;
    protected int maxEnergy=40;
    protected double dashDistance=0;
    protected long dashDuration=0;
    protected long dashCooldown=1000;
    private int maxHealth;
    protected double walkSpeed;
    protected double runSpeed;
    private boolean runDirection;
    private int damageOnContact;
    protected long invulnTimer;
    private final int toughness;

    //listeners
    private final ArrayList<DamageTaken> damageListeners=new ArrayList<>();
    private final ArrayList<EnergyChanged> energyListeners=new ArrayList<>();
    protected final ArrayList<Shot> shotListeners=new ArrayList<>();

    public Creature(double x, double y, Room room, boolean isFacingRight,
                    double walkSpeed, double runSpeed, long jumpDuration, int health, int maxHealth, int toughness,
                    int damageOnContact, long invulnTimer, int team, Rectangle2D hitBox, Node sprite){
        super(x, y, room, team, isFacingRight, hitBox, sprite);
        this.walkSpeed=walkSpeed;
        this.runSpeed=runSpeed;
        this.jumpDuration=jumpDuration;
        this.health=health;
        this.maxHealth=Math.max(health,maxHealth);
        this.toughness=toughness;
        this.damageOnContact=damageOnContact;
        this.invulnTimer=invulnTimer;
    }

    public void update(long time){
        double dt=this.lastUpdated==0?0:(time-this.lastUpdated)/1000.;
        //target speed
        this.targetSpeed=(this.runDirection?1:-1)*(this.running?this.runSpeed:this.walking?this.walkSpeed:0);
        this.running=this.autoRun;
        this.walking=false;

        //vertical speed
        this.vY = Math.min(this.vY+this.inRoom.getGravityY()*dt, 6);
        boolean falling=true;

        //dash handling
        if(this.dashSpeed!=0){
            if(time-this.lastDashed>this.dashDuration){
                this.dashSpeed=0;
            }
        }

        //terrain collision detection
        this.hitAWall=0;
        this.speedUpdate();
        Rectangle2D estHitBox = new Rectangle2D(
                Math.min(this.hitBox.getMinX()+vX*dt*GameScene.getPixelToMeter(),this.hitBox.getMinX()),
                Math.min(this.hitBox.getMinY()+vY*dt*GameScene.getPixelToMeter(),this.hitBox.getMinY()),
                this.hitBox.getWidth()+Math.abs(vX*dt*GameScene.getPixelToMeter()),
                this.hitBox.getHeight()+Math.abs(vY*dt*GameScene.getPixelToMeter()));
        for (Walkable walkable : GameScene.getWalkables()) {
            if (walkable.isBelow(this.hitBox)
                    & walkable.intersects(estHitBox)
                    & this.vY > 0
                    & (!this.freeFall | walkable.isSolid())) {
                //if(!this.isGrounded){this.sprite.resetFrame(time);}
                this.vY=0;
                this.y = (walkable.getHitBox().getMinY() - (this.yOffset + this.hitBoxHeight));
                this.forcedSpeed = walkable.getForcedSpeed();
                falling=false;
            } else if (walkable.isSolid()) {
                if (walkable.isAbove(this.hitBox)
                        & walkable.intersects(estHitBox)) {
                    this.vY = 0;
                    this.y = walkable.getHitBox().getMaxY() - this.yOffset;
                } else if (walkable.isRight(this.hitBox)
                        & walkable.intersects(estHitBox)) {
                    this.x = walkable.getHitBox().getMinX() - (this.hitBox.getWidth() + this.xOffset);
                    this.hitAWall=1;
                } else if (walkable.isLeft(this.hitBox)
                        & walkable.intersects(estHitBox)) {
                    this.x = walkable.getHitBox().getMaxX() - this.xOffset;
                    this.hitAWall=-1;
                }
            }
        }
        //if(this.isGrounded&falling){this.sprite.resetFrame(time);}
        this.isGrounded=!falling;
        this.freeFall=false;

        super.update(time);

        //invulnerability handling
        this.isInvulnerable = time - this.invulnStarted < this.invulnTimer;

        //hit detection
        if (!this.isInvulnerable){
            //from enemies
            for (Entity creature : GameScene.getEntities()){
                if(creature instanceof Creature){
                    if(creature.getTeam()!=this.team & this.hitBox.intersects(creature.getHitBox())){
                        this.hurt(((Creature)creature).getDamageOnContact(),time);
                    }
                }
            }
            //from projectiles
            for (Entity projectile : GameScene.getEntities()){
                if(projectile instanceof Projectile){
                    if(projectile.getTeam()!=this.team & this.hitBox.intersects(projectile.getHitBox())){
                        this.hurt(((Projectile)projectile).getDamage(), time);
                        ((Projectile)projectile).pierce(this.toughness);
                    }
                }
            }
        }
    }

    public void hurt(int amount, long time){
        this.health-=amount;
        this.invulnStarted=time;
        for(DamageTaken listener:this.damageListeners){listener.onDamageTaken(this.health+amount,this.health);}
        if(this.health<=0){
            GameScene.requestDelete(this);
            for(Removal listener:this.removalListener){listener.onRemoval();}
        }
    }

    public void gainEnergy(int amount){
        int newEnergy=Math.min(this.energy+amount,this.maxEnergy);
        for(EnergyChanged energyListener:this.energyListeners){energyListener.onEnergyChanged(this.energy,newEnergy);}
        this.energy=newEnergy;
    }

    public void jump(long time){
        if(this.isGrounded){
            this.jumpStarted=time;
            this.vY=-4.5;
        }else if(time-this.jumpStarted<this.jumpDuration){
            double dt=(time-this.lastUpdated)/1000.;
            this.vY-=8*dt;
        }
    }

    public void dash(long time){
        if(time-this.lastDashed>this.dashCooldown){
            double dt=(time-this.lastUpdated)/1000.;
            //teleporter dash
            if(this.dashDuration/1000.<dt){
                double displacement=(this.getFacingRight()?1:-1)*this.dashDistance*GameScene.getPixelToMeter();
                this.x+=displacement;
                Rectangle2D tpHitBox = new Rectangle2D(this.hitBox.getMinX()+Math.min(displacement,0), this.hitBox.getMinY(), this.hitBox.getWidth()+Math.abs(displacement), this.hitBox.getHeight());
                for (Walkable walkable : GameScene.getWalkables()){
                    if (walkable.isSolid()) {
                        if (walkable.isRight(this.hitBox)
                                & walkable.intersects(tpHitBox)) {
                            this.x = Math.min(walkable.getHitBox().getMinX() - (this.hitBox.getWidth() + this.xOffset),this.x);
                            this.hitAWall=1;
                        } else if (walkable.isLeft(this.hitBox)
                                & walkable.intersects(tpHitBox)) {
                            this.x = Math.max(walkable.getHitBox().getMaxX() - this.xOffset,this.x);
                            this.hitAWall=-1;
                        }
                    }
                }
            }
            //regular dash
            else{this.dashSpeed=(this.getFacingRight()?1:-1)*this.dashDistance*1000./this.dashDuration;}
            this.lastDashed=time;
            this.updateHitBox();
        }
    }

    public void run(boolean direction){
        this.runDirection=direction;
        this.setFacingRight(direction);
        this.running=true;
    }

    public void walk(boolean direction){
        if(!this.running){
            this.runDirection=direction;
            this.setFacingRight(direction);
            this.walking=!this.running;
        }
    }

    public void triggerShoot(Projectile projectile){
        for(Shot listener:shotListeners)listener.onShot(projectile);
    }

    public void heal(int amount){this.health=Math.min(this.health+amount,this.maxHealth);}
    public void walk(){walk(this.getFacingRight());}
    public void run(){run(this.getFacingRight());}
    public void autoRun(){this.autoRun=true;}
    public void stopAutoRun(){this.autoRun=false;}
    public int getDamageOnContact(){return this.damageOnContact;}
    public void freeFall(){this.freeFall=true;}
    public int getHealth(){return this.health;}
    public int getMaxHealth(){return this.maxHealth;}
    public int getEnergy(){return this.energy;}
    public int getMaxEnergy(){return this.maxEnergy;}
    public void addDamageListener(DamageTaken damageListener){this.damageListeners.add(damageListener);}
    public void addEnergyListener(EnergyChanged energyListener){this.energyListeners.add(energyListener);}
    public void addShotListener(Shot listener){this.shotListeners.add(listener);}
}
