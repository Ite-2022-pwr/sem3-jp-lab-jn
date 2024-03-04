package com.pwr.comment.manager.main;

import com.pwr.comment.manager.abstractModels.TrendModel;
import com.pwr.comment.manager.logic.Adapter;
import com.pwr.comment.manager.logic.Parser;
import com.pwr.comment.manager.logic.Trend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class UI {

    Adapter adapter;
    public static enum FrameName {
        MAIN_MENU,
        MODIFY_EMPLOYEE, VIEW_EMPLOYEES, ADD_EMPLOYEE, DELETE_EMPLOYEE,
        MODIFY_COMMENT, VIEW_COMMENTS, ADD_COMMENT, DELETE_COMMENT,
        TRENDS
    }

    public UI(Adapter adapter){
        this.adapter = adapter;
    }


    public FrameName renderFrameByName(FrameName frameName, Scanner scan) throws IOException {

        switch (frameName){
            case MAIN_MENU -> {return this.mainMenuUI(scan);}
            case MODIFY_EMPLOYEE -> {return this.modifyEmployeeUI(scan);}
            case DELETE_EMPLOYEE -> {return this.deleteEmployeeUI(scan);}
            case ADD_EMPLOYEE -> {return this.addEmployeeUI(scan);}
            case VIEW_EMPLOYEES -> {return this.viewEmployeesUI();}
            case MODIFY_COMMENT -> {return this.modifyCommentUI(scan);}
            case DELETE_COMMENT -> {return this.deleteCommentUI(scan);}
            case ADD_COMMENT -> {return this.addCommentUI(scan);}
            case VIEW_COMMENTS -> {return this.viewCommentsUI();}
            case TRENDS -> {return this.trendsUI(scan);}
        }

        return FrameName.MAIN_MENU;
    }




    private FrameName mainMenuUI(Scanner scan){
        this.clearConsole();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("MAIN MENU");
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("1. Modify employee data");
        System.out.println("2. Modify comment data");
        System.out.println("3. Analise trends");
        System.out.println();
        System.out.println("Type 'exit' to end the program");

        String chosenOption = scan.nextLine();
        int parsedOption = 0;

        try{
            parsedOption = this.validateOption(chosenOption, new int[]{1, 2, 3});
            switch (parsedOption) {
                case 1 -> {return FrameName.MODIFY_EMPLOYEE;}
                case 2 -> {return FrameName.MODIFY_COMMENT;}
                case 3 -> {return FrameName.TRENDS;}
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        return FrameName.MAIN_MENU;

    }



    private FrameName modifyEmployeeUI(Scanner scan){
        this.clearConsole();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("MODIFY EMPLOYEE DATA");
        System.out.println();
        System.out.println("1. Add employee");
        System.out.println("2. View employees");
        System.out.println("3. Delete employee");
        System.out.println("4. Move to main menu");
        System.out.println();
        System.out.println("Type 'exit' to end the program");

        String chosenOption = scan.nextLine();
        int parsedOption = 0;

        try{
            parsedOption = this.validateOption(chosenOption, new int[]{1, 2, 3, 4});
            switch (parsedOption) {
                case 1 -> {return FrameName.ADD_EMPLOYEE;}
                case 2 -> {return FrameName.VIEW_EMPLOYEES;}
                case 3 -> {return FrameName.DELETE_EMPLOYEE;}
                case 4 -> {return FrameName.MAIN_MENU;}
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        return  FrameName.MODIFY_EMPLOYEE;
    }

    private FrameName viewEmployeesUI() throws FileNotFoundException {
        this.adapter.viewEmployees();
        return FrameName.MODIFY_EMPLOYEE;
    }

    private FrameName deleteEmployeeUI(Scanner scan) throws IOException {
        this.clearConsole();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("DELETE EMPLOYEE DATA");
        System.out.println();

        this.adapter.viewEmployees();


        System.out.println();
        System.out.println("Enter employee <id> to delete it");
        System.out.println("All comments referencing deleted employee will be deleted");
        System.out.println("Type 'menu' to go back");

        String chosenOption = scan.nextLine();
        int parsedOption = 0;

        if(chosenOption.equals("menu") || chosenOption.equals("'menu'")){
            return FrameName.MODIFY_COMMENT;
        }

        try{
            parsedOption = Integer.parseInt(chosenOption);
        }
        catch(NumberFormatException e){
            System.out.println("Podano niepoprawną komendę");
            return FrameName.DELETE_EMPLOYEE;
        }
        adapter.deleteEmployee(parsedOption);

        return  FrameName.DELETE_EMPLOYEE;
    }

    private FrameName addEmployeeUI(Scanner scan) throws IOException {
        this.clearConsole();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("ADD EMPLOYEE DATA");
        System.out.println();

        System.out.println("Provide name for new Employee: ");
        String name = scan.nextLine();

        if(name.equals("")){
            return FrameName.ADD_EMPLOYEE;
        }

        adapter.addEmployee(name);

        return FrameName.MODIFY_COMMENT;
    }








    private FrameName modifyCommentUI(Scanner scan){
        this.clearConsole();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("MODIFY COMMENT DATA");
        System.out.println();
        System.out.println("1. Add comment");
        System.out.println("2. View comments");
        System.out.println("3. Delete comment");
        System.out.println("4. Move to main menu");
        System.out.println();
        System.out.println("Type 'exit' to end the program");

        String chosenOption = scan.nextLine();
        int parsedOption = 0;

        try{
            parsedOption = this.validateOption(chosenOption, new int[]{1, 2, 3,4});
            switch (parsedOption) {
                case 1 -> {return FrameName.ADD_COMMENT;}
                case 2 -> {return FrameName.VIEW_COMMENTS;}
                case 3 -> {return FrameName.DELETE_COMMENT;}
                case 4 -> {return FrameName.MAIN_MENU;}
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        return FrameName.DELETE_EMPLOYEE;
    }

    private FrameName deleteCommentUI(Scanner scan) throws IOException {
        this.clearConsole();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("DELETE COMMENT DATA");
        System.out.println();

        this.adapter.viewComments();


        System.out.println();
        System.out.println("Enter comment <id> to delete it");
        System.out.println("Type 'menu' to go back");

        String chosenOption = scan.nextLine();
        int parsedOption = 0;

        if(chosenOption.equals("menu") || chosenOption.equals("'menu'")){
            return FrameName.MODIFY_COMMENT;
        }

        try{
            parsedOption = Integer.parseInt(chosenOption);
        }
        catch(NumberFormatException e){
            System.out.println("Podano niepoprawną komendę");
            return FrameName.DELETE_COMMENT;
        }
        adapter.deleteComment(parsedOption);

        return  FrameName.DELETE_COMMENT;
    }



    private FrameName viewCommentsUI() throws FileNotFoundException {
        this.adapter.viewComments();
        return FrameName.MODIFY_COMMENT;
    }

    private FrameName addCommentUI(Scanner scan) throws IOException {
        this.clearConsole();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("ADD COMMENT DATA");
        System.out.println();
        adapter.viewEmployees();
        System.out.println();

        System.out.println("Provide employee_id for new comment: ");
        String employee_id = scan.nextLine();

        System.out.println("Provide date (format of yyyy-mm-dd): ");
        String date = scan.nextLine();

        System.out.println("Enter comment type (positive or negative): ");
        String commentType = scan.nextLine();

        System.out.println("Tell us wage of this comment (on a scale <1:10>): ");
        String wage = scan.nextLine();

        System.out.println("If you want, add a note to this comment or leave it blank: ");
        String note = scan.nextLine();

        try{
            adapter.addComment(employee_id+";"+date+";"+commentType+";"+wage+";"+note);
        }
        catch (InvalidParameterException e){
            System.out.println("Cannot insert given comment, error: " + e.getMessage());
            System.out.println("If you want to go to main menu enter 'back', if not press any key");
            String decision = scan.nextLine();

            if(decision.equals("back") || decision.equals("'back'")){
                return FrameName.MODIFY_COMMENT;
            }
            else{
                return  FrameName.ADD_COMMENT;
            }
        }

        return FrameName.MODIFY_COMMENT;
    }


    private FrameName trendsUI(Scanner scan){
        this.clearConsole();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("ANALYZE TRENDS");
        System.out.println();

        System.out.println("Enter what period of time you would like to analyze (week, month, quarter) : ");
        String duration = scan.nextLine().toUpperCase();

        System.out.println("Enter date from where you would like to analyze (format yyyy-mm-dd) : ");
        String startDate = scan.nextLine();

        try{
            adapter.analyzeTrend(duration, startDate);
        }
        catch (InvalidParameterException e){
            System.out.println("Cannot analyze, error: " + e.getMessage());
            System.out.println("If you want to go to main menu enter 'back', if not press any key");
            String decision = scan.nextLine();

            if(decision.equals("back") || decision.equals("'back'")){
                return FrameName.MAIN_MENU;
            }
            else{
                return  FrameName.TRENDS;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        return FrameName.MAIN_MENU;
    }





    public void clearConsole()
    {
//        try {
//            final String os = System.getProperty("os.name");
//
//            if (os.contains("Windows")) {
//                Runtime.getRuntime().exec("cls");
//            }
//            else{
//                Runtime.getRuntime().exec("clear");
//            }
//        }
//        catch (final Exception e) {
//            for (int i = 0; i < 50; ++i) System.out.println();
//        }

        for (int i = 0; i < 50; ++i) System.out.println();
    }

    private int validateOption(String chosenOption, int[] options) throws IllegalArgumentException{

        if(chosenOption.equals("exit") || chosenOption.equals("'exit'")){
            System.exit(0);
        }
        else{
            try{
                int parsedOption = Integer.parseInt(chosenOption);
                boolean isValidOption = false;

                for (int option : options) {
                    if (option == parsedOption) {
                        isValidOption = true;
                        break;
                    }
                }

                if(!isValidOption){
                    throw new IllegalArgumentException("Chosen option is illegal");
                }

                return parsedOption;

            }
            catch (NumberFormatException e){
                throw new IllegalArgumentException("Chosen option is not a integer or string 'exit'");
            }
        }

        return -1;
    }

}
