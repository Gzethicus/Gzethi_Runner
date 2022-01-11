package game.entities.equipment.subParts;

import game.entities.assembly.AttachPoint;
import game.entities.assembly.OriginPoint;

import static game.entities.equipment.subParts.BaseSets.*;

public enum AttachSets {
    PHASEBLADE      (new double[]{10,25},new double[][][][]{{NULL.get()}},new int[][]{{}}),;

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
