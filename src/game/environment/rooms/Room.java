package game.environment.rooms;

import game.AnimatedSprite;
import game.WorldElement;
import game.entities.Entity;

public class Room extends WorldElement {
    protected Room[] neighbours;

    public Room(double x, double y, AnimatedSprite sprite){
        super(x, y, sprite);
        this.neighbours= new Room[]{null,null};
    }

    public Room leave(Entity entity){
        if(entity.getCenterX()>=this.getBoundsInParent().getMaxX()){return this.neighbours[1];}
        else{return this.neighbours[0];}
    }

    public double getGravityX(){return 0;}
    public double getGravityY(){return 10;}
    public void setNeighbour(Room room, int sourceLinkedSegment,int targetLinkedSegment){
        this.neighbours[sourceLinkedSegment]=room;
        room.neighbours[targetLinkedSegment]=this;
    }
}
