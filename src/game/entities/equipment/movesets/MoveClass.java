package game.entities.equipment.movesets;

public enum MoveClass {
    BLADE   (0),;

    private final int index;

    MoveClass(int index){this.index=index;}

    public int getIndex(){return index;}
}
