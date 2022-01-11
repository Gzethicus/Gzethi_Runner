package game.entities.equipment.movesets;

import game.entities.assembly.movesets.MoveSet;

public enum MoveSets {
    STILL       (new MoveSet[]{BladeMove.STILL.get()}),;

    private final MoveSet[] moveSets;

    MoveSets(MoveSet[] animations){this.moveSets=animations;}


    public MoveSet getAnimations(MoveClass mClass){return moveSets[mClass.getIndex()];}
}
