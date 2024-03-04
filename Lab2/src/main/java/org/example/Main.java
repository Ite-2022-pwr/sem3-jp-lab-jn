package org.example;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        if(args.length >= 3){
            System.out.println("Too many arguments provided");
            System.exit(-1);
        }

        if(args[0].equals("--help")){
            System.out.println("""
                    usage: <name_of_jar>.jar [-P path] [--help]
                            [path]: absolute path to the input file with softwarehouse projects
                                    file must be in format:
                                    <P1>: <technology1> <technology2> <technology3> <technologyN>
                                    <PN>: <technology1> <technology2> <technology3> <technologyN>
                                    <R1>: <technology1> <technology2> <technologyN>
                                    <RN>: <technology1> <technology2> <technologyN>
                                    
                                    where:
                                    <PN> - indicates that this line is project with ordinal number of N
                                    <RN> - indicates that this line is stuff member with ordinal number of N
                                    <technologyN> - name of technology etc.
                                    
                    """);
            System.exit(0);
        }

        if(args[0].startsWith("-P")){
            String absPathInput = args[1];

            FileHandler Fh = new FileHandler(args[1]);

            ArrayList<ProjectEntity> projects = Fh.getProjects();
            ArrayList<EmployeeEntity> employees = Fh.getEmployees();

            SimulatedAnnealing Sa = new SimulatedAnnealing(projects, employees);
            Sa.simulate();



        }
        else{
            System.out.println("please use this .jar with --help for insturctions");
        }
    }
}