package main;

import main.PositionTypes.Antenna;
import main.PositionTypes.Coord;
import main.PositionTypes.Point;

public class main{
    public static void main(String[]args){

       Grid grid = new Grid("test_andre.txt");

        System.out.println(grid);
        System.out.println("--------------------------");

        Point p1 = new Coord(4,6,1);
        Antenna p2 = new Antenna(p1, grid);

        System.out.println(p2);

    }
}