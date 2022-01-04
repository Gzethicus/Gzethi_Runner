package game.gui;

import game.AnimatedSprite;
import game.entities.players.Player;
import javafx.scene.layout.Region;

public class EnergyBar extends Region implements GUI {
    private final int maxEnergy;
    private final AnimatedSprite energy;
    private final AnimatedSprite frame;

    public EnergyBar(double x, double y, Player player){
        super();
        this.maxEnergy=player.getMaxEnergy();
        this.energy=Sprites.ENERGY.get(2,2);
        this.frame=Sprites.ENERGYFRAME.get();
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.getChildren().add(this.energy);
        this.getChildren().add(this.frame);
        player.addEnergyListener(((before, after)->this.setEnergy(after)));
    }

    public void update(long time){
        this.energy.update(time);
        this.frame.update(time);
    }

    public void setEnergy(int energy){this.energy.setWidth(40*energy/this.maxEnergy);}
    public void setX(int x) {this.setTranslateX(x);}
    public void setY(int y) {this.setTranslateY(y);}
}
