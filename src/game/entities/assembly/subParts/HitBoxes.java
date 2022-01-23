package game.entities.assembly.subParts;

import game.physics.ConvexPolygon;
import game.physics.HitBox;
import game.physics.Point;

public enum HitBoxes{
    TORSO0      (new double[][][]{{{7,30},{25,30},{35,53},{34,62},{22,72},{15,72},{3,63},{2,53}},
            {{7,30},{0,19},{0,11},{7,0},{23,0},{27,3},{31,11},{31,18},{29,24},{25,30}}}),

    ARM0        (new double[][][]{{{0,5},{3,1},{9,1},{12,5},{12,29},{9,33},{3,33},{0,29}}}),

    FOREARM0    (new double[][][]{{{2,0},{12,5},{0,5}},
            {{0,5},{12,5},{10,32},{8,34},{4,34},{2,32}}}),

    PALM0       (new double[][][]{{{10,0},{15,7},{12,9}},
            {{10,0},{12,9},{12,16},{0,16},{2,0}}}),

    CLAW0       (new double[][][]{{{0,0},{4,0},{5,10},{3,15},{0,8}},
            {{3,15},{5,10},{6,17}}}),

    THIGH0      (new double[][][]{{{12,0},{17,0},{25,7},{29,19},{29,30},{25,42},{17,49},{12,49},{4,42},{0,30},{0,19},{4,7}}}),

    LEG0        (new double[][][]{{{2,5},{10,0},{12,5}},
            {{0,5},{12,5},{11,32},{1,32}}}),

    FOOT0       (new double[][][]{{{0,3},{3,0},{12,12},{18,29},{10,37},{5,30},{0,8}},
            {{10,37},{18,29},{21,30},{25,36},{23,38},{13,38}}}),

    HEAD0       (new double[][][]{{{0,4},{22,0},{31,11},{43,29},{39,37},{28,56},{10,44},{1,19}}}),;

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
