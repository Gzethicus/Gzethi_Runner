package game.entities.assembly.movesets;

public enum LegMove {
    STILL      (new long[][]{{100},{100},{100}},
                new double[][]{{-35},{80},{-55}}),

    RUNNING    (new long[][]{{100,100,100,100,100,100,100,100},{100,100,100,100,100,100,100,100},{100,100,100,100,100,100,100,100}},
                new double[][]{{-10,-35,-45,-40,-35,0,20,10},{80,90,60,45,35,30,20,45},{-60,-40,-30,-20,-20,-30,-10,-35}},
                new long[][]{{100,100,100,100,100,100,100,100},{100,100,100,100,100,100,100,100},{100,100,100,100,100,100,100,100}},
                new double[][]{{-35,0,20,10,-10,-35,-45,-40},{35,30,20,45,80,90,60,45},{-20,-30,-10,-35,-60,-40,-30,-20}}),

    JUMPUP     (new long[][]{{100},{100},{100}},
                new double[][]{{0},{30},{-20}},
                new long[][]{{50},{50},{50}},
                new double[][]{{-60},{120},{-70}}),

    JUMPTOP    (new long[][]{{200},{200},{200}},
                new double[][]{{-50},{100},{-50}},
                new long[][]{{200},{200},{200}},
                new double[][]{{-40},{75},{-40}}),

    JUMPDOWN   (new long[][]{{200},{200},{200}},
                new double[][]{{-60},{120},{-70}},
                new long[][]{{200},{200},{200}},
                new double[][]{{-20},{30},{-10}}),;

    private final MoveSet animation;

    LegMove(long[][] durations, double[][] angles){this.animation=new MoveSet(durations,angles);}

    LegMove(long[][] durations, double[][] angles, long[][] mirroredDurations, double[][] mirroredAngles){this.animation=new MoveSet(durations,angles,mirroredDurations,mirroredAngles);}

    public MoveSet get(){return animation;}
}
