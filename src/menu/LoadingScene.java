package menu;

import game.AnimatedThing;
import game.Camera;
import game.environment.Room;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class LoadingScene extends Scene {
    private static final Pane pane = new Pane();
    private final AnimatedThing logo=new AnimatedThing(525,225,51,50,100,0,"sprites\\loading.png");

    public LoadingScene(){
        super(pane,600,300);
        int[] maxFrames={14};
        this.logo.setMaxFrames(maxFrames);
        Room background = new Room(null,'r',new Camera(0,100),"menu.png");
        pane.getChildren().add(background);
        pane.getChildren().add(this.logo.getImage());
        AnimationTimer loadingTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                logo.update(time / 1000000, new Camera(0, 0));
            }
        };
        loadingTimer.start();
    }
}
