package game;

import game.entities.Creature;
import game.entities.Shot;
import game.entities.npc.AntiHero;
import game.entities.players.Gz_37;
import game.entities.players.Hero;
import game.entities.players.Player;
import game.entities.projectiles.Projectile;
import game.environment.*;
import game.gui.EnergyBar;
import game.gui.GUI;
import game.gui.HeartMeter;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static java.lang.Math.*;

public class GameScene extends Scene {
    private static final StackPane pane = new StackPane();
    private static final Pane worldPane = new Pane();
    private static final Pane guiPane = new Pane();
    private final AnimationTimer timer;

    private Boolean gameIsRunning=false;
    private Boolean gameIsResettable=false;
    long gameEndedAt=0;

    private static Camera cam;
    private static Player player;
    private static final ArrayList<Room> backgrounds = new ArrayList<>();
    private static final ArrayList<Walkable> walkables = new ArrayList<>();
    private static final ArrayList<Creature> creatures = new ArrayList<>();
    private static final ArrayList<GUI> gui = new ArrayList<>();
    private static final ArrayList<Projectile> projectiles = new ArrayList<>();
    private final ArrayList<KeyCode> keyPresses=new ArrayList<>();
    private static double xMousePos=0;
    private static double yMousePos=0;

    private final int objective=100;
    private int difficulty=3;
    private final Text objectiveTracker = new Text(10,100,"");
    private final ArrayList<OpenMenu> openMenuListeners=new ArrayList<>();

    private long lastRotated=0;

