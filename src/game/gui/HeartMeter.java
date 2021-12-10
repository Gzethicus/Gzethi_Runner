package game.gui;

import game.entities.players.Player;
import javafx.scene.Parent;

public class HeartMeter extends Parent {
    private final Heart[] hearts;

    public HeartMeter(int x, int y, Player player){
        super();
        this.hearts=new Heart[player.getMaxHealth()];
        for(int heart=0;heart<player.getMaxHealth();heart++){
            this.hearts[heart]=new Heart(x+10*heart,y);
            this.getChildren().add(this.hearts[heart]);
        }
        player.addDamageListener((before, after)->this.setHealth(after));
    }

    public void setHealth(int health){
        for(int heart=0;heart<this.hearts.length;heart++){
            this.hearts[heart].setState((heart<health)?0:1);
        }
    }
}
