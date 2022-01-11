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
    private double overriddenTargetAngle;
    private long rotationStart;
    private long rotationEnd;
    private long overriddenRotationStart;
    private long overriddenRotationEnd;
    private long overrideEnd;

    public SubPart(OriginPoint origin, AttachPoint[][][] sets, int[] mirrorIndexes, AnimatedSprite sprite){
        this.origin=origin;
        this.sets=sets;
        this.attaches=new AttachPoint[sets[0][0].length];
        if(this.attaches.length!=0){
            for(int i=0;i<this.attaches.length;i++){
                this.attaches[i]=new AttachPoint(0,0,0,this.sets[0][0][i].isMirroring());
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
                this.rotation.setAngle(((this.origin.getLinkedTo()!=null)?this.origin.getLinkedTo().getAngle():0)+this.lastAngle+(this.targetAngle-this.lastAngle)*(time-this.rotationStart)/(this.rotationEnd-this.rotationStart));
            }else{
                this.rotationEnd=time;
            }
        }else if(time<this.overriddenRotationEnd){
            this.rotation.setAngle(((this.origin.getLinkedTo()!=null)?this.origin.getLinkedTo().getAngle():0)+this.lastAngle+(this.overriddenTargetAngle-this.lastAngle)*(time-this.overriddenRotationStart)/(this.overriddenRotationEnd-this.overriddenRotationStart));
        }else{
            this.rotation.setAngle(((this.origin.getLinkedTo()!=null)?this.origin.getLinkedTo().getAngle():0)+this.overriddenTargetAngle);
            this.lastAngle=this.overriddenTargetAngle;
        }
        for(AttachPoint attach:this.attaches){attach.setAngle(this.rotation.getAngle());}
        this.sprite.update(time);
        if(this.sprite.isChanged()){this.switchSet(this.sprite.getAnim(),this.sprite.getFrame());}
        this.refreshTransforms();
    }

    public long rotate(double angle, long duration, long time){
        if(time>this.rotationEnd){
            if(time>this.overrideEnd)this.lastAngle=this.targetAngle;
            this.rotationStart=this.rotationEnd;
        }else{
            if(time>this.overrideEnd)this.lastAngle=this.rotation.getAngle()-((this.origin.getLinkedTo()!=null)?this.origin.getLinkedTo().getAngle():0);
            this.rotationStart =time;
        }
        this.targetAngle=angle;
        this.rotationEnd=this.rotationStart+duration;
        if(duration<=0&time>this.overrideEnd){
            this.rotation.setAngle(this.origin.getLinkedTo().getAngle()+angle);
            this.refreshTransforms();
            for(AttachPoint attach:this.attaches){attach.setAngle(this.rotation.getAngle());}
        }
        return this.rotationEnd;
    }

    public void overriddenRotate(double angle, long duration, long time, long overrideDuration){
        this.lastAngle=this.rotation.getAngle()-((this.origin.getLinkedTo()!=null)?this.origin.getLinkedTo().getAngle():0);
        this.overriddenRotationStart=time;
        this.overriddenRotationEnd=this.overriddenRotationStart+duration;
        this.overriddenTargetAngle=angle;
        if(duration<=0){
            this.rotation.setAngle(this.origin.getLinkedTo().getAngle()+angle);
            this.refreshTransforms();
            for(AttachPoint attach:this.attaches){attach.setAngle(this.rotation.getAngle());}
        }
        this.overrideEnd=this.overriddenRotationStart+overrideDuration;
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
    public AttachPoint getAttach(int attach){return this.attaches[attach];}
}
