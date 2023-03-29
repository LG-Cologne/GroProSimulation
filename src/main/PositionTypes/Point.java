package main.PositionTypes;

public abstract class Point extends Position{
    private final int size;

    public int getIncreaseTo(Point point){
        return point.getSize() - this.getSize();
    }

    //constructor
    public Point(int x, int y, int size) {
        super(x,y);
        this.size = size;
    }

    public Point(Point point) {
        super(point.getX(), point.getY());
        this.size = point.size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "(" + this.getX() + "," + this.getY() + "|" + this.size + ")";
    }
}
