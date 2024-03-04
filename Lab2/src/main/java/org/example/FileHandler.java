package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    private String absPath;



    public FileHandler(String absPath){
        this.absPath = absPath;
        this.validateFile();

    }

    /**
     * validate if file is in good format
     * exits program if any problem appears
     * TODO: regex
     */
    private void validateFile() {

        File inputFile = new File(this.absPath);
        try{
            Scanner fileScanner = new Scanner(inputFile);
            String readLine;
            List<String> parsedLine = new ArrayList<>();

        } catch(FileNotFoundException e){
            System.out.println("Cannot open input file at path " + this.absPath);
        }
    }

    public ArrayList<ProjectEntity> getProjects() throws FileNotFoundException {

        ArrayList<ProjectEntity> outputProjectEntities = new ArrayList<>();
        String readLine;
        File inputFile = new File(this.absPath);
        Scanner fileScanner = new Scanner(inputFile);

        while(fileScanner.hasNextLine()){
            readLine = fileScanner.nextLine().toUpperCase();
            if(readLine.startsWith("P") && readLine.charAt(1) != 'R'){
                ProjectEntity Pe = new ProjectEntity(
                        readLine.substring(0, readLine.indexOf(":")), // project name
                        readLine.split(": ")[1].split(" ")
                );
                outputProjectEntities.add(Pe);
            }
        }

        return outputProjectEntities;
    }

    public ArrayList<EmployeeEntity> getEmployees() throws FileNotFoundException {
        ArrayList<EmployeeEntity> outputEmployeeEntities = new ArrayList<>();
        String readLine;
        File inputFile = new File(this.absPath);
        Scanner fileScanner = new Scanner(inputFile);

        while(fileScanner.hasNextLine()){
            readLine = fileScanner.nextLine().toUpperCase();
            if(readLine.startsWith("R") && readLine.charAt(1) != 'R'){

                EmployeeEntity Pe = new EmployeeEntity(
                        readLine.split(": ")[0],
                        readLine.split(": ")[1].split(" "));

                outputEmployeeEntities.add(Pe);
            }
        }

        return outputEmployeeEntities;
    }

    /**
     * ATTENTION: use validateFile() before this method
     * converts file line in format specified in this exercise to ArrayList of employees<br/>
     * Examples:<br/><br/>
     * "P1: JAVA PYTHON" returns ("JAVA", "PYTHON")<br/>
     * "P2: ANGULAR QA JAVA" returns ("ANGULAR QA", "JAVA")<br/>
     * "P3: JAVA JAVA QA PM" retuturns ("JAVA", "JAVA QA PM")
     * @param line file line all in upperCase
     * @return list of employees
     */
//    private ArrayList<String> parseLineEmployees(String line){
//
//        String[] wordList = line.split(" ");
//        ArrayList<String> employees = new ArrayList<>();
//
//        for (int i = 1; i<wordList.length; i++){
//
//            if(SPECIAL_WORDS.contains(wordList[i])){
//                int j = i-1;
//                while(SPECIAL_WORDS.contains(wordList[j])){j--;}
//                employees.add(wordList[j] + " " + wordList[i]);
//            }
//
//            // OUT of Bonds
//            else if( (i+1 < wordList.length) &&
//                     SPECIAL_WORDS.contains(wordList[i+1])
//            ){
//                employees.add(wordList[i] + " " + wordList[i+1]);
//                i++;
//            }
//            else{
//                employees.add(wordList[i]);
//            }
//        }
//
//        return employees;
//    }


}
