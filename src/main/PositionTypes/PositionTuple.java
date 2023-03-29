package main.PositionTypes;

public class PositionTuple {
    private final Position positionA;
    private final Position positionB;

    public PositionTuple(Position pA, Position pB) {
        this.positionA = pA;
        this.positionB = pB;
    }

    public Position getPositionA() {
        return positionA;
    }

    public Position getPositionB() {
        return positionB;
    }
}
