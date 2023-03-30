package main;

import main.PositionTypes.Antenna;
import main.PositionTypes.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class AntennaSetter {

    private Grid grid;
    private ArrayList<Antenna> allAntennas = new ArrayList<>();
    private ArrayList<Antenna> placedAntennas = new ArrayList<>();

    public AntennaSetter(Grid grid){
        this.grid = grid;

        for (Point point : grid.getField()){
            allAntennas.add(new Antenna(point, grid));
        }

        allAntennas.sort(Comparator.comparingInt(a -> a.getReachables().size()));

        // print the sorted list

    }

    public ArrayList<Antenna> findSolution(){
        for(int i = 0; i < allAntennas.size(); i++){
            i = i * i;
        }
        return null;
    }

//    public Antenna findBestMatch(ArrayList<Antenna> missingPoints){
//        Antenna nextBestAntenna = null;
//        int nextBestAntennaHitCount= 0;
//        for (Antenna antenna : allAntennas){
//            int hitCount = missingPoints.size() - getUnreachedPoints().size();
//            if (hitCount > nextBestAntennaHitCount){
//                nextBestAntenna = antenna;
//                nextBestAntennaHitCount = hitCount;
//            }
//        }
//
//        return nextBestAntenna;
//    }

    public ArrayList<Antenna> getAllAntennas() {
        for (Antenna antenna : allAntennas) {
            System.out.println(antenna);
            System.out.println("------------------\n");
        }
        return null;
    }

    private static ArrayList<Antenna> concatenateDistinct(ArrayList<Antenna> list1, ArrayList<Antenna> list2) {
        ArrayList<Antenna> concatenatedList = new ArrayList<>(list1);
        concatenatedList.addAll(list2);
        HashSet<Antenna> uniqueElements = new HashSet<>(concatenatedList);
        concatenatedList.clear();
        concatenatedList.addAll(uniqueElements);
        return concatenatedList;
    }

    public ArrayList<Point> getUnreachedPoints(ArrayList<Antenna> list){
        ArrayList<Point> unreachedPoints = new ArrayList<>();
        for (Point point : grid.getField()) {
            if (!list.contains(point)) {
                unreachedPoints.add(point);
            }
        }
        return unreachedPoints;
    }

    public void createOutput(){
        System.out.println(this);
        fileOutput();
    }



    public void fileOutput(){
        String[] filesToAdd = new String[placedAntennas.size() + 1];

        createGridFile(grid, "grid");

        for(int i = 0; i < placedAntennas.size(); i++){
            filesToAdd[1 + i] = "Antenna" + i;
            createAntennaFile(placedAntennas.get(i), "Antenna" + i);
        }

        createDefinitionFile(filesToAdd, "fileName");
    }

    private void  createDefinitionFile(String[] filesToAdd, String fileName){
        try {
            FileWriter fileWriter = new FileWriter(fileName + ".dem");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("set title \"" + fileName + "\"");
            bufferedWriter.newLine();

            bufferedWriter.write("set hidden3d");
            bufferedWriter.newLine();

            bufferedWriter.write("set key outside top");
            bufferedWriter.newLine();

            bufferedWriter.write("splot '" + filesToAdd[0] + "' w line , \\");
            bufferedWriter.newLine();

            for (int i = 1; i < filesToAdd.length - 1; i++){
                bufferedWriter.write("'" + filesToAdd[i] + "'" + "\\");
                bufferedWriter.newLine();
            }

            bufferedWriter.write("'" + filesToAdd[filesToAdd.length-1] + "'" + " w linespace");
            bufferedWriter.newLine();

            bufferedWriter.write("pause -1 \"Hit return to continue\"");
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createGridFile(Grid grid, String fileName){
        try {
            FileWriter fileWriter = new FileWriter( fileName + ".dat");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);


            for(int i = 0; i < grid.getWidth(); i++){
                ArrayList<Point> row = grid.getPointsInRow(i);
                for(Point point : row){
                    bufferedWriter.write(point.getX() + " " + point.getY() + " " + point.getSize());
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAntennaFile(Antenna antenna, String fileName){
        try {
            FileWriter fileWriter = new FileWriter(fileName + ".dat");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(antenna.getX() + " " + antenna.getY() + " " + antenna.getSize());
            bufferedWriter.newLine();

            bufferedWriter.write(antenna.getX() + " " + antenna.getY() + " " + antenna.getAntennaTop());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(";***************************").append("\n");
        sb.append("; ").append(grid.getName()).append("\n");
        sb.append(";***************************").append("\n");
        sb.append("; ").append("Groesse des Gebietes").append("\n");
        sb.append(grid.getWidth()).append(" ").append(grid.getHeight()).append("\n");
        sb.append(";").append(" Hoehen").append("\n");
        sb.append(grid);

        return sb.toString();
    }
}
