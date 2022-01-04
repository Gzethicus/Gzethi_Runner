package game.entities.assembly.movesets;

public enum HeadMove {
    STILL       (new long[][]{{100},{100},{100}},
                new double[][]{{0},{0},{0}}),
    RUNNING     (new long[][]{{100},{100},{100}},
                new double[][]{{0},{0},{0}}),
    JUMPUP      (new long[][]{{100},{100},{100}},
                new double[][]{{0},{0},{0}}),
    JUMPTOP     (new long[][]{{100},{100},{100}},
                new double[][]{{0},{0},{0}}),
    JUMPDOWN    (new long[][]{{100},{100},{100}},
                new double[][]{{0},{0},{0}});

    private final MoveSet animation;

    HeadMove(long[][] durations, double[][] angles){this.animation=new MoveSet(durations,angles);}

    public MoveSet get(){return animation;}
}
