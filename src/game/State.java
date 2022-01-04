package game;

import game.entities.assembly.movesets.MoveSets;

public enum State{
    STILL       (0, MoveSets.STILL),
    RUNNING     (1, MoveSets.RUNNING),
    JUMPUP      (2, MoveSets.JUMPUP),
    JUMPTOP     (3, MoveSets.JUMPTOP),
    JUMPDOWN    (4, MoveSets.JUMPDOWN),

    CYAN        (0, null),
    RED         (1, null);

    private final int defValue;
    private final MoveSets anim;

    State(int defValue, MoveSets anim){
        this.defValue=defValue;
        this.anim=anim;
    }

    public int getDef(){return defValue;}
    public MoveSets getAnim(){return anim;}
}
