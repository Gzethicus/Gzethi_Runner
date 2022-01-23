package game.entities.assembly;

import game.GameScene;
import game.State;
import game.Updatable;
import game.WorldElement;
import game.entities.Creature;
import game.entities.Entity;
import game.physics.HitBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.transform.Scale;

public class Part extends Group implements Updatable{
    protected final SubPart[] subParts;
    private final OriginPoint origin;
    private final AttachPoint[] attaches;
    private final HitBox hitBox;
    private final Part[] attached;
    private boolean mirrored;
    protected boolean facingRight=true;
    protected WorldElement owner;

    private final int aClass;
    private final int[] position;
    private int[] maxPosition;
    private double[][] angles;
    private long[][] positionDuration;
    private final long[] rotationEnd;
    private final Scale scale=new Scale();

    public Part(SubPart subPart, int subParts, int attaches, int aClass){
        this.subParts=new SubPart[subParts];
        this.position=new int[subParts];
        this.rotationEnd=new long[subParts];
        this.aClass=aClass;

        this.positionDuration=State.STILL.getAnim().getAnimations(this.aClass).getDurations();
        this.angles=State.STILL.getAnim().getAnimations(this.aClass).getAngles();
        this.maxPosition=State.STILL.getAnim().getAnimations(this.aClass).getMaxPos();

        subPart.setOriginal();
        this.subParts[0]=subPart;
        this.origin=subPart.getOrigin();
        this.attaches=new AttachPoint[attaches];
        this.attached=new Part[attaches];
        this.getChildren().add(subPart);

        this.scale.setPivotX(this.origin.getLocalX());
        this.scale.setPivotY(this.origin.getLocalY());
        this.getTransforms().add(this.scale);
        this.hitBox=new HitBox();
        this.hitBox.addSubBox(subPart.getHitBox());
    }

    public void update(long time){
        for(int subPart=0;subPart<subParts.length;subPart++){
            if(time>this.rotationEnd[subPart]){
                this.position[subPart]=(this.position[subPart]+1)%this.maxPosition[subPart];
                this.rotationEnd[subPart]=subParts[subPart].rotate(this.angles[subPart][this.position[subPart]],this.positionDuration[subPart][this.position[subPart]], time);
            }
            subParts[subPart].update(time);
        }
        for(Part attached:this.attached){if(attached!=null)attached.update(time);}
    }

    public void setState(State state, long time){
        this.positionDuration=state.getAnim().getAnimations(this.aClass).getDurations(!this.mirrored^this.facingRight);
        this.angles=state.getAnim().getAnimations(this.aClass).getAngles(!this.mirrored^this.facingRight);
        this.maxPosition=state.getAnim().getAnimations(this.aClass).getMaxPos();

        for(int subPart=0;subPart<subParts.length;subPart++){
            this.position[subPart]=0;
            this.rotationEnd[subPart]=subParts[subPart].rotate(this.angles[subPart][this.position[subPart]], this.positionDuration[subPart][this.position[subPart]],time);
        }
        for(Part part:this.attached){
            if(part!=null)part.setState(state,time);
        }
    }

    public void attach(SubPart part, int subPart, int attach){
        for(int subPart1=0;subPart1<this.subParts.length;subPart1++){
            if(this.subParts[subPart1]==null){
                this.subParts[subPart1]=part;
                break;
            }
        }
        part.getOrigin().link(this.subParts[subPart].getAttach(attach));
        this.hitBox.addSubBox(part.getHitBox());
        this.getChildren().add(part);
        part.setViewOrder(this.subParts[subPart].getAttach(attach).getLayer());
    }

    public void attach(Part object, int attach){
        this.attached[attach]=object;
        object.getOrigin().link(this.attaches[attach]);
        object.setOwner(this.owner);
        object.setMirror(this.attaches[attach].isMirroring());
        object.setViewOrder(this.attaches[attach].getLayer());
        this.hitBox.addSiblingBox(object.hitBox);
        this.getChildren().add(object);
    }

    public void detach(int attach, long time){
        if(this.attached[attach]!=null){
            Part removed=this.attached[attach];
            Rectangle2D hitbox=new Rectangle2D(removed.getBoundsInLocal().getMinX(),removed.getBoundsInLocal().getMinY(),removed.getBoundsInLocal().getWidth(),removed.getBoundsInLocal().getHeight());
            Entity part=new Entity(removed.getOrigin().getGlobalX()-removed.getOrigin().getLocalX(),
                    removed.getOrigin().getGlobalY()-removed.getOrigin().getLocalY(),
                    ((Creature)this.owner).getRoom(),0,this.owner.getFacingRight(),hitbox,removed);
            removed.getOrigin().unlink();
            removed.setFacingRight(this.owner.getFacingRight());
            removed.setState(State.STILL,time);
            removed.setOwner(part);
            GameScene.addToWorld(part);
            part.setViewOrder(removed.getViewOrder());
            this.getChildren().remove(removed);
            this.attached[attach]=null;
        }
    }

    public void mainAction(long time){}

    public void setMirror(boolean mirror){
        this.mirrored=mirror;
        for(SubPart subPart:this.subParts){subPart.setMirror(subPart.getOrigin().getLinkedTo()!=null&&subPart.getOrigin().getLinkedTo().isMirroring());}
        for(Part object:this.attached){if(object!=null)object.setMirror(object.getOrigin().getLinkedTo().isMirroring());}
    }

    public void addAttach(int subPart, int attach){
        for(int attach1=0;attach1<this.attaches.length;attach1++){
            if(this.attaches[attach1]==null){
                this.attaches[attach1]=this.subParts[subPart].getAttach(attach);
                break;
            }
        }
    }

    public OriginPoint getOrigin(){return this.origin;}

    public void setFacingRight(boolean facingRight){
        for(SubPart subPart:this.subParts){subPart.setFacingRight(facingRight);}
        for(Part object:this.attached){if(object!=null)object.setFacingRight(facingRight);}
        this.facingRight=facingRight;
        if(this.origin.getLinkedTo()!=null)this.setViewOrder(this.origin.getLinkedTo().getLayer());
        if(this.origin.getLinkedTo()==null)this.scale.setX(facingRight?1:-1);
    }

    protected void setOverriddenPos(long duration,double[] angles,long time){
        for(int i=0;i<this.subParts.length;i++){
            this.subParts[i].overriddenRotate(angles[i],0, time, duration);
        }
    }

    public void setOwner(WorldElement owner){
        this.owner=owner;
        this.origin.setOwner(owner);
    }

    public void displayHitBox(){for(SubPart subPart:this.subParts)subPart.displayHitBox();}
    public void hideHitBox(){for(SubPart subPart:this.subParts)subPart.hideHitBox();}
    public HitBox getHitBox(){return this.hitBox;}
    public Part[] getAttached(){return this.attached;}
}
