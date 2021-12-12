package menu;

import game.Camera;
import game.gui.LoadingLogo;
import game.environment.Room;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class LoadingScene extends Scene {
    private static final Pane pane = new Pane();
    private final LoadingLogo logo=new LoadingLogo(525,225);

    public LoadingScene(){
        super(pane,600,300);
        Room background = new Room(null,'r',new Camera(0,100, pane),"menu.png");
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
