package org.example.models;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Config {
    private int xSize;
    private int ySize;
    private int iterationCount;
    private int initialLiveCellCount;
    private ArrayList<Coords> initialLiveCellCoords;



    public Config(){
        this.setInitialLiveCellCoords(new ArrayList<>());
    }
    public Config(int xSize, int ySize, int iterationCount, int initialLiveCellCount){
        this.setxSize(xSize);
        this.setySize(ySize);
        this.setIterationCount(iterationCount);
        this.setInitialLiveCellCoords(initialLiveCellCoords);
        this.setInitialLiveCellCoords(new ArrayList<>());
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) throws InvalidParameterException {
        if(xSize <= 0)
            throw new InvalidParameterException("xSize must be positive integer");

        this.xSize = xSize;
    }

    public void setxSize(String xSize) throws InvalidParameterException {
        try{
            int xSizeParsed = Integer.parseInt(xSize);
            if(xSizeParsed <= 0)
                throw new InvalidParameterException("xSize must be positive integer");
            this.xSize = xSizeParsed;
        } catch(NumberFormatException e){
            throw new InvalidParameterException("xSize is not a integer");
        }
    }

    public int getySize() {
        return ySize;
    }


    public void setySize(String ySize) throws InvalidParameterException {
        try{
            int ySizeParsed = Integer.parseInt(ySize);
            if(ySizeParsed <= 0)
                throw new InvalidParameterException("ySize must be positive integer");
            this.ySize = ySizeParsed;
        } catch(NumberFormatException e){
            throw new InvalidParameterException("ySize is not a integer");
        }
    }

    public void setySize(int ySize) {
        if(ySize <= 0)
            throw new InvalidParameterException("ySize must be positive integer");

        this.ySize = ySize;
    }

    public int getIterationCount() {
        return iterationCount;
    }


    public void setIterationCount(String iterationCount) throws InvalidParameterException {
        try{
            int iterationCountParsed = Integer.parseInt(iterationCount);
            if(iterationCountParsed <= 0)
                throw new InvalidParameterException("iterationCount must be positive integer");
            this.iterationCount = iterationCountParsed;
        } catch(NumberFormatException e){
            throw new InvalidParameterException("iterationCount is not a integer");
        }
    }

    public void setIterationCount(int iterationCount) {
        this.iterationCount = iterationCount;
    }

    public int getInitialLiveCellCount() {
        return initialLiveCellCount;
    }


    public void setInitialLiveCellCount(String cellCount) throws InvalidParameterException {
        try{
            int cellCountParsed = Integer.parseInt(cellCount);
            if(cellCountParsed <= 0)
                throw new InvalidParameterException("cellCount must be positive integer");
            this.initialLiveCellCount = cellCountParsed;
        } catch(NumberFormatException e){
            throw new InvalidParameterException("cellCount is not a integer");
        }
    }

    public void setInitialLiveCellCount(int initialLiveCellCount) {
        if(initialLiveCellCount <= 0)
            throw new InvalidParameterException("cellCount must be positive integer");

        this.initialLiveCellCount = initialLiveCellCount;
    }

    public ArrayList<Coords> getInitialLiveCellCoords() {
        return initialLiveCellCoords;
    }

    public void setInitialLiveCellCoords(ArrayList<Coords> initialLiveCellCoords) {
        this.initialLiveCellCoords = initialLiveCellCoords;
    }

    // format of line: "<number> <number>"
    public void addInitialCellCoords(String line){
        String[] coords = line.strip().split(" ");

        if(coords.length != 2)
            throw new InvalidParameterException("line " + line + " is not in format of '<number> <number>'");

        try{
            int xCoord = Integer.parseInt(coords[0]);
            int yCoord = Integer.parseInt(coords[1]);

            if(xCoord < 0 || yCoord < 0){
                throw new InvalidParameterException("coords in line " + line + " are not positive integerers");
            }

            if(initialLiveCellCoords.size() > initialLiveCellCount ){
                throw new InvalidParameterException("too many initial coords passed, expected to get " + initialLiveCellCount);
            }

            this.initialLiveCellCoords.add(new Coords(xCoord, yCoord));

        } catch (NumberFormatException e){
            throw new InvalidParameterException(" one of the coords in line " + line + " is not an integer");
        }
    }

    public void validateInitialCoordsLength(){
        if(initialLiveCellCoords.size() != initialLiveCellCount){
            throw new InvalidParameterException("bad number of initial coords passed, expected to get " + initialLiveCellCount + " got " + initialLiveCellCoords.size());
        }
    }
}
