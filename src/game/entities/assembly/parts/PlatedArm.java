package game.entities.assembly.parts;

import game.GameScene;
import game.State;
import game.entities.assembly.Part;
import game.entities.assembly.actions.Shoot;
import game.entities.assembly.movesets.MoveClass;
import game.entities.assembly.subParts.*;
import game.entities.projectiles.LaserProjectile;
import game.entities.projectiles.Projectile;

public class PlatedArm extends Part implements Shoot{
    private long startedShooting;
    private long shootCooldown=400;

    public PlatedArm(){
        super(SubParts.ARM.get(Sprites.PH_ARM.get()), 7, 1, MoveClass.ARM);
        //forearm
        this.attach(SubParts.FOREARM.get(Sprites.PH_PLATED_FOREARM.get()), 0,0);
        //palm
        this.attach(SubParts.PALM.get(Sprites.PH_PALM.get()), 1,0);
        //claws
        this.attach(SubParts.CLAW.get(Sprites.PH_CLAW.get(),-1.2,.8),2,0);
        this.attach(SubParts.CLAW.get(Sprites.PH_CLAW.get(),.9,.9),2,1);
        this.attach(SubParts.CLAW.get(Sprites.PH_CLAW.get(),1,1),2,2);
        this.attach(SubParts.CLAW.get(Sprites.PH_CLAW.get(),.8,.9),2,3);
        this.addAttach(2,4);
    }

    public Projectile shoot(long time){
        if(time-this.startedShooting>this.shootCooldown&this.owner.getEnergy()>=10){
            double[] mousePos=new double[]{GameScene.getMouseX(),GameScene.getMouseY()};
            double[] blasterPos=new double[]{this.subParts[2].getOrigin().getX()+this.owner.getX(),this.subParts[2].getOrigin().getY()+this.owner.getY()};
            this.startedShooting=time;
            this.owner.gainEnergy(-10);
            this.owner.setFacingRight(mousePos[0]>this.owner.getX());
            this.setOverriddenPos(500, new double[]{(this.facingRight?-180:180)*Math.atan2(mousePos[0]-blasterPos[0],mousePos[1]-blasterPos[1])/Math.PI+5,-10,30,-10,0,0,0},time);
            this.owner.lockOrient(500, time);
            return new LaserProjectile((this.facingRight?1:-1)*this.subParts[2].getOrigin().getX()+this.owner.getX(), this.subParts[2].getOrigin().getY()+this.owner.getY(), this.owner.getRoom(), GameScene.getMouseX(),GameScene.getMouseY(), State.CYAN, this.owner.getTeam());
        }
        return null;
    }

    private void setOverriddenPos(long duration,double[] angles,long time){
        for(int i=0;i<this.subParts.length;i++){
            this.subParts[i].rotate(angles[i],0, time);
            this.subParts[i].setOverride(duration,time);
        }
    }
}
