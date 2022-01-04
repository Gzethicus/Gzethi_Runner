package game.entities.assembly.movesets;

public enum LegMove {
    STILL       (new long[][]{{100},{100},{100}},
                new double[][]{{-35},{80},{-55}}),
    RUNNING     (new long[][]{{100,100,100,100,100,100,100,100},{100,100,100,100,100,100,100,100},{100,100,100,100,100,100,100,100}},
                new double[][]{{-10,-35,-45,-40,-35,0,20,10},{80,90,60,45,35,30,20,45},{-60,-40,-30,-20,-20,-30,-10,-35}}),
    JUMPUP      (new long[][]{{100},{100},{100}},
                new double[][]{{-35},{80},{-55}}),
    JUMPTOP     (new long[][]{{100},{100},{100}},
                new double[][]{{-35},{80},{-55}}),
    JUMPDOWN    (new long[][]{{100},{100},{100}},
                new double[][]{{-35},{80},{-55}});

    private final MoveSet animation;

    LegMove(long[][] durations, double[][] angles){this.animation=new MoveSet(durations,angles);}

    public MoveSet get(){return animation;}
}
