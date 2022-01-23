package game.entities.equipment.subParts;

import game.physics.ConvexPolygon;
import game.physics.HitBox;
import game.physics.Point;

public enum HitBoxes {
    PHASEBLADE      (new double[][][]{{{21,28},{21,21},{59,20},{59,28}},
                                      {{59,28},{59,20},{85,16},{86,24}},
                                      {{86,24},{85,16},{98,14},{104,18}},
                                      {{104,18},{98,14},{109,11}}}),;



    private final HitBox hitBox;

    HitBoxes(double[][][] vertexCoordinates){
        ConvexPolygon[] polygons=new ConvexPolygon[vertexCoordinates.length];
        Point[] vertexes;
        for(int i=0;i<vertexCoordinates.length;i++){
            vertexes=new Point[vertexCoordinates[i].length];
            for(int j=0;j<vertexCoordinates[i].length;j++){
                vertexes[j]=new Point(vertexCoordinates[i][j][0],vertexCoordinates[i][j][1]);
            }
            polygons[i]=new ConvexPolygon(vertexes);
        }
        this.hitBox=new HitBox(polygons);
    }

    public HitBox get(){return this.hitBox.clone();}
}
