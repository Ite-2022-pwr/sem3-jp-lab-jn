package org.example.models;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class Board {

    private Config config;

    // board[0] is row with index 0
    // board[0..n][0] in loop is column with index 0
    private static AtomicReferenceArray<AtomicReferenceArray<Cell>> cells;
    private CyclicBarrier cyclicBarrier;

    private final ArrayList<TestThread> columnThreads;





    public Board(Config config){
        this.config = config;



        CyclicBarrier b = new CyclicBarrier(3, ()->{
            System.out.println("aktualizacja liścia");
        });

        Thread t1 = new TestThread(b,1,1,2);
        Thread t2 = new TestThread(b,1,1,2);
        Thread t3 = new TestThread(b,1,1,2);


        // będziemy mieli tyle wątków ColumnThread, ile jest kolumn
        this.cyclicBarrier = new CyclicBarrier(config.getxSize(), this::drawBoard);
        this.columnThreads = new ArrayList<>();

        for(int i =0; i < config.getxSize(); i++)
            columnThreads.add(new TestThread(this.cyclicBarrier, config.getIterationCount(), i, i+1));





        cells = new AtomicReferenceArray<>(config.getySize());

        for(int i = 0; i < config.getySize(); i++){
            Cell[] row = new Cell[config.getxSize()];
            cells.getAndSet(i, new AtomicReferenceArray<>(row));
        }



        for(int i = 0; i < cells.length(); i++){
            for(int j = 0; j < cells.get(i).length(); j++){
                cells.get(i).getAndSet(j, new Cell());
            }
        }

        for(Coords c : this.config.getInitialLiveCellCoords()){
            cells.get(c.y).getAndUpdate(c.x, cell -> new Cell(true));
        }


    }

    public void simulate() {

        drawBoard();
        for(TestThread t1 : this.columnThreads){
            t1.start();
        }

    }




    public static Cell[] getColumn(int columnIndex){

        Cell[] output = new Cell[cells.get(0).length()];

        for(int i = 0; i < cells.get(0).length(); i++)
            output[i] = (Cell) cells.get(i).get(columnIndex).clone();

        return output;
    }

    public static synchronized void setColumn(Cell[] column, int columnIndex){

        for(int i = 0; i < column.length; i++){
            cells.get(i).getAndSet(columnIndex, column[i]);
        }

    }


    public synchronized static AtomicReferenceArray<AtomicReferenceArray<Cell>> getCells(){
        return Board.cells;
    }


    public static Cell[][] getAdjacentColumns(int columnIndex){

        int leftColumnIndex = (columnIndex != 0) ? columnIndex - 1 : cells.get(0).length() - 1;
        int rightColumnIndex = (columnIndex != cells.get(0).length() - 1) ? columnIndex + 1 : 0;
        AtomicReferenceArray<AtomicReferenceArray<Cell>> debug = getCells();
        Cell[] leftColumn = getColumn(leftColumnIndex);
        Cell[] rightColumn = getColumn(rightColumnIndex);

        Cell[][] output = new Cell[2][];
        output[0] = leftColumn;
        output[1] = rightColumn;
        return output;
    }




    public static Cell[] getAdjacentCells(int x, int y){

        Cell[] output = new Cell[8];

        Cell[][] adjacentColumns = getAdjacentColumns(x);



            output[0] = adjacentColumns[0][y]; // center left cell
            output[1] = adjacentColumns[1][y]; // center right cell

        try{
            output[2] = (y == 0) ? cells.get(cells.get(0).length() - 1).get(x) : cells.get(y-1).get(x); // top center cell
            output[3] = (y == cells.get(0).length() - 1) ? cells.get(0).get(x) : cells.get(y+1).get(x); // bottom center cell
        } catch(IndexOutOfBoundsException e){
            System.out.println("Center Error for: " + x + " " + y);
            System.exit(-1);
        }
        try{
            output[4] = (y == 0) ? adjacentColumns[0][adjacentColumns[0].length - 1] : adjacentColumns[0][y-1]; // top left cell
            output[5] = (y == adjacentColumns[0].length - 1) ? adjacentColumns[0][0] : adjacentColumns[0][y+1]; // bottom left cell
        } catch(IndexOutOfBoundsException e){
            System.out.println("Left corner Error for: " + x + " " + y);
            System.exit(-1);
        }
        try{
            output[6] = (y == 0) ? adjacentColumns[1][adjacentColumns[1].length - 1] : adjacentColumns[1][y-1]; // top right cell
            output[7] = (y == cells.get(0).length() - 1) ? adjacentColumns[1][0] : adjacentColumns[1][y+1]; // bottom right cell
        } catch(IndexOutOfBoundsException e){
            System.out.println("Right corner Error for: " + x + " " + y);
            System.exit(-1);
        }


        return output;
    }








    public void drawBoard(){
        StringBuilder output = new StringBuilder();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for(int i =0; i < cells.length(); i++){
            for(int j = 0; j < cells.get(i).length(); j++){
                output.append(cells.get(i).get(j).toString());
            }
            output.append("\n");
        }

        System.out.println(output);
    }
}
