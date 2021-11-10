public class Camera {
    private int x;
    private int y;
    private double vX=0;
    private double vY=0;

    public Camera(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX(){
        return(x);
    }

    public int getY(){
        return(y);
    }

    @Override
    public String toString(){
        return(this.x+", "+this.y);
    }

    public boolean update(Hero hero) {
        //horizontal tracking
        double a=15*(hero.getX()-this.x-100)-12*this.vX;
        this.vX=this.vX+a*0.01;
        this.x=(int)(this.x+vX*0.1);

        //vertical tracking
        a=20*(hero.getY()-this.y-150)-20*this.vY;
        this.vY=this.vY+a*0.01;
        this.y=(int)(this.y+vY*0.1);

        //looping
        if(this.x>=800){
            this.x=this.x-800;
            return true;
        }
        return false;
    }
}
