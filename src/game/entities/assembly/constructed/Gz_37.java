package game.entities.assembly.constructed;

import game.State;
import game.entities.assembly.OriginPoint;
import game.entities.assembly.Part;
import game.entities.assembly.actions.Shoot;
import game.entities.assembly.parts.*;
import game.entities.equipment.PhaseBlade;
import game.entities.players.Player;
import game.environment.rooms.Room;
import game.physics.HitBox;
import javafx.geometry.Rectangle2D;

public class Gz_37 extends Player{
    private final Part core;
    private State state;
    private final HitBox hitBox=new HitBox();

    public Gz_37(double x, double y, Room room, int health){
        super(x, y, room, health, new Rectangle2D(x+17,y-30,47,171), new Torso(null));
        this.core=(Torso)this.getChildren().get(0);
        this.core.setOwner(this);
        this.hitBox.addSubBox(this.core.getHitBox());
        this.core.attach(new Head(),0);
        this.core.attach(new Arm(),1);
        this.core.attach(new PlatedArm(),2);
        this.core.attach(new Leg(),3);
        this.core.attach(new Leg(),4);
        this.core.getAttached()[1].attach(new PhaseBlade(),0);
        this.displayHitBox();
    }

    @Override
    public void shoot(long time){
        for(Part part:this.core.getAttached()){
            if(part instanceof Shoot){
                ((Shoot)part).shoot(time);
            }
        }
    }

    public void mainAction(long time){
        if(this.core.getAttached()[1]!=null)this.core.getAttached()[1].mainAction(time);
        else this.core.attach(new Arm(),1);
    }

    public void secondaryAction(long time){
        if(this.core.getAttached()[2]!=null)this.core.getAttached()[2].mainAction(time);
        else this.core.attach(new PlatedArm(),2);
    }

    public void detach(int attach, long time){
        this.core.detach(attach,time);
    }

    public void update(long time) {
        super.update(time);
        this.core.setFacingRight(this.getFacingRight());

        //state
        State state;
        if(this.isGrounded&this.targetSpeed==0){
            state=State.STILL;
        }else if(this.isGrounded){
            state=State.RUNNING;
        }else if(this.vY<-1.5){
            state=State.JUMPUP;
        }else if(this.vY<1.5){
            state=State.JUMPTOP;
        }else{
            state=State.JUMPDOWN;
        }
        //if(((time-this.invulnStarted)/150)%2==0&&(time-this.invulnStarted)<this.invulnTimer){this.state=10;}
        if(this.state!=state){
            this.state=state;
            this.core.setState(state, time);
        }
        this.core.update(time);
    }

    public void setFacingRight(boolean facingRight) {
        super.setFacingRight(facingRight);
        this.core.setFacingRight(this.getFacingRight());
    }

    public OriginPoint getOrigin(){return this.core.getOrigin();}
    public void displayHitBox(){this.hitBox.setVisible(true);}
    public void hideHitBox(){this.hitBox.setVisible(false);}
}
