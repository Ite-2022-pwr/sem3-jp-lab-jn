package org.example.models;

public class Cell {

    private boolean isAlive = false;



    public static char aliveCellIcon = '@';
    public static char deadCellIcon = '-';


    public Cell(){

    }

    public Cell(boolean isAlive){
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public String toString(){
        return (this.isAlive) ? String.valueOf(Cell.aliveCellIcon) : String.valueOf(Cell.deadCellIcon);
    }

    @Override
    public Object clone() {
        Cell c = null;
        try {
            c = (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            c = new Cell(this.isAlive());
        }
        return c;
    }
}
