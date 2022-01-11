package game.entities.equipment.subParts;

public enum BaseSets {
    NULL            (new double[][]{{}}),;

    private final double[][] attachCoordinates;

    BaseSets(double[][] attachCoordinates){this.attachCoordinates=attachCoordinates;}

    double[][] get(){return this.attachCoordinates;}
}
