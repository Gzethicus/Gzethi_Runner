package menu;

import javafx.geometry.Rectangle2D;
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
    private final ImageView tr;
    private final ImageView l;
    private final ImageView b;
    private Text text;
    private final ArrayList<ButtonPressed> listeners=new ArrayList<>();

    public Button(int x, int y,int width,int height,String name) {
        this.tr = new ImageView(NORMAL_IMAGE);
        this.tr.setViewport(new Rectangle2D(510-width,0,width-10,height));
        this.tr.setX(x+10);
        this.tr.setY(y);
        this.l = new ImageView(NORMAL_IMAGE);
        this.l.setViewport(new Rectangle2D(0,0,10,height-10));
        this.l.setX(x);
        this.l.setY(y);
        this.b = new ImageView(NORMAL_IMAGE);
        this.b.setViewport(new Rectangle2D(0,190,width-5,10));
        this.b.setX(x);
        this.b.setY(y+height-10);

        Rectangle area=new Rectangle(x,y,width,height);
        area.setFill(Color.TRANSPARENT);

        this.text = new Text(x, y + height/2 + ((50<height)?height/2-20 : 5), name);
        text.setFont(Font.font("Verdana",Math.max(10,height-40)));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(width);

        this.getChildren().add(this.tr);
        this.getChildren().add(this.l);
        this.getChildren().add(this.b);
        this.getChildren().add(text);
        this.getChildren().add(area);


        area.setOnMousePressed(evt -> {
            tr.setImage(PRESSED_IMAGE);
            l.setImage(PRESSED_IMAGE);
            b.setImage(PRESSED_IMAGE);
            text.setX(x+2);
            text.setY(y+height/2+((50<height)?height/2-18:7));
        });
        area.setOnMouseReleased(evt -> {
            tr.setImage(NORMAL_IMAGE);
            l.setImage(NORMAL_IMAGE);
            b.setImage(NORMAL_IMAGE);
            text.setX(x);
            text.setY(y+height/2+((50<height)?height/2-20:5));});
        area.setOnMouseClicked(evt -> {
            for(ButtonPressed listener:listeners){
                listener.onButtonPressed();
            }
        });
    }

    public void setText(String text){
        this.text.setText(text);
    }

    public void addButtonListener(ButtonPressed listener){
        this.listeners.add(listener);
    }

}