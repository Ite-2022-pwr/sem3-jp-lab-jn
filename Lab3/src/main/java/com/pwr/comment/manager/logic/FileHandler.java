package com.pwr.comment.manager.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class FileHandler {

    private String employeeAbsPath;
    private String commentAbsPath;

    public FileHandler(String employeeAbsPath, String commentAbsPath) throws IOException {

        if(employeeAbsPath.equals("") || commentAbsPath.equals("")){
            this.setEmployeeAbsPath(System.getProperty("user.dir") + File.separator + "employees.txt");
            this.setCommentAbsPath(System.getProperty("user.dir") + File.separator + "comments.txt");
        }
        else{
            this.setEmployeeAbsPath(employeeAbsPath);
            this.setCommentAbsPath(commentAbsPath);
        }
    }

    public FileHandler() throws IOException {
        this.setEmployeeAbsPath(System.getProperty("user.dir") +  File.separator + "employees.txt");
        this.setCommentAbsPath(System.getProperty("user.dir") + File.separator + "comments.txt");
    }

    public final void createFile(String path) throws IOException {

            File myObj = new File(path);
            if (myObj.createNewFile())
                System.out.println("File created: " + path);
    }


    public final void appendFile(ArrayList<String> data, String path) throws IOException {
        File inputFile = new File(path);
        FileWriter fileWriter = new FileWriter(inputFile, true);

        for(String record : data){
            fileWriter.write(record + System.getProperty("line.separator"));
        }

        fileWriter.close();
    }

    public final void appendFile(String line, String path) throws IOException {
        File inputFile = new File(path);
        FileWriter fileWriter = new FileWriter(inputFile, true);
        fileWriter.write(line+ System.getProperty("line.separator"));
        fileWriter.close();
    }

    public final void writeFile(ArrayList<String> data, String path) throws IOException {
        File inputFile = new File(path);
        FileWriter fileWriter = new FileWriter(inputFile+ System.getProperty("line.separator"));

        for(String record : data){
            fileWriter.write(record + System.getProperty("line.separator"));
        }

        fileWriter.close();

    }
    public final ArrayList<String> readFile(String path) throws FileNotFoundException {

        ArrayList<String> lines = new ArrayList<>();
        File inputFile = new File(path);
        Scanner fileScanner = new Scanner(inputFile);

        while(fileScanner.hasNextLine()){
            lines.add(fileScanner.nextLine());
        }
        return lines;
    }

    public final ArrayList<String> readEmployeeFile() throws FileNotFoundException {

        ArrayList<String> lines = new ArrayList<>();
        File inputFile = new File(this.getEmployeeAbsPath());
        Scanner fileScanner = new Scanner(inputFile);

        while(fileScanner.hasNextLine()){
            lines.add(fileScanner.nextLine());
        }
        return lines;
    }

    public final ArrayList<String> readCommentFile() throws FileNotFoundException {

        ArrayList<String> lines = new ArrayList<>();
        File inputFile = new File(this.getCommentAbsPath());
        Scanner fileScanner = new Scanner(inputFile);

        while(fileScanner.hasNextLine()){
            lines.add(fileScanner.nextLine());
        }
        return lines;
    }

    public void setCommentAbsPath(String employeeCommentAbsPath) throws IOException {
        this.createFile(employeeCommentAbsPath);
        this.commentAbsPath = employeeCommentAbsPath;
    }

    public String getEmployeeAbsPath() {
        return employeeAbsPath;
    }

    public void setEmployeeAbsPath(String employeeAbsPath) throws IOException {
        this.createFile(employeeAbsPath);
        this.employeeAbsPath = employeeAbsPath;
    }

    public String getCommentAbsPath() {
        return commentAbsPath;
    }
}
