package game.entities.assembly.parts;

import game.GameScene;
import game.State;
import game.entities.Creature;
import game.entities.assembly.Part;
import game.entities.assembly.actions.Shoot;
import game.entities.assembly.movesets.MoveClass;
import game.entities.assembly.subParts.*;
import game.entities.projectiles.LaserProjectile;

public class PlatedArm extends Part implements Shoot{
    private long startedShooting;
    private long shootCooldown=400;

    public PlatedArm(){
        super(SubParts.ARM.get(Sprites.PH_ARM.get()), 7, 1, MoveClass.ARM.getIndex());
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

    @Override
    public void mainAction(long time){this.shoot(time);}

    public void shoot(long time){
        Creature owner=(Creature)this.owner;
        if(time-this.startedShooting>this.shootCooldown&owner.getEnergy()>=10){
            double[] mousePos=new double[]{GameScene.getMouseX(),GameScene.getMouseY()};
            double[] blasterPos=new double[]{this.subParts[2].getOrigin().getX()+this.owner.getX(),this.subParts[2].getOrigin().getY()+this.owner.getY()};
            this.startedShooting=time;
            owner.gainEnergy(-10);
            owner.setFacingRight(mousePos[0]>this.owner.getX());
            owner.lockOrient(500, time);
            setOverriddenPos(500, new double[]{(this.facingRight?-180:180)*Math.atan2(mousePos[0]-blasterPos[0],mousePos[1]-blasterPos[1])/Math.PI+5,-10,30,-10,0,0,0},time);
            owner.triggerShoot(new LaserProjectile((this.facingRight?1:-1)*this.subParts[2].getOrigin().getX()+this.owner.getX(), this.subParts[2].getOrigin().getY()+this.owner.getY(), owner.getRoom(), GameScene.getMouseX(),GameScene.getMouseY(), State.CYAN, owner.getTeam()));
        }
    }
}
