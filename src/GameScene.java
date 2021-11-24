import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;

public class GameScene extends Scene {
    private static final Pane pane = new Pane();
    private final Stage primaryStage;
    private Boolean gameIsRunning;
    private Boolean gameIsResettable=false;
    long gameEndedAt=0;
    private Camera cam;
    private final ArrayList<Room> backgrounds = new ArrayList<>();
    private final ArrayList<Terrain> terrains = new ArrayList<>();
    private StaticThing energy;
    private final ArrayList<StaticThing> liveCounter = new ArrayList<>();
    private final ArrayList<Foe> foes = new ArrayList<>();
    private final ArrayList<Projectile> allyProjectiles = new ArrayList<>();
    private final ArrayList<Projectile> enemyProjectiles = new ArrayList<>();
    private Hero hero;
    private int objective=100;
    private final Text objectiveTracker = new Text(10,100,"");

    public GameScene(Stage primaryStage) {
        super(pane,600,300);
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Runner 1.0");
        this.primaryStage.setScene(this);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                if (gameIsRunning) {
                    update(time / 1000000);
                } else gameIsResettable = time / 1000000 - gameEndedAt > 1000;
            }
        };
        timer.start();

        this.startGame();

        //keybindings
        this.setOnKeyPressed((event)->{
            if(!gameIsRunning&gameIsResettable){
                startGame();
            }
            if (event.getCode()== KeyCode.SPACE) {
                hero.jump();
            }
            if (event.getCode()== KeyCode.E) {
                Projectile newProjectile=hero.attack();
                if(newProjectile!=null) {
                    pane.getChildren().add(newProjectile.getImage());
                    allyProjectiles.add(newProjectile);
                }
            }
            if (event.getCode()== KeyCode.Q) {
                hero.walk();
            }
            if (event.getCode()== KeyCode.D) {
                hero.sprint();
            }
            if (event.getCode()== KeyCode.S) {
                hero.fall();
            }
        });
        this.setOnKeyReleased((event)->{
            if (event.getCode()== KeyCode.Q) {
                hero.run();
            }
            if (event.getCode()== KeyCode.D) {
                hero.run();
            }
            if (event.getCode()== KeyCode.SPACE) {
                hero.stopJumping();
            }
        });
    }

    private void startGame(){
        this.gameIsRunning=true;
        this.cam = new Camera(0,200);
        this.backgrounds.add(new Room(null,'r',800,500,"sprites\\desert.png"));
        for(int i=0;i<(objective/10)+1;i++) {
            this.backgrounds.add(new Room(backgrounds.get(i), 'r', 800, 500, "sprites\\desert.png"));
        }
        this.terrains.add(new Terrain(500,350,100,20,false,"sprites\\platform.png"));
        this.hero=new Hero(100,350);
        StaticThing energyBar = new StaticThing(278, 275, 44, 9, "sprites\\energy bar.png");
        this.energy=new StaticThing(278,275,44,9,"sprites\\energy bar.png");
        this.energy.setFrame(1);
        for(int i=0;i<this.hero.getHealth();i++) {
            this.liveCounter.add(new StaticThing(10*(i%5+1), 10*((i/5)+1),9,9, "sprites\\heart.png"));
        }

        //putting images on window
        pane.getChildren().clear();
        for(Room room:this.backgrounds){
            pane.getChildren().add(room.getImage());
        }
        for(Terrain terrain:this.terrains){
            pane.getChildren().add(terrain.getImage());
        }
        pane.getChildren().add(energyBar.getImage());
        pane.getChildren().add(this.energy.getImage());
        pane.getChildren().add(this.objectiveTracker);
        this.generateNewFoes((int)(Math.random()*3)+1);
        pane.getChildren().add(this.hero.getImage());
        for(StaticThing life : this.liveCounter){
            pane.getChildren().add(life.getImage());
        }
    }

    private void endGame(long time){
        this.gameEndedAt=time;
        this.gameIsRunning=false;
        this.gameIsResettable=false;
        this.liveCounter.clear();
        this.backgrounds.clear();
        this.foes.clear();
        this.allyProjectiles.clear();
        this.enemyProjectiles.clear();
    }

    private void update(long time) {
        this.hero.update(time,this.cam,this.terrains,this.foes,this.enemyProjectiles);

        //generating new foes
        if(this.cam.update(this.hero)){
            this.generateNewFoes((int)(Math.random()*3)+1);
        }

        //Background update
        for(Room room:this.backgrounds){
            room.update(this.cam);
        }

        //Terrain update
        for(Terrain terrain:this.terrains){
            terrain.update(this.cam);
        }

        //updating projectiles and deleting when out of bounds
        for(Projectile projectile : allyProjectiles){
            if(projectile.update(time,this.cam)){
                pane.getChildren().remove(projectile.getImage());
                allyProjectiles.remove(projectile);
                break;
            }
        }
        for(Projectile projectile : enemyProjectiles){
            if(projectile.update(time,this.cam)){
                pane.getChildren().remove(projectile.getImage());
                enemyProjectiles.remove(projectile);
                break;
            }
        }

        //deleting out of bound foes
        for(Foe foe : foes){
            if(foe.getX()<-76){
                pane.getChildren().remove(foe.getImage());
                foes.remove(foe);
                break;
            }
        }

        for(Foe foe : foes){
            //randomly making foes attack
            if(Math.random()<.01){
                Projectile newProjectile = foe.attack(time);
                if(newProjectile!=null) {
                    pane.getChildren().add(newProjectile.getImage());
                    enemyProjectiles.add(newProjectile);
                }
            }
            //deleting foes and projectiles that are in contact
            Projectile projectile=foe.update(time,this.cam,this.allyProjectiles);
            if(projectile!=null&&projectile.isDeletable()){
                pane.getChildren().remove(foe.getImage());
                this.foes.remove(foe);
                pane.getChildren().remove(projectile.getImage());
                this.allyProjectiles.remove(projectile);
                this.hero.heal(1);
                break;
            }else if(projectile!=null){
                pane.getChildren().add(projectile.getImage());
                enemyProjectiles.add(projectile);
            }
        }
        for(int i=0;i<this.liveCounter.size();i++){
            if(this.hero.getHealth()>i) {
                this.liveCounter.get(i).setFrame(0);
            }else{
                this.liveCounter.get(i).setFrame(1);
            }
        }

        this.objectiveTracker.setText("Parcourez "+this.objective+"m\n"+this.hero.getDistance()+"/"+this.objective);

        if(this.hero.getHealth()<0){
            Text text=new Text(200,100,"Défaite");
            text.setFont(Font.font("Verdana",40));
            text.setFill(Color.RED);
            pane.getChildren().add(text);
            this.endGame(time);
        }

        if(this.hero.getDistance()>=this.objective){
            Text text=new Text(200,100,"Victoire");
            text.setFont(Font.font("Verdana",40));
            text.setFill(Color.GREEN);
            pane.getChildren().add(text);
            this.objective=this.objective*5;
            this.endGame(time);
        }
        this.energy.setWidth(this.hero.getEnergy()+2);
        this.primaryStage.show();
    }

    private void generateNewFoes(int amount) {
        int[] pos={(int)(Math.random()*(950-150*amount)),(int)(Math.random()*(950-150*amount)),(int)(Math.random()*(950-150*amount))};
        Arrays.sort(pos);
        for(int newFoe=0;newFoe<amount;newFoe++){
            foes.add(new Foe(this.hero.getX()+800+pos[newFoe]+150*newFoe));
            pane.getChildren().add(foes.get(foes.size()-1).getImage());
        }
    }
}