package game.entities.assembly.movesets;

public class MoveSet {
    private final long[][] durations;
    private final long[][] mirroredDurations;
    private final double[][] angles;
    private final double[][] mirroredAngles;
    private final int[] maxPosition;

    public MoveSet(long[][] durations, double[][] angles){
        this.durations=durations;
        this.mirroredDurations=durations;
        this.angles=angles;
        this.mirroredAngles=angles;
        this.maxPosition=new int[angles.length];
        for(int i=0;i<this.maxPosition.length;i++){
            this.maxPosition[i]=angles[i].length;
        }
    }

    public MoveSet(long[][] durations, double[][] angles, long[][] mirroredDurations, double[][] mirroredAngles){
        this.durations=durations;
        this.mirroredDurations=mirroredDurations;
        this.angles=angles;
        this.mirroredAngles=mirroredAngles;
        this.maxPosition=new int[angles.length];
        for(int i=0;i<this.maxPosition.length;i++){
            this.maxPosition[i]=angles[i].length;
        }
    }

    public long[][] getDurations(){return durations;}
    public long[][] getDurations(boolean mirrored){return mirrored?mirroredDurations:durations;}
    public double[][] getAngles(){return angles;}
    public double[][] getAngles(boolean mirrored){return mirrored?mirroredAngles:angles;}
    public int[] getMaxPos(){return maxPosition;}
}
