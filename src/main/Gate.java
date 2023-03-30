package main;

import main.PositionTypes.Point;
import main.PositionTypes.GatePoint;

public class Gate {

    private final int gateLocation; //common coordinate of the two points
    private final boolean isVertical;
    private final GatePoint firstPost;
    private final GatePoint secondPost;

    public Gate(Point pA, Point pB) {
        isVertical = pA.getX() == pB.getX();

        int positionA;
        int positionB;

        if (isVertical) {
            this.gateLocation = pA.getX();

            positionA = pA.getY();
            positionB = pB.getY();
        } else {
            this.gateLocation = pA.getY();

            positionA = pA.getX();
            positionB = pB.getX();
        }

        GatePoint postA = new GatePoint(positionA, pA.getSize());
        GatePoint postB = new GatePoint(positionB, pB.getSize());

        if (postA.getPosition() < postB.getPosition()) {
            firstPost = postA;
            secondPost = postB;
        } else {
            firstPost = postB;
            secondPost = postA;
        }
    }

    public boolean intersectsWith(Point from, Point to) {
        if (isVertical) {
            return intersectsVertical(from, to);
        } else {
            return intersectsHorizontal(from, to);
        }
    }

    public boolean intersectsHorizontal(Point from, Point to) {

        double lamda = ((double) gateLocation - from.getY()) / (to.getY() - from.getY());

        double intersectionPosition = from.getX() + lamda * (to.getX() - from.getX());
        double intersectionValue = from.getSize() + lamda * (to.getSize() - from.getSize());

        double linePosition = intersectionPosition - firstPost.getPosition(); // im intervall[0-1]
        double gateValue = getGateValue(linePosition);

        return gateValue >= intersectionValue;
    }

    public boolean intersectsVertical(Point from, Point to) {
        double lamda = ((double) gateLocation - from.getX()) / (to.getX() - from.getX());

        double intersectionPosition = from.getY() + lamda * (to.getY() - from.getY());
        double intersectionValue = from.getSize() + lamda * (to.getSize() - from.getSize());

        double linePosition = intersectionPosition - firstPost.getPosition(); // im intervall[0-1]
        double gateValue = getGateValue(linePosition);

        return gateValue >= intersectionValue;
    }

    private double getGateValue(double linePosition) {
        int postSizeDiff = secondPost.getValue() - firstPost.getValue();
        return firstPost.getValue() + linePosition * postSizeDiff;
    }
}
