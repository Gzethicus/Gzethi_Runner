package game.entities.assembly.movesets;

public enum ArmMove {
    STILL      (new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{20},{-40},{0},{-30},{0},{0},{0}}),

    RUNNING    (new long[][]{{100,100,100,100,100,100,100,100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{10,30,50,45,35,15,-5,0},{-100},{0},{-30},{0},{0},{0}},
                new long[][]{{100,100,100,100,100,100,100,100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{35,15,-5,0,10,30,50,45},{-100},{0},{-30},{0},{0},{0}}),

    JUMPUP     (new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{45},{-90},{0},{-30},{0},{0},{0}},
                new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{35},{-90},{0},{-30},{0},{0},{0}}),

    JUMPTOP    (new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{55},{-80},{0},{-30},{0},{0},{0}},
                new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{25},{-100},{-10},{-30},{0},{0},{0}}),

    JUMPDOWN   (new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{60},{-75},{0},{-30},{0},{0},{0}},
                new long[][]{{100},{100},{100},{100},{100},{100},{100}},
                new double[][]{{20},{-110},{-10},{-30},{0},{0},{0}});

    private final MoveSet animation;

    ArmMove(long[][] durations, double[][] angles){this.animation=new MoveSet(durations,angles);}

    ArmMove(long[][] durations, double[][] angles, long[][] mirroredDurations, double[][] mirroredAngles){this.animation=new MoveSet(durations,angles,mirroredDurations,mirroredAngles);}

    public MoveSet get(){return animation;}
}
