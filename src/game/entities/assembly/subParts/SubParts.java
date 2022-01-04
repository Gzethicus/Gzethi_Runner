package game.entities.assembly.subParts;

import game.AnimatedSprite;
import game.entities.assembly.AttachPoint;
import game.entities.assembly.OriginPoint;
import game.entities.assembly.SubPart;

public enum SubParts{
    TORSO       (AttachSets.TORSO.getOrigin(),AttachSets.TORSO.get(),AttachSets.TORSO.getMirroredIndexes()),

    ARM         (AttachSets.ARM.getOrigin(),AttachSets.ARM.get(),AttachSets.ARM.getMirroredIndexes()),

    FOREARM     (AttachSets.FOREARM.getOrigin(),AttachSets.FOREARM.get(),AttachSets.FOREARM.getMirroredIndexes()),

    PALM        (AttachSets.PALM.getOrigin(),AttachSets.PALM.get(),AttachSets.PALM.getMirroredIndexes()),

    CLAW        (AttachSets.CLAW.getOrigin(),AttachSets.CLAW.get(),AttachSets.CLAW.getMirroredIndexes()),


    THIGH       (AttachSets.THIGH.getOrigin(),AttachSets.THIGH.get(),AttachSets.THIGH.getMirroredIndexes()),

    LEG         (AttachSets.LEG.getOrigin(),AttachSets.LEG.get(),AttachSets.LEG.getMirroredIndexes()),

    FOOT        (AttachSets.FOOT.getOrigin(),AttachSets.FOOT.get(),AttachSets.FOOT.getMirroredIndexes()),


    HEAD        (AttachSets.HEAD.getOrigin(),AttachSets.HEAD.get(),AttachSets.HEAD.getMirroredIndexes());

    private final OriginPoint origin;
    private final AttachPoint[][][] sets;
    private final int[] mirrorIndexes;

    SubParts(OriginPoint origin, AttachPoint[][][] sets, int[][] mirrorIndexes){
        this.origin=origin;
        this.sets=sets;
        this.mirrorIndexes=new int[sets[0][0].length];
        for(int i=0;i<this.mirrorIndexes.length;i++){this.mirrorIndexes[i]=i;}
        if(mirrorIndexes[0].length>0)for(int[] couple:mirrorIndexes){
            this.mirrorIndexes[couple[0]]=couple[1];
            this.mirrorIndexes[couple[1]]=couple[0];
        }
    }

    public SubPart get(AnimatedSprite sprite){
        return new SubPart(new OriginPoint(this.origin.getLocalX(),this.origin.getLocalY()), this.sets, this.mirrorIndexes, sprite);
    }

    public SubPart get(AnimatedSprite sprite, double scaleX, double scaleY){
        SubPart requested=new SubPart(new OriginPoint(this.origin.getLocalX(),this.origin.getLocalY()), this.sets, this.mirrorIndexes, sprite);
        requested.setXScale(scaleX);
        requested.setYScale(scaleY);
        return requested;
    }
}
