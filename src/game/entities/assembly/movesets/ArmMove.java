package game.entities.assembly.movesets;

public enum ArmMove {
    STILL       (new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{20},{-40},{0},{-30},{0},{0},{0}}),
    RUNNING     (new long[][]{{100,100,100,100,100,100,100,100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{10,30,50,45,35,15,-5,0},{-100},{0},{-30},{0},{0},{0}}),
    JUMPUP      (new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{45},{-90},{0},{-30},{0},{0},{0}}),
    JUMPTOP     (new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{55},{-80},{0},{-30},{0},{0},{0}}),
    JUMPDOWN    (new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{60},{-75},{0},{-30},{0},{0},{0}});

    private final MoveSet animation;

    ArmMove(long[][] durations, double[][] angles){this.animation=new MoveSet(durations,angles);}

    public MoveSet get(){return animation;}
}
