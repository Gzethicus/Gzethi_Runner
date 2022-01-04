package game.entities.projectiles;

import game.AnimatedSprite;

import static java.lang.Long.MAX_VALUE;

public enum Sprites{
    CYAN_LASER      (23,8,new long[][]{{MAX_VALUE}},"cyan_laser.png"),
    RED_LASER       (23,8,new long[][]{{MAX_VALUE}},"red_laser.png"),;

    private final AnimatedSprite sprite;

    Sprites(int width, int height, long[][] durations, String spriteName){
        this.sprite=new AnimatedSprite(width,height,durations,"entities\\projectiles\\"+spriteName);
    }

    public AnimatedSprite get(){return this.sprite.copy();}
}
