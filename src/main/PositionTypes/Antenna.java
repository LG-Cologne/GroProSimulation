package main.PositionTypes;

import main.Gate;
import main.Grid;

import java.util.ArrayList;
import java.util.Arrays;

public class Antenna extends Point {

    private ArrayList<Point> reachables = new ArrayList<>();



    private final double antennaTop;
    private final Grid grid;

    public Antenna(Point location, Grid grid) {
        super(location);
        this.antennaTop = location.getSize() + 0.1;
        this.grid = grid;

        for (Point point : this.grid.getField()) {
            if (isReachable(point)) {
                this.reachables.add(point);
            }
        }
    }

    private boolean isReachable(Point point) {
        if (isDirect(point)) {
            return checkDirect(point);
        } else {
            return checkWarped(point);
        }
    }

    private boolean isDirect(Point p) {
        return isHorizontalTo(p) || isVerticalTo(p) || isDiagonalTo(p);
    }

    /***
     * With getPositionsBetween i recieve all Points between the location and the given point.
     * Now i determine a line between the locations and the points sizes (lineValue)  and compare those to the actuall size of the points (pointValue).
     * If all lineValues are greater than the corresponding pointValues the given point is reachable.
     *
     * @param p the point to check for reachability
     * @return the point is reachable
     */
    private boolean checkDirect(Point p) {
        ArrayList<Point> pointsBetween = new ArrayList<>();
        for (Position position : this.getPositionsBetween(p)) {
            pointsBetween.add(getPointFromPosition(position));
        }

        double difference = p.getSize() - this.antennaTop;
        double step = (difference) / pointsBetween.size();

        for (int i = 1; i < pointsBetween.size(); i++) {
            double pointValue = pointsBetween.get(i - 1).getSize();
            double lineValue = this.antennaTop + (i * step);

            if (pointValue >= lineValue) {
                return false;
            }
        }
        return true;
    }

    private Point getPointFromPosition(Position pos) {
        for (Point point : this.grid.getField()) {
            if (point.getX() == pos.getX() && point.getY() == pos.getY()) {
                return point;
            }
        }
        return null;
    }

    /***
     * With getGetGatePostPositionsBetween i recieve all Gates between the location and the given point.
     * The class Gate has a Method to determine if a line between two points intersects with the gate.
     * with this information i can check if the line between the location and the given Point is reachable.
     *
     * @param p the point to check for reachability
     * @return true if the line passes each gate
     *         false if the line intersects with at least one gate
     */
    private boolean checkWarped(Point p) {
        ArrayList<Gate> gatesBetween = new ArrayList<>();
        for (PositionTuple gatePostPositions : this.getGatePostPositionsBetween(p)) {
            gatesBetween.add(getGateFromGatePositions(gatePostPositions));
        }

        for (Gate gate : gatesBetween) {
            if (gate.intersectsWith(this, p)) {
                return false;
            }
        }
        return true;
    }

    private Gate getGateFromGatePositions(PositionTuple gatePostPositions) {
        Point pA = getPointFromPosition(gatePostPositions.getPositionA());
        Point pB = getPointFromPosition(gatePostPositions.getPositionB());
        return new Gate(pA, pB);
    }


    @Override
    public String toString() {
        String[][] output_field = new String[grid.getHeight()][grid.getWidth()];
        for (String[] ints : output_field) {
            Arrays.fill(ints, "X");
        }
        for (Point point : this.reachables) {
            output_field[(grid.getHeight() - 1) - point.getY()][point.getX()] = "0";
        }
        output_field[(grid.getHeight() - 1) - this.getY()][this.getX()] = "A";

        String field = Arrays.deepToString(output_field).replace("], ", "\n").replaceAll("[\\[,\\]]", "");
        String pos = "Antenna Position: (" + this.getX() + "," + this.getY() + "|" + this.antennaTop + ")";
        String count = "reachable Points: (" + this.reachables.size() + "/" + this.grid.getField().size() +  ")";

        return pos + "\n" + field + "\n" + count;
    }

    public ArrayList<Point> getReachables() {
        return reachables;
    }
    /*
    @Override
    public String toString(){
        String[][] output_field = new String[grid.getHeight()][grid.getWidth()];
        for (String[] ints : output_field) {
            Arrays.fill(ints, "X");
        }

// fill in the reachable points
        for (Point point : this.reachables) {
            output_field[(grid.getHeight() - 1) - point.getY()][point.getX()] = "0";
        }

// mark the current point as "A"
        output_field[(grid.getHeight() - 1) - getY()][getX()] = "A";

// create the header row for the coordinate system
        StringBuilder headerRow = new StringBuilder("  ");
        for (int i = 0; i < grid.getWidth(); i++) {
            headerRow.append(i).append("-");
        }

// create the output string with the coordinate system
        StringBuilder sb = new StringBuilder(headerRow.toString()).append("\n");
        for (int i = 0; i < grid.getHeight(); i++) {
            sb.append(i).append(" ");
            for (int j = 0; j < grid.getWidth(); j++) {
                sb.append(output_field[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();

    }*/

    public double getAntennaTop() {
        return antennaTop;
    }
}

