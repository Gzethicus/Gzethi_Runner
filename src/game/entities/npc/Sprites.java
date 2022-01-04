package game.entities.npc;

import game.AnimatedSprite;

import static java.lang.Long.MAX_VALUE;

public enum Sprites{
    ANTIHERO        (77,100,new long[][]{{MAX_VALUE},{150,150,150,150,150,150},{MAX_VALUE},{MAX_VALUE},{150,200,250,100},{150,150,150,150,150,150},{MAX_VALUE},{MAX_VALUE},{150,150,150,150,150,150}},"antihero.png");
    private final AnimatedSprite sprite;

    Sprites(int width, int height, long[][] durations, String spriteName){
        this.sprite=new AnimatedSprite(width,height,durations,"entities\\creatures\\"+spriteName);
    }

    public AnimatedSprite get(){return this.sprite.copy();}
}
