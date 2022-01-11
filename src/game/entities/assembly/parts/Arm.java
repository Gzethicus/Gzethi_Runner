package game.entities.assembly.parts;

import game.entities.assembly.Part;
import game.entities.assembly.movesets.MoveClass;
import game.entities.assembly.subParts.*;


public class Arm extends Part{

    public Arm(){
        super(SubParts.ARM.get(Sprites.PH_ARM.get()), 7, 1, MoveClass.ARM.getIndex());
        //forearm
        this.attach(SubParts.FOREARM.get(Sprites.PH_FOREARM.get()), 0,0);
        //palm
        this.attach(SubParts.PALM.get(Sprites.PH_PALM.get()), 1,0);
        //claws
        this.attach(SubParts.CLAW.get(Sprites.PH_CLAW.get(), -1.2, .8), 2,0);
        this.attach(SubParts.CLAW.get(Sprites.PH_CLAW.get(),.9,.9),2,1);
        this.attach(SubParts.CLAW.get(Sprites.PH_CLAW.get(),1,1),2,2);
        this.attach(SubParts.CLAW.get(Sprites.PH_CLAW.get(),.8,.9),2,3);
        this.addAttach(2,4);
    }
}
