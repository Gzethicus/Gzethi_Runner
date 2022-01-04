package game.entities.assembly.subParts;

import game.entities.assembly.AttachPoint;
import game.entities.assembly.OriginPoint;

import static game.entities.assembly.subParts.BaseSets.*;

public enum AttachSets{
    TORSO       (new double[]{18,40},new double[][][][]{{TORSO0.get()}},new int[][]{{1,2},{3,4}}),

    ARM         (new double[]{6,5},new double[][][][]{{ARM0.get()}},new int[][]{{}}),

    FOREARM     (new double[]{6,4},new double[][][][]{{FOREARM0.get()}},new int[][]{{}}),

    PALM        (new double[]{6,0},new double[][][][]{{PALM0.get()}},new int[][]{{}}),

    CLAW        (new double[]{2,1},new double[][][][]{{{{}}}},new int[][]{{}}),


    THIGH       (new double[]{15,12},new double[][][][]{{THIGH0.get(),THIGH0.get(),THIGH0.get(),THIGH0.get()}},new int[][]{{}}),

    LEG         (new double[]{5,-5},new double[][][][]{{LEG0.get()}},new int[][]{{}}),

    FOOT        (new double[]{5,3},new double[][][][]{{{{}}}},new int[][]{{}}),


    HEAD        (new double[]{27,54},new double[][][][]{{{{}}}},new int[][]{{}}),;

    private final OriginPoint origin;
    private final AttachPoint[][][] set;
    private final int[][] mirroredIndexes;

    AttachSets(double[] originCoordinates, double[][][][] attachCoordinates, int[][] mirroredIndexes){
        this.origin=new OriginPoint(originCoordinates[0],originCoordinates[1]);
        if(attachCoordinates[0][0][0].length!=0){
            this.set = new AttachPoint[attachCoordinates.length][][];
            for(int i=0;i<attachCoordinates.length;i++){
                this.set[i]=new AttachPoint[attachCoordinates[i].length][attachCoordinates[0][0].length];
                for (int j=0;j<attachCoordinates[i].length;j++){
                    for (int k=0;k<attachCoordinates[0][0].length;k++){
                        this.set[i][j][k]=new AttachPoint(attachCoordinates[i][j][k][0], attachCoordinates[i][j][k][1], attachCoordinates[i][j][k][2], attachCoordinates[i][j][k][3]!=0);
                        this.set[i][j][k].setOrigin(this.origin);
                    }
                }
            }
        }else{
            this.set=new AttachPoint[attachCoordinates.length][][];
            for (int i=0;i<attachCoordinates.length;i++){
                this.set[i]=new AttachPoint[attachCoordinates[i].length][0];
            }
        }
        this.mirroredIndexes=mirroredIndexes;
    }

    public OriginPoint getOrigin(){return new OriginPoint(this.origin.getLocalX(),this.origin.getLocalY());}
    public AttachPoint[][][] get(){return this.set;}
    public int[][] getMirroredIndexes(){return this.mirroredIndexes;}
}
