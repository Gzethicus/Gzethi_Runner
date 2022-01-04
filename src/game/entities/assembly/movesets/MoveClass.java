package game.entities.assembly.movesets;

public enum MoveClass {
    TORSO   (0),
    ARM     (1),
    LEG     (2),
    HEAD    (3);

    private final int index;

    MoveClass(int index){this.index=index;}

    public int getIndex(){return index;}
}
