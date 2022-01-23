package game;

import com.sun.javafx.scene.text.TextLayout;
import game.entities.*;
import game.entities.assembly.constructed.Gz_37;
import game.entities.equipment.subParts.HitBoxes;
import game.entities.npc.*;
import game.entities.players.*;
import game.entities.projectiles.*;
import game.environment.*;
import game.environment.obstacles.*;
import game.environment.obstacles.Sprites;
import game.environment.platforms.*;
import game.environment.rooms.*;
import game.gui.*;
import game.physics.HitBox;
import game.physics.Intersectible;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static java.lang.Math.*;

public class GameScene extends Scene {
    private static final StackPane pane = new StackPane();
    private static final Pane spacePane = new Pane();
    private static final Pane worldPane = new Pane();
    private static final Pane guiPane = new Pane();
    private final AnimationTimer timer;
    private static final double pixelToMeter=80;

    private Boolean gameIsRunning=false;
    private Boolean gameIsResettable=false;
    long gameEndedAt=0;

    private static Camera cam;
    private static Player player;
    private static final Space space=new Space();
    private static final ArrayList<Room> backgrounds = new ArrayList<>();
    private static final ArrayList<Walkable> walkables = new ArrayList<>();
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static final ArrayList<GUI> gui = new ArrayList<>();
    private static final ArrayList<WorldElement> deletionList = new ArrayList<>();

    private final ArrayList<KeyCode> keyPresses=new ArrayList<>();
    private final ArrayList<MouseButton> mousePresses=new ArrayList<>();
    private boolean control;
    private boolean shift;
    private static double xMousePos=0;
    private static double yMousePos=0;

    private final int objective=100;
    private int difficulty=3;
    private final Text objectiveTracker = new Text(10,100,"");
    private final ArrayList<OpenMenu> openMenuListeners=new ArrayList<>();

    private long lastRotated=0;

