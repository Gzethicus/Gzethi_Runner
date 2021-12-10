import game.GameScene;
import menu.LoadingScene;
import menu.MenuScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

        @Override
        public void start(Stage primaryStage){
                primaryStage.setTitle("Runner 1.0");
                MenuScene menuScene=new MenuScene();
                GameScene gameScene=new GameScene();
                LoadingScene loadingScene=new LoadingScene();

                menuScene.addStartGameListener((character,cheats) -> {
                        primaryStage.setScene(loadingScene);
                        gameScene.startGame(character,cheats);
                        primaryStage.setScene(gameScene);});
                menuScene.addCloseWindowListener(primaryStage::close);

                gameScene.addOpenMenuListener(()-> primaryStage.setScene(menuScene));

                menuScene.mainMenu();
                primaryStage.setScene(menuScene);
                primaryStage.show();
        }

        public static void main(String[] args) {Application.launch(args);}
}
