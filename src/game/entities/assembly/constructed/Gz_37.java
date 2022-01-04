package game.entities.assembly.constructed;

import game.State;
import game.entities.Shot;
import game.entities.assembly.Attachable;
import game.entities.assembly.Part;
import game.entities.assembly.actions.Shoot;
import game.entities.assembly.parts.*;
import game.entities.players.Player;
import game.entities.players.Sprites;
import game.entities.projectiles.Projectile;
import game.environment.rooms.Room;
import javafx.geometry.Rectangle2D;

public class Gz_37 extends Player{
    private final Part core=new Torso(this);
    private State state;

    public Gz_37(double x, double y, Room room, int health){
        super(x, y, room, health, new Rectangle2D(x+17,y-30,47,171), Sprites.GZ_37.get());
        this.getChildren().clear();
        this.sprite.setPreserveRatio(true);
        this.sprite.setFitHeight(1);
        this.getChildren().add(this.core);
        this.core.attach(new Head(),0);
        this.core.attach(new Arm(),1);
        this.core.attach(new PlatedArm(),2);
        this.core.attach(new Leg(),3);
        this.core.attach(new Leg(),4);
    }

    @Override
    public void shoot(long time){
        for(Attachable part:this.core.getAttached()){
            if(part instanceof Shoot){
                Projectile projectile=((Shoot)part).shoot(time);
                if(projectile!=null){
                    for(Shot listener:this.shotListeners){listener.onShot(projectile);}
                }
            }
        }
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
}
