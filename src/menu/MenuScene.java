package menu;

import game.Camera;
import game.Hero;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import game.StaticThing;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuScene extends Scene {
    private static final Pane pane = new Pane();
    private final StaticThing background=new StaticThing(0,-100,800,500,"sprites\\menu.png");
    private final ArrayList<StartGame> startGameListeners=new ArrayList<>();
    private final ArrayList<CloseWindow> closeWindowListener=new ArrayList<>();
    private boolean cheats=false;

    public MenuScene(){super(pane,800,400);}

    public void mainMenu(){
        Button startButton=new Button(250,30,300,100,"Start");
        startButton.addButtonListener(()-> {for(StartGame listener:startGameListeners){listener.onGameStart(cheats);}});

        Button optionsButton=new Button(250,150,300,100,"Options");
        optionsButton.addButtonListener(this::optionsMenu);

        Button quitButton=new Button(250,270,300,100,"Quit");
        quitButton.addButtonListener(()-> {for(CloseWindow listener:closeWindowListener){listener.onWindowClose();}});

        pane.getChildren().clear();
        pane.getChildren().add(this.background.getImage());
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
        pane.getChildren().add(this.background.getImage());
        pane.getChildren().add(characterSelect);
        pane.getChildren().add(keyBindings);
        pane.getChildren().add(cheats);
        pane.getChildren().add(back);
    }


    private void charactersMenu() {
        Camera phCam=new Camera(0,0);

        Text text1=new Text(150,45,"Hero");
        text1.setFont(Font.font("Verdana",40));
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setWrappingWidth(200);
        Text text2=new Text(425,45,"GZ-37 (unimplemented)");
        text2.setFont(Font.font("Verdana",20));
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setWrappingWidth(250);

        StaticThing frame1=new StaticThing(150,50,200,250,"sprites\\characterFrame.png");
        StaticThing frame2=new StaticThing(450,50,200,250,"sprites\\characterFrame.png");

        Hero character1=new Hero(174,75,77,100,150,6,200,false,"sprites\\hero.png");
        Hero character2=new Hero(474,75,163,200,100,8,200,false,"sprites\\gz-37.png");

        AnimationTimer timer1 = new AnimationTimer() {
            @Override
            public void handle(long time) {
                character1.update(time/1000000,phCam);
            }
        };

        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long time) {
                character2.update(time/1000000,phCam);
            }
        };

        Rectangle area1=new Rectangle(150,50,200,250);
        area1.setFill(Color.TRANSPARENT);
        area1.setOnMouseEntered(evt-> {
            timer1.start();
            frame1.setFrame(2);
        });
        area1.setOnMouseExited(evt-> {
            timer1.stop();
            frame1.setFrame(0);
        });
        Rectangle area2=new Rectangle(450,50,200,250);
        area2.setFill(Color.TRANSPARENT);
        area2.setOnMouseEntered(evt-> {
            timer2.start();
            frame2.setFrame(1);
        });
        area2.setOnMouseExited(evt-> {
            timer2.stop();
            frame2.setFrame(0);
        });

        Button back=new Button(575,325,200,50,"Back");
        back.addButtonListener(this::optionsMenu);

        pane.getChildren().clear();
        pane.getChildren().add(this.background.getImage());
        pane.getChildren().add(text1);
        pane.getChildren().add(text2);
        pane.getChildren().add(back);
        pane.getChildren().add(frame1.getImage());
        pane.getChildren().add(frame2.getImage());
        pane.getChildren().add(character1.getImage());
        pane.getChildren().add(character2.getImage());
        pane.getChildren().add(area1);
        pane.getChildren().add(area2);
    }

    private void keyBindingMenu() {
        Button back=new Button(575,325,200,50,"Back");
        back.addButtonListener(this::optionsMenu);

        StaticThing keyBinds=new StaticThing(212,75,375,205,"sprites\\keyBinds.png");

        pane.getChildren().clear();
        pane.getChildren().add(this.background.getImage());
        pane.getChildren().add(keyBinds.getImage());
        pane.getChildren().add(back);
    }

    public void addStartGameListener(StartGame listener){
        this.startGameListeners.add(listener);
    }

    public void addCloseWindowListener(CloseWindow listener){
        this.closeWindowListener.add(listener);
    }
}
