package game.entities.assembly.movesets;

public class MoveSet {
    private final long[][] durations;
    private final double[][] angles;
    private final int[] maxPosition;

    MoveSet(long[][] durations, double[][] angles){
        this.durations=durations;
        this.angles=angles;
        this.maxPosition=new int[angles.length];
        for(int i=0;i<this.maxPosition.length;i++){
            this.maxPosition[i]=angles[i].length;
        }
    }

    public long[][] getDurations(){return durations;}
    public double[][] getAngles(){return angles;}
    public int[] getMaxPos(){return maxPosition;}
}
