import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Button extends Parent{

    private static final Image NORMAL_IMAGE = new Image("sprites\\button.png");
    private static final Image PRESSED_IMAGE = new Image("sprites\\buttonPressed.png");
    private final ImageView iv;
    private final ArrayList<ButtonPressed> listeners=new ArrayList<>();

    public Button(int x, int y,String name) {
        this.iv = new ImageView(NORMAL_IMAGE);
        this.iv.setX(x);
        this.iv.setY(y);

        Rectangle area=new Rectangle(x,y,300,100);
        area.setFill(Color.TRANSPARENT);

        Text text=new Text(x,y+60,name);
        text.setFont(Font.font("Verdana",40));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(300);

        this.getChildren().add(this.iv);
        this.getChildren().add(text);
        this.getChildren().add(area);


        area.setOnMousePressed(evt -> {
            iv.setImage(PRESSED_IMAGE);
            text.setX(x+2);
            text.setY(y+62);
        });
        area.setOnMouseReleased(evt -> {
            iv.setImage(NORMAL_IMAGE);
            text.setX(x);
            text.setY(y+60);});
        area.setOnMouseClicked(evt -> {
            for(ButtonPressed listener:listeners){
                listener.onButtonPressed();
            }
        });
    }

    public void addButtonListener(ButtonPressed listener){
        this.listeners.add(listener);
    }

}