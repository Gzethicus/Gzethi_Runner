package game.entities.assembly.parts;

import game.entities.Creature;
import game.entities.assembly.Part;
import game.entities.assembly.movesets.MoveClass;
import game.entities.assembly.subParts.Sprites;
import game.entities.assembly.subParts.SubParts;

public class Torso extends Part{
    public Torso(Creature owner){
        super(SubParts.TORSO.get(Sprites.PH_TORSO.get()), 1, 5, MoveClass.TORSO);
        this.addAttach(0,0);
        this.addAttach(0,1);
        this.addAttach(0,2);
        this.addAttach(0,3);
        this.addAttach(0,4);
        this.owner=owner;
    }
}
