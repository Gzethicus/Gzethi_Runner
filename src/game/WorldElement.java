package game;

import javafx.scene.Parent;

public class WorldElement extends Parent {
    protected double x;
    protected double y;
    protected final AnimatedSprite sprite;
    protected long lastUpdated=0;

    public WorldElement(double x, double y, AnimatedSprite sprite){
        super();
        this.x=x;
        this.y=y;
        this.sprite =sprite;
        this.setTranslateX(this.x);
        this.setTranslateY(this.y);
        this.getChildren().add(this.sprite);
    }

    public void update(long time){
        this.setTranslateX(this.x);
        this.setTranslateY(this.y);
    }

    public double getX(){return this.x;}
    public double getY(){return this.y;}
}
