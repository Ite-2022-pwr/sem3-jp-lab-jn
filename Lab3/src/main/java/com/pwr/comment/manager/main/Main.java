package com.pwr.comment.manager.main;

import com.pwr.comment.manager.logic.Adapter;
import com.pwr.comment.manager.logic.FileHandler;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner scan = new Scanner(System.in);

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("WELCOME TO MANAGER EMPLOYEE COMMENTER");

        System.out.println("please provide absolute path to the employee data file (leave blank for the default path): ");
        String employeePath = scan.nextLine();

        System.out.println("please provide absolute path to the comment data file (leave blank for the default path): ");
        String commentPath = scan.nextLine();

        FileHandler Fh = new FileHandler(
                employeePath,
                commentPath
        );

        Adapter adapter = new Adapter(Fh);
        UI UIHandler = new UI(adapter);

        UI.FrameName frame = UI.FrameName.MAIN_MENU;

        while(true){
            frame = UIHandler.renderFrameByName(frame, scan);
        }
    }

}
