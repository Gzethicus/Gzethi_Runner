package game.environment;

import game.Camera;
import game.WorldElement;

import static java.lang.Long.MAX_VALUE;

public class Room extends WorldElement {
    public Room(Room neighbour, char direction, Camera cam, String spriteName){
        super(  neighbour==null?0:neighbour.getX()+(direction=='r'?800:direction=='l'?-800:0),
                neighbour==null?0:neighbour.getY()+(direction=='d'?500:direction=='u'?-500:0),
                800, 500, cam, "rooms\\"+spriteName);
        int[]ph1={1};
        long[]ph2={MAX_VALUE};
        this.maxFrame=ph1;
        this.durations=ph2;
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}
}
