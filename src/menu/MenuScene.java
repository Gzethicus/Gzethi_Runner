package menu;

import game.Camera;
import game.GameScene;
import game.entities.players.Gz_37;
import game.entities.players.Hero;
import game.entities.players.Player;
import game.gui.CharacterFrame;
import game.environment.Obstacle;
import game.environment.Room;
import game.gui.GUIElement;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuScene extends Scene {
    private static final Pane pane = new Pane();
    private final Room background=new Room(null,'r',new Camera(0,100, pane),"menu.png");
    private final ArrayList<StartGame> startGameListeners=new ArrayList<>();
    private final ArrayList<CloseWindow> closeWindowListener=new ArrayList<>();
    private boolean cheats=false;
    private int character=0;

    public MenuScene(){super(pane,800,400);}

    public void mainMenu(){
        Button startButton=new Button(250,30,300,100,"Start");
        startButton.addButtonListener(()-> {for(StartGame listener:startGameListeners){listener.onGameStart(this.character,this.cheats);}});

        Button optionsButton=new Button(250,150,300,100,"Options");
        optionsButton.addButtonListener(this::optionsMenu);

        Button quitButton=new Button(250,270,300,100,"Quit");
        quitButton.addButtonListener(()-> {for(CloseWindow listener:closeWindowListener){listener.onWindowClose();}});

        pane.getChildren().clear();
        pane.getChildren().add(this.background);
        pane.getChildren().add(startButton);
        pane.getChildren().add(optionsButton);
        pane.getChildren().add(quitButton);
    }

    private void optionsMenu() {
        Button characterSelect=new Button(250,24,300,75,"Characters");
        characterSelect.addButtonListener(this::charactersMenu);

        Button keyBindings=new Button(250,115,300,75,"Keybindings");
        keyBindings.addButtonListener(this::keyBindingMenu);

        Button cheats=new Button(250,206,300,75,"Cheats : "+(this.cheats?"ON":"OFF"));
        cheats.addButtonListener(()->{
            this.cheats ^= true;
            cheats.setText("Cheats : "+(this.cheats?"ON":"OFF"));
        });

        Button back=new Button(250,297,300,75,"Back");
        back.addButtonListener(this::mainMenu);

        pane.getChildren().clear();
        pane.getChildren().add(this.background);
        pane.getChildren().add(characterSelect);
        pane.getChildren().add(keyBindings);
        pane.getChildren().add(cheats);
        pane.getChildren().add(back);
    }


    private void charactersMenu() {
        Camera phCam=new Camera(0,0, pane);
        GameScene.getWalkables().clear();

        Text text1=new Text(150,45,"Hero");
        text1.setFont(Font.font("Verdana",40));
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setWrappingWidth(200);
        Text text2=new Text(425,45,"GZ-37");
        text2.setFont(Font.font("Verdana",20));
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setWrappingWidth(250);

        CharacterFrame frame1=new CharacterFrame(150,50);
        CharacterFrame frame2=new CharacterFrame(450,50);
        frame1.setState(this.character==0?2:0);
        frame2.setState(this.character==1?2:0);

        Player character1=new Hero(211,125,1,phCam);
        character1.setScaleX(2);
        character1.setScaleY(2);
        Obstacle conveyor1=new Obstacle(150,225,200,1,phCam,"invisible.png");
        GameScene.getWalkables().add(conveyor1);
        Player character2=new Gz_37(509,125,1,phCam);
        character2.setScaleX(2);
        character2.setScaleY(2);
        Obstacle conveyor2=new Obstacle(450,225,200,1,phCam,"invisible.png");
        GameScene.getWalkables().add(conveyor2);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                character1.update(time/1000000);
                character2.update(time/1000000);
            }
        };
        timer.start();

        Rectangle area1=new Rectangle(150,50,200,250);
        area1.setFill(Color.TRANSPARENT);
        area1.setOnMouseEntered(evt-> {
            frame1.setState(2);
            conveyor1.setForcedSpeed(-5);
            character1.autoRun();
        });
        area1.setOnMouseExited(evt-> {
            frame1.setState(this.character==0?2:0);
            conveyor1.setForcedSpeed(0);
            character1.stopAutoRun();
        });
        area1.setOnMouseClicked(evt->{
            this.character=0;
            frame2.setState(0);
        });
        Rectangle area2=new Rectangle(450,50,200,250);
        area2.setFill(Color.TRANSPARENT);
        area2.setOnMouseEntered(evt-> {
            frame2.setState(2);
            conveyor2.setForcedSpeed(-5);
            character2.autoRun();
        });
        area2.setOnMouseExited(evt-> {
            frame2.setState(this.character==1?2:0);
            conveyor2.setForcedSpeed(0);
            character2.stopAutoRun();
        });
        area2.setOnMouseClicked(evt->{
            this.character=1;
            frame1.setState(0);
        });

        Button back=new Button(575,325,200,50,"Back");
        back.addButtonListener(this::optionsMenu);

        pane.getChildren().clear();
        pane.getChildren().add(this.background);
        pane.getChildren().add(text1);
        pane.getChildren().add(text2);
        pane.getChildren().add(back);
        pane.getChildren().add(frame1);
        pane.getChildren().add(frame2);
        pane.getChildren().add(character1);
        pane.getChildren().add(character2);
        pane.getChildren().add(area1);
        pane.getChildren().add(area2);
    }

    private void keyBindingMenu() {
        Button back=new Button(575,325,200,50,"Back");
        back.addButtonListener(this::optionsMenu);

        GUIElement keyBinds=new GUIElement(212,75,375,205,"keyBinds.png");

        pane.getChildren().clear();
        pane.getChildren().add(this.background);
        pane.getChildren().add(keyBinds);
        pane.getChildren().add(back);
    }

    public void addStartGameListener(StartGame listener){
        this.startGameListeners.add(listener);
    }

    public void addCloseWindowListener(CloseWindow listener){
        this.closeWindowListener.add(listener);
    }
}
