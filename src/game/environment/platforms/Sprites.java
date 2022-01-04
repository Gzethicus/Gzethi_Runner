package game.environment.platforms;

import game.AnimatedSprite;

import static java.lang.Long.MAX_VALUE;

public enum Sprites{
    PLATFORM        (100,20,new long[][]{{MAX_VALUE}},"platform.png");

    private final AnimatedSprite sprite;

    Sprites(int width, int height, long[][] durations, String spriteName){
        this.sprite=new AnimatedSprite(width,height,durations,"walkable\\"+spriteName);
    }

    public AnimatedSprite get(){return this.sprite.copy();}
}
