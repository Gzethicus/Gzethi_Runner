package game.entities.equipment.movesets;

import game.entities.assembly.movesets.MoveSet;

public enum BladeMove {
    STILL      (new long[][]{{100}},
                new double[][]{{0}}),;

    private final MoveSet animation;

    BladeMove(long[][] durations, double[][] angles){this.animation=new MoveSet(durations,angles);}

    BladeMove(long[][] durations, double[][] angles, long[][] mirroredDurations, double[][] mirroredAngles){this.animation=new MoveSet(durations,angles,mirroredDurations,mirroredAngles);}

    public MoveSet get(){return animation;}
}
