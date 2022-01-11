package game.environment.rooms;

import game.AnimatedSprite;
import game.GameScene;
import game.entities.Entity;

public class Space extends Room{
    public Space() {
        super(-1000,-1000, Sprites.SPACE.get());
        ((AnimatedSprite)this.sprite).setFitWidth(10000);
        ((AnimatedSprite)this.sprite).setFitHeight(3000);
        this.neighbours= new Room[]{null};
    }

    @Override
    public Room leave(Entity entity){
        System.out.println("you can't leave space");
        GameScene.requestDelete(entity);
        return this;
    }

    @Override
    public double getGravityY(){return 0;}
}
