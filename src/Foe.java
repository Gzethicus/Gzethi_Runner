import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Foe extends AnimatedThing {
    private static long lastAttacked;
    public Foe(int x) {
        super(x, 250,76,94,200,0, "sprites\\foe.png");
        this.maxFrames=new int[]{1,4};
    }

    public void loop(){
        this.x-=800;
        this.hitBox=new Rectangle2D(this.x+25,this.y,this.width-50,this.height);
    }

    public Projectile update(long time,Camera cam, ArrayList<Projectile> projectiles){
        super.update(time,cam);

        //hit detection from projectiles
        for (Projectile projectile : projectiles) {
            if (this.getHitBox().intersects(projectile.getHitBox())){
                projectile.requestDelete();
                return projectile;
            }
        }
        //shoot projectile at end of animation
        if(this.frame==3){
            this.attitude=0;
            this.resetFrame(time);
            return new Projectile(this.x - 23, this.y + 45, -5,1);
        }
        return null;
    }

    public Projectile attack(long time){
        if(time-this.lastAttacked>1000) {
            this.attitude=1;
            this.resetFrame(time);
            this.lastAttacked=time;
        }
        return null;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
}
