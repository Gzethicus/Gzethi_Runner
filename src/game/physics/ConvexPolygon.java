package game.physics;

import game.GameScene;
import game.entities.assembly.OriginPoint;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Arrays;

public class ConvexPolygon implements Intersectible, Cloneable{
    private final Point[] vertexes;
    private final LineSegment[] sides;
    private HitBox master;
    private Bounds bounds;
    private final Group outline;

    public ConvexPolygon(Point[] vertexes){
        Point a=vertexes[0];
        Point b=vertexes[1];
        Point c=vertexes[2];
        boolean orientation=((b.getX()-a.getX())*(c.getY()-b.getY()))-((b.getY()-a.getY())*(c.getX()-b.getX()))<0;
        for(int i=1;i<vertexes.length;i++){
            a=vertexes[i];
            b=vertexes[(i+1)%vertexes.length];
            c=vertexes[(i+2)%vertexes.length];
            if(((b.getX()-a.getX())*(c.getY()-b.getY()))-((b.getY()-a.getY())*(c.getX()-b.getX()))<0!=orientation){
                Exception e=new PolygonNotConvexException();
                e.printStackTrace();
            }
        }
        if(orientation)this.vertexes=vertexes;
        else{
            this.vertexes=new Point[vertexes.length];
            this.vertexes[0]=vertexes[0];
            for(int i=1;i<vertexes.length;i++){
                this.vertexes[i]=vertexes[vertexes.length-i];
            }
        }
        this.sides=new LineSegment[vertexes.length];
        for(int i=0;i<vertexes.length;i++){
            this.sides[i]=new LineSegment(this.vertexes[i],this.vertexes[(i+1)% vertexes.length]);
        }
        refreshBounds();
        this.outline=new Group();
        this.outline.setVisible(false);
        this.outline.setViewOrder(-1000);
        Line line;
        for(LineSegment segment:this.sides){
            line=new Line(segment.getA().getGlobalX(),segment.getA().getGlobalY(),segment.getB().getGlobalX(),segment.getB().getGlobalY());
            line.setStroke(Color.RED);
            this.outline.getChildren().add(line);
        }
        GameScene.addToWorld(this.outline);
    }

    private ConvexPolygon(ConvexPolygon original){
        this.vertexes=new Point[original.vertexes.length];
        for(int i=0;i<this.vertexes.length;i++)this.vertexes[i]=original.vertexes[i].clone();
        this.sides=new LineSegment[original.sides.length];
        for(int i=0;i<vertexes.length;i++){
            this.sides[i]=new LineSegment(this.vertexes[i],this.vertexes[(i+1)% this.vertexes.length]);
        }
        refreshBounds();
        this.outline=new Group();
        this.outline.setVisible(false);
        this.outline.setViewOrder(-1000);
        for(LineSegment segment:this.sides){
            this.outline.getChildren().add(segment.getVisual());
        }
        this.master=null;
        GameScene.addToWorld(this.outline);
    }

    public boolean contains(Point point){
        for(LineSegment side:this.sides){
            if(!side.oriented(point))return false;
        }
        return true;
    }

    public void refreshBounds(){
        double minX=vertexes[0].getGlobalX();
        double maxX=vertexes[0].getGlobalX();
        double minY=vertexes[0].getGlobalY();
        double maxY=vertexes[0].getGlobalY();
        for(Point vertex:vertexes){
            minX=Math.min(minX,vertex.getGlobalX());
            maxX=Math.max(maxX,vertex.getGlobalX());
            minY=Math.min(minY,vertex.getGlobalY());
            maxY=Math.max(maxY,vertex.getGlobalY());
        }
        this.bounds=new BoundingBox(minX,minY,maxX-minX,maxY-minY);
    }

    public Bounds getBounds(){return this.bounds;}

    public boolean intersects(Intersectible intersectible) {
        if(intersectible instanceof ConvexPolygon)return this.intersects((ConvexPolygon)intersectible);
        else return intersectible.intersects(this);
    }

    private boolean intersects(ConvexPolygon polygon){
        if(this.bounds.intersects(polygon.bounds))return this.intersects(polygon,true);
        else return false;
    }

    private boolean intersects(ConvexPolygon polygon, boolean recurring){
        boolean isSeparating;
        for(LineSegment side:this.sides){
            isSeparating=true;
            for(Point vertex:polygon.vertexes){
                if(side.oriented(vertex)){
                    isSeparating=false;
                    break;
                }
            }
            if(isSeparating)return false;
        }
        if(recurring){return polygon.intersects(this, false);}
        return true;
    }

    public ConvexPolygon clone(){return new ConvexPolygon(this);}
    public void setOrigin(OriginPoint origin){for(Point vertex:this.vertexes)vertex.setOrigin(origin);}
    public void setAngle(double angle){for(Point point:this.vertexes)point.setAngle(angle);}
    public void setScaleX(double x){for(Point vertex:this.vertexes)vertex.setScaleX(x);}
    public void setScaleY(double y){for(Point vertex:this.vertexes)vertex.setScaleY(y);}
    public void setVisible(boolean visible){this.outline.setVisible(visible);}
    @Override
    public String toString(){return "ConvexPolygon{"+Arrays.toString(vertexes)+'}';}
    public void setMaster(HitBox masterBox){this.master=masterBox;}
    public void addSiblingBox(Intersectible sibling){this.master.addSubBox(sibling);}
    public void refreshVisual(){if(this.outline.isVisible())for(LineSegment side:this.sides)side.refreshVisual();}

    public static void main(String[] args){
        ConvexPolygon polygon1=new ConvexPolygon(new Point[]{new Point(-.51,-.51),new Point(-.51,.51),new Point(.51,.51),new Point(.51,-.51)});
        polygon1.setAngle(0);
        for(double y=-1;y<=1;y+=.1){
            for(double x=-1;x<=1;x+=.1){
                System.out.print(polygon1.contains(new Point(x,y))?"■  ":"□  ");
            }
            System.out.print("\n");
        }
    }
}
