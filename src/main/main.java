package main;

import main.PositionTypes.Antenna;

public class main{
    public static void main(String[]args){

       Grid grid = new Grid("test_andre.txt");

        System.out.println(grid);
        System.out.println("--------------------------");

        AntennaSetter modell = new AntennaSetter(grid);
        Antenna test = new Antenna(grid.getField().get(0), grid );
        modell.createOutput();

    }
}