    public GameScene() {
        super(pane,1400,500);
        this.timer = new AnimationTimer() {
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
        //clearing any potential leftover
        backgrounds.clear();
        walkables.clear();
        entities.clear();
        pane.getChildren().clear();
        spacePane.getChildren().clear();
        worldPane.getChildren().clear();
        guiPane.getChildren().clear();

        this.gameIsRunning=true;
        cam = new Camera(150,400,worldPane);
        backgrounds.add(new Room(-800, 0, game.environment.rooms.Sprites.DESERT.get()));
        backgrounds.add(new Room(0, 0, game.environment.rooms.Sprites.DESERT.get()));
        backgrounds.get(0).setNeighbour(backgrounds.get(1),1,0);
        backgrounds.get(0).setNeighbour(space,0,0);
        for(int i=1;i<(objective/10)+3;i++){
            backgrounds.add(new Room(800*i, 0, game.environment.rooms.Sprites.DESERT.get()));
            backgrounds.get(i+1).setNeighbour(backgrounds.get(i),0,1);
        }
        backgrounds.get(backgrounds.size()-1).setNeighbour(space,1,0);
        walkables.add(new Platform(500,320));
        walkables.add(new Platform(800,250));
        walkables.add(new Obstacle(0,450,this.objective*80+200,1, Sprites.INVISIBLE.get()));
        walkables.add(new Obstacle(0,-1,this.objective*80+200,1,Sprites.INVISIBLE.get()));
        walkables.add(new Obstacle(-1,0,1,450,Sprites.INVISIBLE.get()));
        //walkables.add(new Obstacle(800,350,1,100,Sprites.INVISIBLE.get()));       //tester's wall
        walkables.add(new Obstacle(this.objective*80+200,0,1,450,Sprites.INVISIBLE.get()));

        Shot shotListener=(projectile -> {
            entities.add(projectile);
            worldPane.getChildren().add(projectile);
        });
        /*for(int i=0;i<this.difficulty*5;i++){
            AntiHero foe = new AntiHero((int)(Math.random()*(this.objective*80-900))+1000, 350,backgrounds.get(2),false);
            foe.addShotListener(shotListener);
            entities.add(foe);
        }*/

        player =character==0?new Hero(100,350, backgrounds.get(0),cheats?10:4):new game.entities.assembly.constructed.Gz_37(100,315, backgrounds.get(0),cheats?10:4);
        cam.setTarget(player);
        player.addShotListener(shotListener);
        entities.add(player);

        HeartMeter heartMeter=new HeartMeter(10, 10, player);
        EnergyBar energyBar=new EnergyBar(guiPane.getWidth()/2-22, guiPane.getHeight()-20, player);
        gui.add(heartMeter);
        gui.add(energyBar);

        //putting images on window
        pane.getChildren().add(spacePane);
        pane.getChildren().add(worldPane);
        pane.getChildren().add(guiPane);
        spacePane.getChildren().add(space);
        for(Room room:backgrounds){
            worldPane.getChildren().add(room);
            room.setViewOrder(100);
        }
        for(Walkable terrain:walkables){
            worldPane.getChildren().add(terrain);
        }
        guiPane.getChildren().add(energyBar);
        guiPane.getChildren().add(heartMeter);
        guiPane.getChildren().add(this.objectiveTracker);
        for(Entity entity:entities){worldPane.getChildren().add(entity);}

        //key tracking
        this.setOnKeyPressed((event)->{
            if(!gameIsRunning&gameIsResettable){
                this.keyPresses.clear();
                this.mousePresses.clear();
                for(OpenMenu listener:openMenuListeners){listener.onMenuOpen();}
            }else{
                if(event.getCode()==KeyCode.SHIFT)this.shift=true;
                if(event.getCode()==KeyCode.CONTROL)this.control=true;
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
        this.setOnKeyReleased((event)->{
            if(event.getCode()==KeyCode.SHIFT)this.shift=false;
            if(event.getCode()==KeyCode.CONTROL)this.control=false;
            this.keyPresses.remove(event.getCode());
        });

        //mouse click tracking
        this.setOnMousePressed((event)->{
            if(!gameIsRunning&gameIsResettable){
                this.keyPresses.clear();
                this.mousePresses.clear();
                for(OpenMenu listener:openMenuListeners){listener.onMenuOpen();}
            }else{
                boolean isInList=false;
                for(MouseButton mouseButton:mousePresses){
                    if(mouseButton.equals(event.getButton())){
                        isInList=true;
                        break;
                    }
                }
                if(!isInList){this.mousePresses.add(event.getButton());}
            }
        });
        this.setOnMouseReleased((event)->this.mousePresses.remove(event.getButton()));

        //mouse position tracking
        this.setOnMouseMoved(event->{
            xMousePos=event.getSceneX()-pane.getWidth()/2;
            yMousePos=event.getSceneY()-pane.getHeight()/2;
        });

        //UI position handling
        guiPane.widthProperty().addListener((obs,oldVal,newVal)-> {
            gui.get(0).setX(10);
            gui.get(1).setX(newVal.intValue() / 2 - 22);
        });
        guiPane.heightProperty().addListener((obs,oldVal,newVal)-> {
            gui.get(0).setY(10);
            gui.get(1).setY(newVal.intValue() - 20);
        });
        this.timer.start();
    }

    private void endGame(long time){
        this.gameEndedAt=time;
        this.gameIsRunning=false;
        this.gameIsResettable=false;
        backgrounds.clear();
        walkables.clear();
        entities.clear();
        gui.clear();
        deletionList.clear();
        keyPresses.clear();
    }

    private void update(long time){
        //mousebindings
        for(MouseButton mouseButton:mousePresses){
            if(mouseButton==MouseButton.PRIMARY){
                if(this.control)player.detach(1,time);
                else player.mainAction(time);
            }
            else if(mouseButton==MouseButton.SECONDARY){
                if(this.control)player.detach(2,time);
                else player.secondaryAction(time);}
        }

        //keybindings
        for(KeyCode keyCode:keyPresses){
            if(keyCode==KeyCode.SPACE){player.jump(time);}
            else if(keyCode==KeyCode.A){player.dash(time);}
            else if(keyCode==KeyCode.E){player.shoot(time);}
            else if(keyCode==KeyCode.Q){player.run(false);}
            else if(keyCode==KeyCode.D){player.run(true);}
            else if(keyCode==KeyCode.S){player.freeFall();}
            else if(keyCode==KeyCode.J&time-this.lastRotated>200){
                cam.rotate(time,0,1000);
                this.lastRotated=time;
            }
            else if(keyCode==KeyCode.U&time-this.lastRotated>200){
                cam.rotate(time,60,1000);
                this.lastRotated=time;
            }
            else if(keyCode==KeyCode.Y&time-this.lastRotated>200){
                cam.rotate(time,120,1000);
                this.lastRotated=time;
            }
            else if(keyCode==KeyCode.G&time-this.lastRotated>200){
                cam.rotate(time,180,1000);
                this.lastRotated=time;
            }
            else if(keyCode==KeyCode.B&time-this.lastRotated>200){
                cam.rotate(time,240,1000);
                this.lastRotated=time;
            }
            else if(keyCode==KeyCode.N&time-this.lastRotated>200){
                cam.rotate(time,300,1000);
                this.lastRotated=time;
            }
        }

        for(Entity entity:entities){entity.update(time);}
        for(GUI guiEl:gui){guiEl.update(time);}
        cam.update(time);

        for(WorldElement toBeDeleted:deletionList){this.delete(toBeDeleted);}
        deletionList.clear();

        //Background update
        for(Room room:backgrounds){room.update(time);}

        //terrain update
        for(Walkable terrain:walkables){terrain.update(time);}

        this.objectiveTracker.setText("Parcourez "+this.objective+"m\n"+(int)(player.getX()/80-1)+"/"+this.objective);

        if(player.getHealth()<=0){
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

    private void delete(WorldElement toBeDeleted){
        worldPane.getChildren().remove(toBeDeleted);
        if(toBeDeleted instanceof Entity){
            entities.remove(toBeDeleted);
        }else{
            System.out.println("Can't process deletion!");
        }
    }

    public static double getMouseX(){
        double angle=worldPane.getRotate()*PI/180;
        return cam.getX()+xMousePos*cos(angle)+yMousePos*sin(angle);
    }
    public static double getMouseY(){
        double angle=worldPane.getRotate()*PI/180;
        return cam.getY()+yMousePos*cos(angle)-xMousePos*sin(angle);
    }

    public static void addToWorld(Node child){
        worldPane.getChildren().add(child);
        if(child instanceof Entity){
            entities.add((Entity)child);
        }
    }

    public static double getPixelToMeter(){return pixelToMeter;}
    public void addOpenMenuListener(OpenMenu listener){this.openMenuListeners.add(listener);}
    public static ArrayList<Walkable> getWalkables(){return walkables;}
    public static ArrayList<Entity> getEntities(){return entities;}
    public static Player getPlayer(){return player;}
    public static void requestDelete(WorldElement toBeDeleted){deletionList.add(toBeDeleted);}
}