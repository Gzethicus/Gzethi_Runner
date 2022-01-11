package game.entities.equipment.subParts;

import game.AnimatedSprite;

public enum Sprites {
    PHASEBLADE      (120,39,new long[][]{{100}}, "phaseBlade.png"),;

    private final AnimatedSprite sprite;
    private static final String path="entities\\items\\";

    Sprites(int width, int height, long[][] durations, String spriteName){
        this.sprite=new AnimatedSprite(width,height,durations,path+spriteName);
    }

    Sprites(int width, int height, long[][] durations, String spriteNameR, String spriteNameL){
        this.sprite=new AnimatedSprite(width,height,durations,path+spriteNameR,path+spriteNameL);
    }

    public AnimatedSprite get(){return this.sprite.copy();}
}
