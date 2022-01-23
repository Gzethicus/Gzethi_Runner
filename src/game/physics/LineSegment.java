package game.physics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineSegment{
    private final Point a;
    private final Point b;
    private final Line visual;

    public LineSegment(Point a, Point b){
        this.a=a;
        this.b=b;
        this.visual=new Line();
        this.visual.setStroke(Color.RED);
        this.refreshVisual();
    }

    public boolean oriented(Point c){
        return ((b.getX()-a.getX())*(c.getY()-b.getY()))-((b.getY()-a.getY())*(c.getX()-b.getX()))<=0;
    }

    void refreshVisual(){
        this.visual.setStartX(this.a.getGlobalX());
        this.visual.setStartY(this.a.getGlobalY());
        this.visual.setEndX(this.b.getGlobalX());
        this.visual.setEndY(this.b.getGlobalY());
    }

    Line getVisual(){return this.visual;}
    public Point getA(){return a;}
    public Point getB(){return b;}
}
