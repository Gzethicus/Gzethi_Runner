package game.entities.assembly.parts;

import game.entities.assembly.Part;
import game.entities.assembly.movesets.MoveClass;
import game.entities.assembly.subParts.Sprites;
import game.entities.assembly.subParts.SubParts;

public class Head extends Part{
    public Head(){
        super(SubParts.HEAD.get(Sprites.PH_HEAD.get()), 1, 0, MoveClass.HEAD);
    }
}
