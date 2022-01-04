package game.entities.assembly;

import game.AnimatedSprite;
import javafx.scene.Parent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class SubPart extends Parent{
    private final AnimatedSprite sprite;

    private final OriginPoint origin;
    private final AttachPoint[] attaches;
    private final AttachPoint[][][] sets;
    private final int[] mirrorIndexes;
    private boolean original=false;

    private final Rotate rotation;
    private final Translate basePos;
    private final Scale scale;

    private boolean facingRight;
    private boolean mirrored;
    private double lastAngle;
    private double targetAngle;
    private long rotationStarted;
    private long rotationEnd;
    private long overrideEnd;

    public SubPart(OriginPoint origin, AttachPoint[][][] sets, int[] mirrorIndexes, AnimatedSprite sprite){
        this.origin=origin;
        this.sets=sets;
        this.attaches=new AttachPoint[sets[0][0].length];
        if(this.attaches.length!=0){
            for(int i=0;i<this.attaches.length;i++){
                this.attaches[i]=new AttachPoint(0,0,0,false);
                this.attaches[i].copy(this.sets[0][0][i]);
            }
        }
        this.mirrorIndexes=mirrorIndexes;
        for(AttachPoint attach:this.attaches){attach.setOrigin(this.origin);}

        this.sprite=sprite;
        this.getChildren().add(this.sprite);

        this.rotation=new Rotate();
        this.basePos=new Translate();
        this.scale=new Scale();
        this.getTransforms().add(this.rotation);
        this.getTransforms().add(this.basePos);
        this.getTransforms().add(this.scale);
    }

    public void update(long time){
        if(time>this.overrideEnd){
            if(time<this.rotationEnd){
                this.rotation.setAngle(((this.origin.getLinkedTo()!=null)?this.origin.getLinkedTo().getAngle():0)+this.lastAngle+(this.targetAngle-this.lastAngle)*(time-this.rotationStarted)/(this.rotationEnd-this.rotationStarted));
            }else{
                this.rotationEnd=time;
            }
            for(AttachPoint attach:this.attaches){attach.setAngle(this.rotation.getAngle());}
        }
        if(this.sprite.update(time)){this.switchSet(this.sprite.getAnim(),this.sprite.getFrame());}
        this.refreshTransforms();
    }

    public long rotate(double angle, long duration, long time){
        if(time>this.rotationEnd){
            this.lastAngle=this.targetAngle;
            this.rotationStarted=rotationEnd;
        }else{
            this.lastAngle=this.rotation.getAngle()-((this.origin.getLinkedTo()!=null)?this.origin.getLinkedTo().getAngle():0);
            this.rotationStarted=time;
        }
        this.targetAngle=angle;
        this.rotationEnd=rotationStarted+duration;
        if(duration<=0){
            this.rotation.setAngle(this.origin.getLinkedTo().getAngle()+angle);
            this.refreshTransforms();
            for(AttachPoint attach:this.attaches){attach.setAngle(this.rotation.getAngle());}
        }
        return this.rotationEnd;
    }

    private void switchSet(int anim, int frame){
        if(this.origin.getLinkedTo()!=null&!this.original)this.setViewOrder(this.origin.getLinkedTo().getLayer());
        for(int i=0;i<this.attaches.length;i++){this.attaches[i].copy(this.sets[anim][frame][(this.mirrored^this.facingRight)?i:this.mirrorIndexes[i]]);}
    }

    public void refreshTransforms(){
        this.basePos.setX(this.origin.getX()-this.origin.getLocalX());
        this.basePos.setY(this.origin.getY()-this.origin.getLocalY());
        this.rotation.setPivotX(this.origin.getX());
        this.rotation.setPivotY(this.origin.getY());
        this.scale.setPivotX(this.origin.getLocalX());
        this.scale.setPivotY(this.origin.getLocalY());
        if(this.origin.getLinkedTo()!=null&!this.original)this.setViewOrder(this.origin.getLinkedTo().getLayer());
    }

    public void setMirror(boolean mirror){
        this.mirrored=true;
        this.switchSet(this.sprite.getAnim(),this.sprite.getFrame());
        this.sprite.setMirror(mirror);
        for(AttachPoint attach:this.attaches){attach.setMirrored(mirror);}
    }

    public void setFacingRight(boolean facingRight){
        this.facingRight=facingRight;
        this.sprite.setFacingRight(facingRight);
        if(this.origin.getLinkedTo()!=null&!this.original)this.setViewOrder(this.origin.getLinkedTo().getLayer());
    }

    public void setOriginal(){this.original=true;}
    public void setXScale(double x){this.scale.setX(x);}
    public void setYScale(double y){this.scale.setY(y);}
    public OriginPoint getOrigin(){return this.origin;}
    public AttachPoint getAttach(int attach) {return this.attaches[attach];}
    public void setOverride(long overrideEnd){this.overrideEnd=overrideEnd;}
    public void setOverride(long duration, long time){this.setOverride(time+duration);}
}
