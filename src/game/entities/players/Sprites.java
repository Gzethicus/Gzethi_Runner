package game.entities.players;

import game.AnimatedSprite;

import static java.lang.Long.MAX_VALUE;

public enum Sprites{
    HERO        (77,100,new long[][]{{MAX_VALUE},{150,150,150,150,150,150},{MAX_VALUE},{MAX_VALUE},{MAX_VALUE},{150,150,150,150,150,150},{MAX_VALUE},{MAX_VALUE},{150,150,150,150,150,150}},"hero.png"),
    GZ_37       (163,200,new long[][]{{MAX_VALUE},{100,100,100,100,100,100,100,100},{MAX_VALUE},{MAX_VALUE},{MAX_VALUE},{MAX_VALUE},{100,100,100,100,100,100,100,100},{MAX_VALUE},{MAX_VALUE},{MAX_VALUE},{100,100,100,100,100,100,100,100}},"gz-37.png");

    private final AnimatedSprite sprite;

    Sprites(int width, int height, long[][] durations, String spriteName){
        this.sprite=new AnimatedSprite(width,height,durations,"entities\\creatures\\players\\"+spriteName);
    }

    public AnimatedSprite get(){return this.sprite.copy();}
}
