package game.entities.equipment;

import game.entities.assembly.Part;
import game.entities.equipment.movesets.MoveClass;
import game.entities.equipment.subParts.Sprites;
import game.entities.equipment.subParts.SubParts;

public class PhaseBlade extends Part {
    public PhaseBlade() {
        super(SubParts.PHASEBLADE.get(Sprites.PHASEBLADE.get()), 1, 0, MoveClass.BLADE.getIndex());
    }
}
