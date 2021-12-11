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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameScene extends Scene {
    private static final Pane pane = new Pane();
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
    private static int xMousePos=0;
    private static int yMousePos=0;

    private final int objective=100;
    private int difficulty=3;
    private final Text objectiveTracker = new Text(10,100,"");
    private final ArrayList<OpenMenu> openMenuListeners=new ArrayList<>();

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
        cam = new Camera(-354,213);
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
                pane.getChildren().remove(projectile);
            });
            projectiles.add(projectile);
            pane.getChildren().add(projectile);
        });
        for(int i=0;i<this.difficulty*5;i++){
            AntiHero foe = new AntiHero((int)(Math.random()*(this.objective*80-900))+1000, 350, true, cam);
            foe.addShotListener(shotListener);
            foe.addRemovalListener(() -> {
                creatures.remove(foe);
                pane.getChildren().remove(foe);
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
        for(Room room:backgrounds){
            pane.getChildren().add(room);
        }
        for(Walkable terrain:walkables){
            pane.getChildren().add(terrain);
        }
        pane.getChildren().add(energyBar);
        pane.getChildren().add(heartMeter);
        pane.getChildren().add(this.objectiveTracker);
        /*this.generateNewFoes((int)(Math.random()*3)+1);*/
        for(Creature creature:creatures){pane.getChildren().add(creature);}

        //keybindings
        this.setOnKeyPressed((event)->{
            if(!gameIsRunning&gameIsResettable){
                for(OpenMenu listener:openMenuListeners){
                    listener.onMenuOpen();
                }
            }
            if (event.getCode()== KeyCode.SPACE) {player.jump();}
            if (event.getCode()== KeyCode.A) {player.dash();}
            if (event.getCode()== KeyCode.E) {player.shoot();}
            if (event.getCode()== KeyCode.Q) {
                player.faceLeft();
                player.run();
            }
            if (event.getCode()== KeyCode.D) {
                player.faceRight();
                player.run();
            }
            if (event.getCode()== KeyCode.S) { player.freeFall();}
        });
        this.setOnKeyReleased((event)->{
            if (event.getCode()== KeyCode.Q) {player.stop();}
            if (event.getCode()== KeyCode.D) {player.stop();}
            if (event.getCode()== KeyCode.SPACE) {player.stopJumping();}
            if (event.getCode()== KeyCode.S) {player.stopFreeFall();}
        });

        //mouse position tracking
        this.setOnMouseMoved(event->{
            xMousePos=(int)(event.getSceneX());
            yMousePos=(int)(event.getSceneY());
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

    private void update(long time) {
        for(Creature creature:creatures){creature.update(time);}
        for(GUI guiEl:gui){guiEl.update(time);}
        cam.update();

        //Background update
        for(Room room:backgrounds){
            room.update(time);
        }

        //game.Terrain update
        for(Walkable terrain:walkables){
            terrain.update(time);
        }

        //updating projectiles and deleting when out of bounds
        for(Projectile projectile:projectiles){
            projectile.update(time);
        }

        this.objectiveTracker.setText("Parcourez "+this.objective+"m\n"+(player.getX()/80-1)+"/"+this.objective);

        if(player.getHealth()<0){
            Text text=new Text(200,100,"Défaite");
            text.setFont(Font.font("Verdana",40));
            text.setFill(Color.RED);
            pane.getChildren().add(text);
            this.endGame(time);
        }

        if(player.getX()/80-1>=this.objective){
            Text text=new Text(200,100,"Victoire");
            text.setFont(Font.font("Verdana",40));
            text.setFill(Color.GREEN);
            pane.getChildren().add(text);
            this.difficulty+=1;
            this.endGame(time);
        }
    }

    public void addOpenMenuListener(OpenMenu listener){this.openMenuListeners.add(listener);}
    public static ArrayList<Walkable> getWalkables(){return walkables;}
    public static ArrayList<Creature> getCreatures(){return creatures;}
    public static Player getPlayer(){return player;}
    public static int getMouseX(){return xMousePos+cam.getX();}
    public static int getMouseY(){return yMousePos+cam.getY();}
    public static ArrayList<Projectile> getProjectiles(){return projectiles;}
}