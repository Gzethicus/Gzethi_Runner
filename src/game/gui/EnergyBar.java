package game.gui;

import game.entities.players.Player;
import javafx.scene.layout.Region;

public class EnergyBar extends Region implements GUI {
    private final int maxEnergy;
    private final Energy energy;
    private final EnergyFrame frame;

    public EnergyBar(int x, int y, Player player){
        super();
        this.maxEnergy=player.getMaxEnergy();
        this.energy=new Energy(2,2);
        this.frame = new EnergyFrame(0, 0);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.getChildren().add(this.energy);
        this.getChildren().add(this.frame);
        player.addEnergyListener(((before, after)->this.setEnergy(after)));
    }

    public void setEnergy(int energy){
        this.energy.setEnergy(40*energy/this.maxEnergy);
    }

    public void update(long time){
        this.energy.update(time);
        this.frame.update(time);
    }

    public void setX(int x) {this.setTranslateX(x);}
    public void setY(int y) {this.setTranslateY(y);}
}
