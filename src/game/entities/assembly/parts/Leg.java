package game.entities.assembly.parts;

import game.entities.assembly.Part;
import game.entities.assembly.movesets.MoveClass;
import game.entities.assembly.subParts.Sprites;
import game.entities.assembly.subParts.SubParts;

public class Leg extends Part{
    public Leg() {
        super(SubParts.THIGH.get(Sprites.PH_THIGH.get()), 3, 0, MoveClass.LEG);
        this.attach(SubParts.LEG.get(Sprites.PH_LEG.get()), 0,0);
        this.attach(SubParts.FOOT.get(Sprites.PH_FOOT.get()), 1,0);
    }
}
