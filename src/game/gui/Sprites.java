package game.gui;

import game.AnimatedSprite;

import static java.lang.Long.MAX_VALUE;

public enum Sprites{
    HEART           (9,9,new long[][]{{MAX_VALUE},{MAX_VALUE}},"heart.png"),

    ENERGY          (40,5,new long[][]{{1000,100,100,100,100,100}},"energy.png"),
    ENERGYFRAME     (44,9,new long[][]{{MAX_VALUE}},"energyFrame.png"),

    FRAME           (200,250,new long[][]{{MAX_VALUE},{MAX_VALUE},{MAX_VALUE}},"characterFrame.png"),

    KEYBINDS        (375,205,new long[][]{{MAX_VALUE}},"keyBinds.png"),

    LOADING         (50,50,new long[][]{{167,133,100,100,133,167,200,167,133,100,100,133,167,200}},"loading.png");

    private final AnimatedSprite sprite;

    Sprites(int width, int height, long[][] durations, String spriteName){
        this.sprite=new AnimatedSprite(width,height,durations,"GUI\\"+spriteName);
    }

    public AnimatedSprite get(double x, double y){
        AnimatedSprite copy=this.sprite.copy();
        copy.setTranslateX(x);
        copy.setTranslateY(y);
        return copy;
    }
    public AnimatedSprite get(){return this.sprite.copy();}
}
