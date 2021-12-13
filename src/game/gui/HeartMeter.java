package game.gui;

import game.entities.players.Player;
import javafx.scene.layout.Region;

public class HeartMeter extends Region implements GUI {
    private final Heart[] hearts;

    public HeartMeter(int x, int y, Player player){
        super();
        this.hearts=new Heart[player.getMaxHealth()-1];
        for(int heart=0;heart<player.getMaxHealth()-1;heart++){
            this.hearts[heart]=new Heart(10*heart,0);
            this.getChildren().add(this.hearts[heart]);
        }
        this.setTranslateX(x);
        this.setTranslateY(y);
        player.addDamageListener((before, after)->this.setHealth(after));
    }

    public void update(long time){
        for(Heart heart:this.hearts){
            heart.update(time);
        }
    }

    public void setHealth(int health){
        for(int heart=0;heart<this.hearts.length;heart++){
            this.hearts[heart].setState((heart<health-1)?0:1);
        }
    }

    public void setX(int x) {this.setTranslateX(x);}
    public void setY(int y) {this.setTranslateY(y);}
}
