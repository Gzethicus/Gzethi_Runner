package game.entities.assembly.subParts;

import game.AnimatedSprite;

public enum Sprites{
    PH_TORSO(36,73,new long[][]{{100}}, "phTorso.png"),

    PH_THIGH(30,50,new long[][]{{1000,50,50,50}}, "phThighR.png","phThighL.png"),

    PH_LEG(13,35,new long[][]{{800}}, "phLeg.png"),

    PH_FOOT(26,39,new long[][]{{400}}, "phFoot.png"),


    PH_ARM(13,35,new long[][]{{100}}, "phArm.png"),

    PH_FOREARM(13,35,new long[][]{{100}}, "phForearm.png"),
    PH_PLATED_FOREARM(13,35,new long[][]{{100}}, "phPlatedForearm.png"),

    PH_PALM(16,18,new long[][]{{100}}, "phPalm.png"),

    PH_CLAW(7,18,new long[][]{{100}}, "phClaw.png"),


    PH_HEAD(44,57,new long[][]{{100}},"phHead.png");

    private final AnimatedSprite sprite;
    private static final String path="entities\\creatures\\bodyParts\\";

    Sprites(int width, int height, long[][] durations, String spriteName){
        this.sprite=new AnimatedSprite(width,height,durations,path+spriteName);
    }

    Sprites(int width, int height, long[][] durations, String spriteNameR, String spriteNameL){
        this.sprite=new AnimatedSprite(width,height,durations,path+spriteNameR,path+spriteNameL);
    }

    public AnimatedSprite get(){return this.sprite.copy();}
}
