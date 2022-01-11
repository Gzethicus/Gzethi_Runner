package game.entities.assembly.movesets;

public enum MoveSets {
    STILL       (new MoveSet[]{TorsoMove.STILL.get(),   ArmMove.STILL.get(),    LegMove.STILL.get(),    HeadMove.STILL.get()}),
    RUNNING     (new MoveSet[]{TorsoMove.RUNNING.get(), ArmMove.RUNNING.get(),  LegMove.RUNNING.get(),  HeadMove.RUNNING.get()}),
    JUMPUP      (new MoveSet[]{TorsoMove.JUMPUP.get(),  ArmMove.JUMPUP.get(),   LegMove.JUMPUP.get(),   HeadMove.JUMPUP.get()}),
    JUMPTOP     (new MoveSet[]{TorsoMove.JUMPTOP.get(), ArmMove.JUMPTOP.get(),  LegMove.JUMPTOP.get(),  HeadMove.JUMPTOP.get()}),
    JUMPDOWN    (new MoveSet[]{TorsoMove.JUMPDOWN.get(),ArmMove.JUMPDOWN.get(), LegMove.JUMPDOWN.get(), HeadMove.JUMPDOWN.get()});

    private final MoveSet[] moveSets;

    MoveSets(MoveSet[] animations){this.moveSets=animations;}


    public MoveSet getAnimations(int mClass){return moveSets[mClass];}
}
