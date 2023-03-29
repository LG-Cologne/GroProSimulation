package main.PositionTypes;

public class GatePoint {

    private final int position;
    private final int value;

    public GatePoint(int position, int value) {
        this.position = position;
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }
}
