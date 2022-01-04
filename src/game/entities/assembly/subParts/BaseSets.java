package game.entities.assembly.subParts;

public enum BaseSets{
    TORSO0      (new double[][]{{18,8,-1,0},{5,6,-2,0},{24,6,2,1},{7,61,-1,0},{28,61,1,1}}),

    ARM0        (new double[][]{{6,30,-1,0}}),

    FOREARM0    (new double[][]{{6,33,-2,0}}),

    PALM0       (new double[][]{{14,6,1,0},{10,16,1,0},{6,16,1,0},{2,16,1,0},{7,8,1,0}}),

    THIGH0      (new double[][]{{15,40,-1,0}}),

    LEG0        (new double[][]{{6,28,-1,0}});

    private final double[][] attachCoordinates;

    BaseSets(double[][] attachCoordinates){this.attachCoordinates=attachCoordinates;}

    double[][] get(){return this.attachCoordinates;}
}
