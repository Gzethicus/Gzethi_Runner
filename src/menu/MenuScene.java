package menu;

import game.AnimatedSprite;
import game.gui.Sprites;
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
    private final AnimatedSprite background=game.environment.rooms.Sprites.MENU.get();
    private final ArrayList<StartGame> startGameListeners=new ArrayList<>();
    private final ArrayList<CloseWindow> closeWindowListener=new ArrayList<>();
    private boolean cheats=false;
    private int character=1;

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

        Text text1=new Text(150,45,"Hero");
        text1.setFont(Font.font("Verdana",40));
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setWrappingWidth(200);
        Text text2=new Text(425,45,"GZ-37");
        text2.setFont(Font.font("Verdana",20));
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setWrappingWidth(250);

        AnimatedSprite frame0= Sprites.FRAME.get(150,50);
        AnimatedSprite frame1= Sprites.FRAME.get(450,50);
        frame0.setDefaultAnimation(this.character==0?2:0);
        frame1.setDefaultAnimation(this.character==1?2:0);


        AnimatedSprite character0= game.entities.players.Sprites.HERO.get();
        character0.setPreserveRatio(true);
        character0.setFitHeight(200);
        character0.setTranslateX(250-character0.getBoundsInLocal().getWidth()/2);
        character0.setTranslateY(75);
        character0.setMouseTransparent(true);

        AnimatedSprite character1=game.entities.players.Sprites.GZ_37.get();
        character1.setPreserveRatio(true);
        character1.setFitHeight(200);
        character1.setTranslateX(550-character1.getBoundsInLocal().getWidth()/2);
        character1.setTranslateY(75);
        character1.setMouseTransparent(true);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                character0.update(time/1000000);
                character1.update(time/1000000);
                frame0.update(time/1000000);
                frame1.update(time/1000000);
            }
        };
        timer.start();

        frame0.setOnMouseEntered(evt-> {
            frame0.setDefaultAnimation(2);
            character0.setDefaultAnimation(1);
        });
        frame0.setOnMouseExited(evt-> {
            frame0.setDefaultAnimation(this.character==0?2:0);
            character0.setDefaultAnimation(0);
        });
        frame0.setOnMouseClicked(evt->{
            this.character=0;
            frame1.setDefaultAnimation(0);
        });

        frame1.setOnMouseEntered(evt-> {
            frame1.setDefaultAnimation(2);
            character1.setDefaultAnimation(1);
        });
        frame1.setOnMouseExited(evt-> {
            frame1.setDefaultAnimation(this.character==1?2:0);
            character1.setDefaultAnimation(0);
        });
        frame1.setOnMouseClicked(evt->{
            this.character=1;
            frame0.setDefaultAnimation(0);
        });

        Button back=new Button(575,325,200,50,"Back");
        back.addButtonListener(this::optionsMenu);

        pane.getChildren().clear();
        pane.getChildren().add(this.background);
        pane.getChildren().add(text1);
        pane.getChildren().add(text2);
        pane.getChildren().add(back);
        pane.getChildren().add(frame0);
        pane.getChildren().add(frame1);
        pane.getChildren().add(character0);
        pane.getChildren().add(character1);
    }

    private void keyBindingMenu() {
        Button back=new Button(575,325,200,50,"Back");
        back.addButtonListener(this::optionsMenu);

        AnimatedSprite keyBinds= game.gui.Sprites.KEYBINDS.get(212,75);

        pane.getChildren().clear();
        pane.getChildren().add(this.background);
        pane.getChildren().add(keyBinds);
        pane.getChildren().add(back);
    }

    public void addStartGameListener(StartGame listener){this.startGameListeners.add(listener);}
    public void addCloseWindowListener(CloseWindow listener){this.closeWindowListener.add(listener);}
}
