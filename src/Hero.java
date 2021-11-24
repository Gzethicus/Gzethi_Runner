import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Hero extends AnimatedThing{

    private double vX=3;
    private double vY=0;
    private int isAttacking=0;
    private int health;
    private double energy=40;
    private long invulnStarted=0;
    private boolean isJumping=false;
    private boolean isGrounded = false;
    private boolean isHardGrounded = false;
    private long jumpStarted=0;
    private final int maxHealth=3;

    public Hero(int x, int y) {
        super(x, y, 77, 100, 150,0, "sprites\\hero.png");
        this.maxFrames=new int[]{6,1,1,6,1,1,6};
        this.health=maxHealth;
    }
    public void update(long time, Camera cam,ArrayList<Terrain> terrains, ArrayList<Foe> foes,ArrayList<Projectile> projectiles){
        super.update(time,cam);

        //vertical speed
        if (this.dY<350 || this.vY<0){
            this.vY=Math.min(this.vY+.5,6);
            this.isGrounded=false;
        }else{
            if(this.vY>0){this.resetFrame(time);}
            this.vY=0;
            this.dY=350;
            this.isGrounded =true;
            this.isHardGrounded=true;
        }

        //terrain collision detection
        for(Terrain terrain:terrains){
            if(this.x+this.width>terrain.getX()
            & this.x<terrain.getX()+terrain.getWidth()
            &this.dY+this.height<=terrain.getY()
            &this.dY+this.height+this.vY>terrain.getY()){
                this.vY=0;
                this.dY=terrain.getY()-this.height;
                this.isGrounded=true;
                this.isHardGrounded=terrain.isSolid();
            }
            if(terrain.isSolid()){
                if(this.x+this.width>terrain.getX()
                        & this.x<terrain.getX()+terrain.getWidth()
                        & this.dY>=terrain.getY()+terrain.getHeight()
                        & this.dY+this.vY<terrain.getY()+terrain.getHeight()) {
                    this.vY=0;
                    this.dY=terrain.getY()+terrain.getHeight();
                }
                if(this.y+this.height>terrain.getY()
                        & this.y<terrain.getY()+terrain.getHeight()
                        & this.dX+this.width<=terrain.getX()
                        & this.dX+this.width+this.vX>terrain.getX()) {
                    this.dX=terrain.getX()-this.width;
                    this.vX=0;
                }
                if(this.y+this.height>terrain.getY()
                        & this.y<terrain.getY()+terrain.getHeight()
                        & this.dX>=terrain.getX()+terrain.getWidth()
                        & this.dX+this.vX<terrain.getX()+terrain.getWidth()) {
                    this.dX = terrain.getX() + terrain.getWidth();
                    this.vX = 0;
                }
            }
        }

        //horizontal movement
        this.dX=this.dX+vX;
        this.x=(int)this.dX;

        //vertical movement
        this.dY=this.dY+vY;
        this.y=(int)this.dY;

        //jump handling
        if(this.isJumping) {
            if (isGrounded){
                this.jumpStarted = time;
                this.frame = 0;
                this.vY=-7;
            }
            if (time-this.jumpStarted>300){
                this.stopJumping();
            }
            this.vY -= .4;
        }
        this.hitBox=new Rectangle2D(this.x+25,this.y,this.width-50,this.height-30);

        //attitude
        if(this.isGrounded){
            this.attitude=0;
        }else if(this.vY<0){
            this.attitude=1;
            this.resetFrame(time);
        }else{
            this.attitude=2;
            this.resetFrame(time);
        }
        if(this.isAttacking>0){this.attitude+=3;}
        if(((time-this.invulnStarted)/150)%2==0&&(time-this.invulnStarted)<1000){this.attitude=6;}
        this.isAttacking-=1;

        //hit detection from enemies
        for (Foe foe : foes) {
            if (this.getHitBox().intersects(foe.getHitBox())
                    && (time-this.invulnStarted > 1000)) {
                this.takeDamage(time);
            }
        }
        //hit detection from projectiles
        for (Projectile projectile : projectiles) {
            if (this.getHitBox().intersects(projectile.getHitBox())
                    && (time-this.invulnStarted > 1000)) {
                this.takeDamage(time);
            }
        }
        if(this.energy<40) {
            this.energy += 0.02;
        }
    }

    public void jump(){
        if(this.isGrounded){
            this.isJumping=true;
        }
    }

    public void stopJumping(){
        this.isJumping = false;
    }

    public Projectile attack(){
        if(this.isAttacking<2&&this.energy>=10) {
            this.isAttacking = 20;
            this.energy-=10;
            return new Projectile(this.x + 76, this.y + 45, this.vX+10,0);
        }
        return null;
    }

    public void takeDamage(long time){
        this.health -= 1;
        this.invulnStarted = time;
    }

    public void heal(int healthRestored){
        this.health = Math.min(this.maxHealth,this.health + healthRestored);
    }

    public void walk(){
        this.vX=2;
        this.duration=225;
    }
    public void run(){
        this.vX=3;
        this.duration=150;
    }
    public void sprint(){
        this.vX=5;
        this.duration=90;
    }
    public void fall(){
        if(this.isGrounded & !this.isHardGrounded){
            this.dY+=.1;
        }
    }

    public int getHealth() {
        return this.health;
    }

    public int getEnergy(){
        return (int)this.energy;
    }

    public int getDistance() {
        return (int)(this.dX/80)-1;
    }
}