    public GameScene() {
        super(pane,1400,300);
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                if (gameIsRunning) {
                    update(time / 1000000);
                } else {
                    gameIsResettable = time / 1000000 - gameEndedAt > 1000;
                    if(gameIsResettable){timer.stop();}
                }
            }
        };
    }

    public void startGame(int character, boolean cheats){
        backgrounds.clear();
        walkables.clear();
        creatures.clear();
        projectiles.clear();
        this.gameIsRunning=true;
        cam = new Camera(150,400,worldPane);
        backgrounds.add(new Room(null, 'r', cam, "desert.png"));
        backgrounds.add(new Room(backgrounds.get(0), 'l', cam, "desert.png"));
        for(int i=1;i<(objective/10)+2;i++){
            backgrounds.add(new Room(backgrounds.get(i), 'r', cam, "desert.png"));
        }
        walkables.add(new Platform(500,320,cam));
        walkables.add(new Platform(800,250,cam));
        walkables.add(new Obstacle(0,450,this.objective*80+200,1,cam,"invisible.png"));
        walkables.add(new Obstacle(0,-1,this.objective*80+200,1,cam,"invisible.png"));
        walkables.add(new Obstacle(-1,0,1,450,cam,"invisible.png"));
        walkables.add(new Obstacle(this.objective*80+200,0,1,450,cam,"invisible.png"));

        Shot shotListener=(projectile -> {
            projectile.addRemovalListener(() -> {
                projectiles.remove(projectile);
                worldPane.getChildren().remove(projectile);
            });
            projectiles.add(projectile);
            worldPane.getChildren().add(projectile);
        });
        for(int i=0;i<this.difficulty*5;i++){
            AntiHero foe = new AntiHero((int)(Math.random()*(this.objective*80-900))+1000, 350, true, cam);
            foe.addShotListener(shotListener);
            foe.addRemovalListener(() -> {
                creatures.remove(foe);
                worldPane.getChildren().remove(foe);
            });
            creatures.add(foe);
        }

        player =character==0?new Hero(100,350,cheats?10:3,cam):new Gz_37(100,350,cheats?10:3,cam);
        cam.setTarget(player);
        player.addShotListener(shotListener);
        creatures.add(player);

        HeartMeter heartMeter=new HeartMeter(10,10,player);
        EnergyBar energyBar = new EnergyBar(578, 275, player);
        gui.add(heartMeter);
        gui.add(energyBar);

        //putting images on window
        pane.getChildren().clear();
        worldPane.getChildren().clear();
        guiPane.getChildren().clear();
        pane.getChildren().add(worldPane);
        pane.getChildren().add(guiPane);
        for(Room room:backgrounds){
            worldPane.getChildren().add(room);
        }
        for(Walkable terrain:walkables){
            worldPane.getChildren().add(terrain);
        }
        guiPane.getChildren().add(energyBar);
        guiPane.getChildren().add(heartMeter);
        guiPane.getChildren().add(this.objectiveTracker);
        for(Creature creature:creatures){worldPane.getChildren().add(creature);}

        //key tracking
        this.setOnKeyPressed((event)->{
            if(!gameIsRunning&gameIsResettable){
                this.keyPresses.clear();
                for(OpenMenu listener:openMenuListeners){
                    listener.onMenuOpen();
                }
            }else{
                boolean isInList=false;
                for(KeyCode keyCode:keyPresses){
                    if(keyCode.equals(event.getCode())){
                        isInList=true;
                        break;
                    }
                }
                if(!isInList){this.keyPresses.add(event.getCode());}
            }
        });
        this.setOnKeyReleased((event)->this.keyPresses.remove(event.getCode()));

        //mouse position tracking
        this.setOnMouseMoved(event->{
            xMousePos=event.getSceneX()-pane.getWidth()/2;
            yMousePos=event.getSceneY()-pane.getHeight()/2;
        });
        this.timer.start();
    }

    private void endGame(long time){
        this.gameEndedAt=time;
        this.gameIsRunning=false;
        this.gameIsResettable=false;
        backgrounds.clear();
        walkables.clear();
        creatures.clear();
        projectiles.clear();
    }

    private void update(long time){
        //keybindings
        for(KeyCode keyCode:keyPresses){
            if(keyCode==KeyCode.SPACE){player.jump(time);}
            if(keyCode==KeyCode.A){player.dash(time);}
            if(keyCode==KeyCode.E){player.shoot(time);}
            if(keyCode==KeyCode.Q){
                player.faceLeft();
                player.run();
            }
            if(keyCode==KeyCode.D){
                player.faceRight();
                player.run();
            }
            if(keyCode==KeyCode.S){player.freeFall();}
            if(keyCode==KeyCode.J&time-this.lastRotated>200){
                cam.rotate(time,0,1000);
                this.lastRotated=time;
            }
            if(keyCode==KeyCode.U&time-this.lastRotated>200){
                cam.rotate(time,60,1000);
                this.lastRotated=time;
            }
            if(keyCode==KeyCode.Y&time-this.lastRotated>200){
                cam.rotate(time,120,1000);
                this.lastRotated=time;
            }
            if(keyCode==KeyCode.G&time-this.lastRotated>200){
                cam.rotate(time,180,1000);
                this.lastRotated=time;
            }
            if(keyCode==KeyCode.B&time-this.lastRotated>200){
                cam.rotate(time,240,1000);
                this.lastRotated=time;
            }
            if(keyCode==KeyCode.N&time-this.lastRotated>200){
                cam.rotate(time,300,1000);
                this.lastRotated=time;
            }
        }

        for(Creature creature:creatures){creature.update(time);}
        for(GUI guiEl:gui){guiEl.update(time);}
        cam.update(time);

        //updating projectiles
        for(Projectile projectile:projectiles){projectile.update(time);}

        //Background update
        for(Room room:backgrounds){room.update(time);}

        //terrain update
        for(Walkable terrain:walkables){terrain.update(time);}

        this.objectiveTracker.setText("Parcourez "+this.objective+"m\n"+(player.getX()/80-1)+"/"+this.objective);

        if(player.getHealth()<0){
            Text text=new Text(200,100,"Défaite");
            text.setFont(Font.font("Verdana",40));
            text.setFill(Color.RED);
            guiPane.getChildren().add(text);
            this.endGame(time);
        }

        if(player.getX()/80-1>=this.objective){
            Text text=new Text(200,100,"Victoire");
            text.setFont(Font.font("Verdana",40));
            text.setFill(Color.GREEN);
            guiPane.getChildren().add(text);
            this.difficulty+=1;
            this.endGame(time);
        }
    }

    public void addOpenMenuListener(OpenMenu listener){this.openMenuListeners.add(listener);}
    public static ArrayList<Walkable> getWalkables(){return walkables;}
    public static ArrayList<Creature> getCreatures(){return creatures;}
    public static Player getPlayer(){return player;}
    public static double getMouseX(){
        double angle=worldPane.getRotate()*PI/180;
        return cam.getX()+xMousePos*cos(angle)+yMousePos*sin(angle);
    }
    public static double getMouseY(){
        double angle=worldPane.getRotate()*PI/180;
        return cam.getY()+yMousePos*cos(angle)-xMousePos*sin(angle);
    }
    public static ArrayList<Projectile> getProjectiles(){return projectiles;}
}