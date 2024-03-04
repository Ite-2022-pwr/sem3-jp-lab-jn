package com.pwr.comment.manager.logic;

import com.pwr.comment.manager.abstractModels.TrendModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Adapter {

    FileHandler Fh;

    public Adapter(FileHandler Fh){
        this.Fh = Fh;
    }

    public void viewEmployees() throws FileNotFoundException {

        ArrayList<String> lines = Fh.readEmployeeFile();

        for(String line: lines){
            try{
                Employee e = Parser.parseEmployee(line);
                System.out.println(e.serialize());
            }
            catch(InvalidParameterException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void viewComments() throws FileNotFoundException {

        ArrayList<String> lines = Fh.readCommentFile();

        for(String line: lines){
            try{
                Comment e = Parser.parseComment(line);
                System.out.println(e.serialize());
            }
            catch(InvalidParameterException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void deleteComment(int id) throws IOException {

        ArrayList<String> lines = Fh.readCommentFile();
        List<String> modifyLines = lines.stream()
                                .filter(line -> !(line.split(";")[0].equals(String.valueOf(id))))
                                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> toInsert = new ArrayList<>(modifyLines);
        Fh.writeFile(toInsert, Fh.getCommentAbsPath());
    }

    public void deleteEmployee(int id) throws IOException {

        ArrayList<String> lines = Fh.readEmployeeFile();
        List<String> modifyLines = lines.stream()
                .filter(line -> !(line.split(";")[0].equals(String.valueOf(id))))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> toInsert = new ArrayList<>(modifyLines);
        Fh.writeFile(toInsert, Fh.getEmployeeAbsPath());


        ArrayList<String> lines1 = Fh.readCommentFile();
        List<String> modifyLines1 = lines1.stream()
                .filter(line -> (line.split(";")[1].equals(String.valueOf(id))))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> toInsert1 = new ArrayList<>(modifyLines1);
        Fh.writeFile(toInsert1, Fh.getCommentAbsPath());
    }

    public void addEmployee(String name) throws IOException {
        ArrayList<String> lines = Fh.readEmployeeFile();

        int maxId = Parser.parseEmployee(lines.get(lines.size() - 1)).getId() + 1;
        Fh.appendFile(maxId + ";" + name, Fh.getEmployeeAbsPath());

    }

    /**
     *
     * @param line in format of 'employee_id;date;commentType;wage;note'
     */
    public void addComment(String line) throws IOException, InvalidParameterException {
        ArrayList<String> lines = Fh.readCommentFile();

        int maxId = (lines.size() > 0) ? lines.size() : 1;
        line = maxId+";"+line;

        Comment c = Parser.parseComment(line);

        Fh.appendFile(c.deserialize(), Fh.getCommentAbsPath());
    }

    public void analyzeTrend(String duration, String startDate) throws InvalidParameterException, FileNotFoundException {


        TrendModel.AnalyzeDuration durationParsed = Parser.isValidDuration(duration);
        LocalDate startDateParsed = Parser.isValidLocalDate(startDate);


        ArrayList<String> lines = Fh.readCommentFile();
        Map<LocalDate, Integer> datesAndWages = new HashMap<>();

        LocalDate date;
        int wage;
        for(String line : lines){
            String[] params = line.split(";");
            date = LocalDate.parse(params[2]);
            wage = (params[3].equalsIgnoreCase("NEGATIVE")) ? -Integer.parseInt(params[4]) : Integer.parseInt(params[4]);

            if(datesAndWages.containsKey(date))
                datesAndWages.put(date, (datesAndWages.get(date) + wage) / 2);
            else
                datesAndWages.put(date, wage);
        }


        Trend t = new Trend();
        t.makeTrend(datesAndWages, durationParsed, startDateParsed);



    }
}
