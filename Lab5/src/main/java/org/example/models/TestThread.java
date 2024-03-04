package org.example.models;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Collectors;

public class TestThread extends Thread{


    private CyclicBarrier b;

    private int iterationCount;

    private int columnIndexStart;

    private int columnIndexEnd;

    public TestThread(CyclicBarrier b, int iterationCount, int columnIndexStart, int columnIndexEnd){
        this.b = b;
        this.columnIndexStart = columnIndexStart;
        this.columnIndexEnd = columnIndexEnd;
        this.iterationCount = iterationCount;
        this.setName("Thread-"+columnIndexStart);
    }

    @Override
    public void run(){
        for(int i = 0; i < iterationCount; i++){

            Cell[][] changedColumns = new Cell[columnIndexEnd-columnIndexStart][];

            for(int columnIndex = columnIndexStart; columnIndex < columnIndexEnd; columnIndex++){
                Cell[] column = Board.getColumn(columnIndex);

                // cellCount = liczba komórek wokół badanej komórki
                // cellCount < 2 || cellCount > 3:  śmierć żywej komórki
                // cellCount == 3: narodziny nieżywej komórki
                for(int y = 0; y < column.length; y++){
                    Cell[] adjacentCells = Board.getAdjacentCells(columnIndex, y);
                    int aliveCells = (int) Arrays.stream(adjacentCells).filter(Cell::isAlive).count();

                    if(!column[y].isAlive() && aliveCells == 3)
                        column[y].setAlive(true);

                    else if(column[y].isAlive() && (aliveCells < 2 || aliveCells > 3))
                        column[y].setAlive(false);
                }

                changedColumns[columnIndexEnd - columnIndexStart - 1] = column;
            }



            try {

                b.await();

                for(int c = columnIndexStart; c < columnIndexEnd; c++){
                        Board.setColumn(changedColumns[columnIndexEnd - columnIndexStart - 1],columnIndexStart);
                }

                sleep(1000);

            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }



    }
}
