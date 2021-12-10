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

    private static final Image NORMAL_IMAGE = new Image("sprites\\Button.png");
    private static final Image PRESSED_IMAGE = new Image("sprites\\ButtonPressed.png");
    private final ImageView bg;
    private final ImageView t;
    private final ImageView l;
    private final ImageView r;
    private final ImageView b;
    private final ImageView tl;
    private final ImageView tr;
    private final ImageView bl;
    private final ImageView br;
    private final Text text;
    private final ArrayList<ButtonPressed> listeners=new ArrayList<>();

    public Button(int x, int y,int width,int height,String name) {
        this.bg = new ImageView(NORMAL_IMAGE);
        this.bg.setViewport(new Rectangle2D(11,11,1,1));
        this.bg.setX(x);
        this.bg.setY(y);
        this.bg.setFitHeight(height);
        this.bg.setFitWidth(width);

        this.t = new ImageView(NORMAL_IMAGE);
        this.t.setViewport(new Rectangle2D(11,0,1,10));
        this.t.setX(x);
        this.t.setY(y);
        this.t.setFitWidth(width);

        this.l = new ImageView(NORMAL_IMAGE);
        this.l.setViewport(new Rectangle2D(0,11,10,1));
        this.l.setX(x);
        this.l.setY(y);
        this.l.setFitHeight(height);

        this.r = new ImageView(NORMAL_IMAGE);
        this.r.setViewport(new Rectangle2D(13,11,10,1));
        this.r.setX(x+width-10);
        this.r.setY(y);
        this.r.setFitHeight(height);

        this.b = new ImageView(NORMAL_IMAGE);
        this.b.setViewport(new Rectangle2D(11,13,1,10));
        this.b.setX(x);
        this.b.setY(y+height-10);
        this.b.setFitWidth(width);

        this.tl = new ImageView(NORMAL_IMAGE);
        this.tl.setViewport(new Rectangle2D(0,0,10,10));
        this.tl.setX(x);
        this.tl.setY(y);

        this.tr = new ImageView(NORMAL_IMAGE);
        this.tr.setViewport(new Rectangle2D(13,0,10,10));
        this.tr.setX(x+width-10);
        this.tr.setY(y);

        this.bl = new ImageView(NORMAL_IMAGE);
        this.bl.setViewport(new Rectangle2D(0,13,10,10));
        this.bl.setX(x);
        this.bl.setY(y+height-10);

        this.br = new ImageView(NORMAL_IMAGE);
        this.br.setViewport(new Rectangle2D(13,13,10,10));
        this.br.setX(x+width-10);
        this.br.setY(y+height-10);

        this.text = new Text(x, y + height/2. + ((50<height)?height/2.-20 : 5), name);
        text.setFont(Font.font("Verdana",Math.max(10,height-40)));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(width);

        Rectangle area=new Rectangle(x,y,width,height);
        area.setFill(Color.TRANSPARENT);

        this.getChildren().add(this.bg);
        this.getChildren().add(this.t);
        this.getChildren().add(this.l);
        this.getChildren().add(this.r);
        this.getChildren().add(this.b);
        this.getChildren().add(this.tl);
        this.getChildren().add(this.tr);
        this.getChildren().add(this.bl);
        this.getChildren().add(this.br);
        this.getChildren().add(text);
        this.getChildren().add(area);


        area.setOnMousePressed(evt -> {
            bg.setImage(PRESSED_IMAGE);
            t.setImage(PRESSED_IMAGE);
            l.setImage(PRESSED_IMAGE);
            r.setImage(PRESSED_IMAGE);
            b.setImage(PRESSED_IMAGE);
            tl.setImage(PRESSED_IMAGE);
            tr.setImage(PRESSED_IMAGE);
            bl.setImage(PRESSED_IMAGE);
            br.setImage(PRESSED_IMAGE);
            text.setX(x+2);
            text.setY(y+height/2.+((50<height)?height/2.-18:7));
        });
        area.setOnMouseReleased(evt -> {
            bg.setImage(NORMAL_IMAGE);
            t.setImage(NORMAL_IMAGE);
            l.setImage(NORMAL_IMAGE);
            r.setImage(NORMAL_IMAGE);
            b.setImage(NORMAL_IMAGE);
            tl.setImage(NORMAL_IMAGE);
            tr.setImage(NORMAL_IMAGE);
            bl.setImage(NORMAL_IMAGE);
            br.setImage(NORMAL_IMAGE);
            text.setX(x);
            text.setY(y+height/2.+((50<height)?height/2.-20:5));});
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