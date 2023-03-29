package main.PositionTypes;

import java.util.ArrayList;

public class Position {

    private final int x;
    private final int y;

    public Position[] getPositionsBetween(Position position) {
        int xDiff = position.x - this.x;
        int yDiff = position.y - this.y;

        //scale to 1
        int xStep = getStep(xDiff);
        int yStep = getStep(yDiff);

        int positionAmount = Math.max(Math.abs(xDiff), Math.abs(yDiff));

        Position[] positionList = new Position[positionAmount];

        for (int i = 1; i <= positionAmount; i++) {
            positionList[i - 1] = new Position(this.x + (i * xStep), this.y + (i * yStep));
        }
        return positionList;
    }

    /***
     * The Method detects all gates a straight line betwenn this and pos needs to check.
     * @param pos the position i want to reach
     * @return a list of all GatePosts the line passes
     */
    public PositionTuple[] getGatePostPositionsBetween(Position pos) {
        ArrayList<PositionTuple> gatesBetween = new ArrayList<>();

        int horizontalDifference = pos.getX() - this.getX();
        int vertikalDifference = pos.getY() - this.getY();

        int[] horizontalRange = new int[Math.abs(horizontalDifference) + 1];
        for (int i = 0; i < horizontalRange.length; i++) {
            horizontalRange[i] = this.getX() + i * getStep(horizontalDifference);
        }

        int[] verticalRange = new int[Math.abs(vertikalDifference) + 1];
        for (int i = 0; i < verticalRange.length; i++) {
            verticalRange[i] = this.getY() + i * getStep(vertikalDifference);
        }

        double verticalStep = (double) vertikalDifference / ((double) horizontalRange.length - 1);    //vertical movement while horizontal increasement by 1
        double horizontalStep = (double) horizontalDifference / ((double) verticalRange.length - 1);  //horizontal movement while vertival increasement by 1

        for (int i = 1; i < horizontalRange.length - 1; i++) {
            double currentPosition = verticalRange[0] + verticalStep * i;

            Position first = new Position(horizontalRange[i], (int) Math.floor(currentPosition));
            Position second = new Position(horizontalRange[i], (int) Math.ceil(currentPosition));

            PositionTuple gate = new PositionTuple(first, second);
            gatesBetween.add(gate);
        }

        for (int i = 1; i < verticalRange.length - 1; i++) {
            double currentPosition = horizontalRange[0] + horizontalStep * i;

            Position first = new Position((int) Math.floor(currentPosition), verticalRange[i]);
            Position second = new Position((int) Math.ceil(currentPosition), verticalRange[i]);

            PositionTuple gate = new PositionTuple(first, second);
            gatesBetween.add(gate);
        }

        return  gatesBetween.toArray(new PositionTuple[0]);
    }

    private static ArrayList<PositionTuple> determineGates(int[] horizontalRange, int[] vertikalRange) {
        ArrayList<PositionTuple> gatesBetween = new ArrayList<>();

        for (int i = 1; i < horizontalRange.length - 1; i++) {


            int addition = 0;
            PositionTuple gate = new PositionTuple(new Position(horizontalRange[i], vertikalRange[0 + addition]), new Position(horizontalRange[i], vertikalRange[1 + addition]));
            gatesBetween.add(gate);
        }
        return gatesBetween;
    }

    public boolean isDiagonalTo(Position pos) {
        return Math.abs(this.x - pos.x) == Math.abs(this.y - pos.y); //Koordinatenveränderung bei X und Y ist identisch
    }

    public boolean isHorizontalTo(Position pos) {
        return this.y - pos.y == 0;
    }

    public boolean isVerticalTo(Position pos) {
        return this.x - pos.x == 0;
    }

    public boolean isNeigbor(Position pos) {
        return Math.abs(this.x - pos.x) <= 1 && Math.abs(this.y - pos.y) <= 1; //Koordinatenabstand ist kleiner oder gleich 1
    }

    private int getStep(int difference) {
        return difference == 0 ? 0 : difference / Math.abs(difference);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}