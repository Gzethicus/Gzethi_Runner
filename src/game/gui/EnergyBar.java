package game.gui;

import game.entities.players.Player;
import javafx.scene.Parent;

public class EnergyBar extends Parent implements GUI {
    private final int maxEnergy;
    private final Energy energy;
    private final EnergyFrame frame;

    public EnergyBar(int x, int y, Player player){
        super();
        this.maxEnergy=player.getMaxEnergy();
        this.energy=new Energy(x+2,y+2);
        this.frame = new EnergyFrame(x, y);
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
}
