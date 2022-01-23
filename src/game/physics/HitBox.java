package game.physics;

import game.entities.assembly.OriginPoint;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.ArrayList;
import java.util.Arrays;

public class HitBox implements Intersectible, Cloneable{
    private final ArrayList<Intersectible> subBoxes=new ArrayList<>();
    private HitBox master;
    private Bounds bounds;

    public HitBox(){}
    public HitBox(Intersectible[] subBoxes){
        this.subBoxes.addAll(Arrays.asList(subBoxes));
        this.refreshBounds();
    }

    public boolean intersects(Intersectible intersectible){
        if(intersectible instanceof HitBox)return this.intersects((HitBox)intersectible);
        else if(intersectible instanceof ConvexPolygon)return this.intersects((ConvexPolygon)intersectible);
        else return intersectible.intersects(this);
    }

    private boolean intersects(HitBox hitBox){
        for(Intersectible subBox0:this.subBoxes){
            for(Intersectible subBox1:hitBox.subBoxes){
                if(subBox0.intersects(subBox1))return true;
            }
        }
        return false;
    }

    private boolean intersects(ConvexPolygon hitbox){
        for(Intersectible subBox:this.subBoxes){
            if(subBox.intersects(hitbox))return true;
        }
        return false;
    }

    public void refreshBounds(){
        for(Intersectible intersectible:this.subBoxes)intersectible.refreshBounds();
        double minX=subBoxes.get(0).getBounds().getMinX();
        double maxX=subBoxes.get(0).getBounds().getMaxX();
        double minY=subBoxes.get(0).getBounds().getMinY();
        double maxY=subBoxes.get(0).getBounds().getMaxY();
        for(Intersectible intersectible:this.subBoxes){
            minX=Math.min(minX,intersectible.getBounds().getMinX());
            maxX=Math.max(maxX,intersectible.getBounds().getMaxX());
            minY=Math.min(minY,intersectible.getBounds().getMinY());
            maxY=Math.max(maxY,intersectible.getBounds().getMaxY());
        }
        this.bounds=new BoundingBox(minX,minY,maxX-minX,maxY-minY);
    }

    public void addSubBox(Intersectible subBox){
        this.subBoxes.add(subBox);
        subBox.setMaster(this);
    }

    public void removeSubBox(Intersectible subBox){
        this.subBoxes.remove(subBox);
        subBox.setMaster(null);
    }

    public HitBox clone(){
        HitBox clone=new HitBox();
        for(Intersectible subBox:this.subBoxes)clone.addSubBox(subBox.clone());
        return clone;
    }

    @Override
    public String toString(){
        StringBuilder string=new StringBuilder("HitBox{" + "\n");
        for(Intersectible subBox:this.subBoxes)string.append(subBox).append("\n");
        string.append("}");
        return string.toString();
    }

    public void setAngle(double angle){for(Intersectible subBox:this.subBoxes)subBox.setAngle(angle);}
    public void setVisible(boolean visible){for(Intersectible subBox:this.subBoxes)subBox.setVisible(visible);}
    public void setScaleX(double x){for(Intersectible subBox:this.subBoxes)subBox.setScaleX(x);}
    public void setScaleY(double y){for(Intersectible subBox:this.subBoxes)subBox.setScaleY(y);}
    public void refreshVisual(){for(Intersectible subBox:this.subBoxes)subBox.refreshVisual();}
    public Bounds getBounds(){return this.bounds;}
    public void setOrigin(OriginPoint origin){for(Intersectible subBox:this.subBoxes)subBox.setOrigin(origin);}
    public void setMaster(HitBox masterBox){this.master=masterBox;}
    public void addSiblingBox(Intersectible sibling){this.master.addSubBox(sibling);}
}
