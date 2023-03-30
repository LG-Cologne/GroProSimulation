package main;

import main.PositionTypes.Coord;
import main.PositionTypes.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {



    private String name;
    private int height;
    private int width;
    private final ArrayList<Point> field = new ArrayList<>();

    public Grid(String fileName){
        this.name = fileName;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/inputFiles/" + fileName))) {
            String line;
            boolean dimensionIsSet = false;
            int yPos = 0;
            while ((line = br.readLine()) != null) {
                    if (line.matches("^\\d+(\\s\\d+)*$")) {     // Check if line contains only numbers
                        String[] tokens = line.split("\\s");    // Split line into tokens using whitespace as a separator

                        int[] lineNumbers = new int[tokens.length];   // Transform Token to Int List
                        for (int i = 0; i < tokens.length; i++) {
                            lineNumbers[i] = Integer.parseInt(tokens[i]);
                        }


                        if (dimensionIsSet){
                            yPos--;
                            int lastSize = 0;
                            for (int xPos = 0; xPos < this.width; xPos++){
                                if(xPos < lineNumbers.length){
                                    lastSize = lineNumbers[xPos];
                                }
                                // System.out.println("add Point (" + xPos + "|"+ yPos + ") size: " + lastSize);
                                field.add(new Coord(xPos, yPos, lastSize));
                            }

                        }else {
                            this.width = lineNumbers[0];
                            this.height = lineNumbers[1];

                            yPos = this.height;
                            dimensionIsSet = true;
                        }
                }
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    /*
    public String toString(){
        StringBuilder output = new StringBuilder("");
        for( int y = this.height; y >= 0; y--){
            for(int x = 0; x < this.width; x++){
                for(Point point : field){
                    if(x == point.getX() && y == point.getY()){
                        output.append(" ").append(point.getSize());
                    }
                }
            }
            output.append("\n");
        }
        return output.toString();
    }
*/

    public String toString(){
        int[][] output_field = new int[height][width];

        for(Point point : this.field){
            output_field[(this.height - 1) - point.getY()][point.getX()] = point.getSize();
        }

        return Arrays.deepToString(output_field).replace("], ", "\n").replaceAll("[\\[,\\]]", "");
    }

    public ArrayList<Point> getPointsInRow(int row){
        ArrayList<Point> pointsInRow = new ArrayList<>();
        for (Point point : this.field){
            if (point.getX() == row){
                pointsInRow.add(point);
            }
        }

        return pointsInRow;
    }

    public ArrayList<Point> getField() {
        return field;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    public String getName() {
        return name;
    }
}
