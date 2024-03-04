package org.example;

import org.example.models.Board;
import org.example.models.Config;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("GAME OF LIFE");
        System.out.println();
        System.out.println("please provide absolute path to config file or leave blank for the default config: ");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();

        FileHandler Fh = new FileHandler(path);
        Config config = Fh.readConfigSettings();

        Board board = new Board(config);
        board.simulate();

    }
}