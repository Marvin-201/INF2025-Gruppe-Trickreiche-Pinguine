package analysis;

// Im Endeffekt einfach ein Punkt, der double-Koordinaten hat
public class ScatterPoint {
    private final double x;
    private final double y;

    public ScatterPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){return x;}
    public double getY(){return y;}
}
