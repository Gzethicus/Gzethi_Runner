package game.entities.assembly.subParts;

import game.AnimatedSprite;
import game.entities.assembly.AttachPoint;
import game.entities.assembly.OriginPoint;
import game.entities.assembly.SubPart;

public enum SubParts{
    TORSO       (AttachSets.TORSO, HitBoxes.TORSO0),

    ARM         (AttachSets.ARM, HitBoxes.ARM0),

    FOREARM     (AttachSets.FOREARM, HitBoxes.FOREARM0),

    PALM        (AttachSets.PALM, HitBoxes.PALM0),

    CLAW        (AttachSets.CLAW, HitBoxes.CLAW0),


    THIGH       (AttachSets.THIGH, HitBoxes.THIGH0),

    LEG         (AttachSets.LEG, HitBoxes.LEG0),

    FOOT        (AttachSets.FOOT, HitBoxes.FOOT0),


    HEAD        (AttachSets.HEAD, HitBoxes.HEAD0);

    private final OriginPoint origin;
    private final AttachPoint[][][] sets;
    private final HitBoxes hitBox;
    private final int[] mirrorIndexes;

    SubParts(AttachSets set, HitBoxes hitBox){
        this.origin=set.getOrigin();
        this.sets=set.get();
        this.hitBox=hitBox;
        int[][] mirrorIndexes=set.getMirroredIndexes();
        this.mirrorIndexes=new int[sets[0][0].length];
        for(int i=0;i<this.mirrorIndexes.length;i++){this.mirrorIndexes[i]=i;}
        if(mirrorIndexes[0].length>0)for(int[] couple:mirrorIndexes){
            this.mirrorIndexes[couple[0]]=couple[1];
            this.mirrorIndexes[couple[1]]=couple[0];
        }
    }

    public SubPart get(AnimatedSprite sprite){
        return new SubPart(new OriginPoint(this.origin.getLocalX(),this.origin.getLocalY()), this.sets, this.mirrorIndexes, sprite, this.hitBox.get());
    }

    public SubPart get(AnimatedSprite sprite, double scaleX, double scaleY){
        SubPart requested=new SubPart(new OriginPoint(this.origin.getLocalX(),this.origin.getLocalY()), this.sets, this.mirrorIndexes, sprite, this.hitBox.get());
        requested.setXScale(scaleX);
        requested.setYScale(scaleY);
        return requested;
    }
}
