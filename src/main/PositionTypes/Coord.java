package main.PositionTypes;

public class Coord extends Point {
    public Coord(int x, int y, int size) {
        super(x, y, size);
    }

    public Coord(Point point) {
        super(point);
    }
}
