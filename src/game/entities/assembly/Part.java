package game.entities.assembly;

import game.State;
import game.entities.Creature;
import game.entities.assembly.movesets.MoveClass;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Scale;

public class Part extends Group implements Attachable{
    protected final SubPart[] subParts;
    private final OriginPoint origin;
    private final AttachPoint[] attaches;
    private final Attachable[] attached;
    private boolean mirrored;
    protected boolean facingRight=true;
    protected Creature owner;

    private final MoveClass aClass;
    private final int[] position;
    protected int[] maxPosition;
    private double[][] angles;
    private long[][] positionDuration;
    private final long[] rotationEnd;
    private final Scale scale=new Scale();

    public Part(SubPart subPart, int subParts, int attaches, MoveClass aClass){
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
        this.attached=new Attachable[attaches];
        this.getChildren().add(subPart);

        this.scale.setPivotX(subPart.getBoundsInLocal().getWidth()/2);
        this.scale.setPivotY(subPart.getBoundsInLocal().getHeight()/2);
        this.getTransforms().add(this.scale);
    }

    public void update(long time){
        for(int subPart=0;subPart<subParts.length;subPart++){
            if(time>this.rotationEnd[subPart]){
                this.position[subPart]=(this.position[subPart]+1)%this.maxPosition[subPart];
                this.rotationEnd[subPart]=subParts[subPart].rotate(this.angles[subPart][this.position[subPart]],this.positionDuration[subPart][this.position[subPart]], time);
            }
            subParts[subPart].update(time);
        }
        for(Attachable attached:this.attached){if(attached!=null)attached.update(time);}
    }

    public void setState(State state, long time){
        this.positionDuration=state.getAnim().getAnimations(this.aClass).getDurations();
        this.angles=state.getAnim().getAnimations(this.aClass).getAngles();
        this.maxPosition=state.getAnim().getAnimations(this.aClass).getMaxPos();

        for(int subPart=0;subPart<subParts.length;subPart++){
            this.position[subPart]=this.mirrored?0:this.maxPosition[subPart]/2;
            subParts[subPart].rotate(this.angles[subPart][this.position[subPart]], this.positionDuration[subPart][this.position[subPart]],time);
        }
        for(Attachable part:this.attached){
            if(part instanceof Part){
                ((Part) part).setState(state,time);
            }
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
        this.getChildren().add(part);
        part.setViewOrder(this.subParts[subPart].getAttach(attach).getLayer());
    }

    public void attach(Attachable object, int attach){
        this.attached[attach]=object;
        object.getOrigin().link(this.attaches[attach]);
        object.setOwner(this.owner);
        object.setMirror(this.attaches[attach].isMirroring());
        object.setViewOrder(this.attaches[attach].getLayer());
        this.getChildren().add((Node)object);
    }

    public void setMirror(boolean mirror){
        this.mirrored=mirror;
        for(SubPart subPart:this.subParts){subPart.setMirror(subPart.getOrigin().getLinkedTo()!=null&&subPart.getOrigin().getLinkedTo().isMirroring());}
        for(Attachable object:this.attached){if(object!=null)object.setMirror(object.getOrigin().getLinkedTo().isMirroring());}
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

    public void refreshTransforms(){for(SubPart subPart:this.subParts){subPart.refreshTransforms();}}

    public void setFacingRight(boolean facingRight){
        for(SubPart subPart:this.subParts){subPart.setFacingRight(facingRight);}
        for(Attachable object:this.attached){if(object!=null)object.setFacingRight(facingRight);}
        this.facingRight=facingRight;
        if(this.origin.getLinkedTo()!=null)this.setViewOrder(this.origin.getLinkedTo().getLayer());
        if(this.origin.getLinkedTo()==null)this.scale.setX(facingRight?1:-1);
    }

    public void setOwner(Creature owner){this.owner=owner;}
    public Attachable[] getAttached(){return this.attached;}
}
