package menu;

import game.AnimatedSprite;
import game.environment.rooms.Sprites;
import game.environment.rooms.Room;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class LoadingScene extends Scene {
    private static final Pane pane = new Pane();
    private final AnimatedSprite logo= game.gui.Sprites.LOADING.get(525, 225);

    public LoadingScene(){
        super(pane,600,300);
        Room background = new Room(0,0, Sprites.MENU.get());
        pane.getChildren().add(background);
        pane.getChildren().add(this.logo);
        AnimationTimer loadingTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                logo.update(time / 1000000);
            }
        };
        loadingTimer.start();
    }
}
