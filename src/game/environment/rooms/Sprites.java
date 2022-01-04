package game.environment.rooms;

import game.AnimatedSprite;

import static java.lang.Long.MAX_VALUE;

public enum Sprites {
    SPACE       (1,1,new long[][]{{MAX_VALUE}},"space.png"),
    DESERT      (800,500,new long[][]{{MAX_VALUE}},"desert.png"),
    MENU        (800,500,new long[][]{{MAX_VALUE}},"Menu.png");

    private final AnimatedSprite sprite;

    Sprites(int width, int height, long[][] durations, String spriteName){
        this.sprite=new AnimatedSprite(width,height,durations,"rooms\\"+spriteName);
    }

    public AnimatedSprite get(){return this.sprite.copy();}
}
