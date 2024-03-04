package com.pwr.comment.manager.logic;

import com.pwr.comment.manager.abstractModels.CommentModel;
import com.pwr.comment.manager.abstractModels.TrendModel;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class Parser {



    static Comment parseComment(String line) throws InvalidParameterException{

        String[] params = line.split(";");
        if(params.length > 6 || params.length < 5){
            throw new InvalidParameterException("Number of parameters in line " + line + " dont match with parameters in Comment");
        }
        int id = -1;
        try{
            id = Integer.parseInt(params[0]);
        }
        catch(NumberFormatException e){
            throw new InvalidParameterException("first parameter in line " + line + " is not a number");
        }

        if(id < 0){
            throw new InvalidParameterException("first parameter in line " + line + " must be positive");
        }

        int id_employee = -1;
        try{
            id_employee = Integer.parseInt(params[1]);
        }
        catch(NumberFormatException e){
            throw new InvalidParameterException("second parameter in line " + line + " is not a number");
        }

        if(id_employee < 0){
            throw new InvalidParameterException("second parameter in line " + line + " must be positive");
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try{
            LocalDate.parse(params[2], dateFormatter);
        }
        catch (DateTimeParseException e){
            throw new InvalidParameterException("third parameter in line " + line + " must be date in format of yyyy-mm-dd");
        }

        boolean isEnum = false;
        for(Comment.commentType cT : Comment.commentType.values()){
            if(params[3].equals(cT.name())){
                isEnum = true;
                break;
            }
        }

        if(!isEnum){
            StringBuilder commentTypes = new StringBuilder();
            for(Comment.commentType cT : Comment.commentType.values()){
                commentTypes.append(cT).append(",");
            }

            throw new InvalidParameterException("fourth parameter in line " + line + " must be in value of either " + commentTypes);
        }

        int wage = -1;
        try{
            wage = Integer.parseInt(params[4]);
        }
        catch(NumberFormatException e){
            throw new InvalidParameterException("fifth parameter in line " + line + " is not a number");
        }

        if(wage <  1 || wage > 10){
            throw new InvalidParameterException("fifth parameter in line " + line + " must be in range of <1;10>");
        }

        return new Comment(id, id_employee, LocalDate.parse(params[2], dateFormatter), Comment.commentType.valueOf(params[3]), wage, params[5]);
    }

    static Employee parseEmployee(String line) throws InvalidParameterException{
        String[] params = line.split(";");

        if(params.length != 2){
            throw new InvalidParameterException("Number of parameters in line " + line + " dont match with parameters in Employee");
        }
        int id = -1;
        try{
            id = Integer.parseInt(params[0]);
        }
        catch(NumberFormatException e){
            throw new InvalidParameterException("first parameter in line " + line + " is not a number");
        }

        if(id < 0){
            throw new InvalidParameterException("first parameter in line " + line + " must be positive");
        }

        return new Employee(id, params[1]);
    }


    static TrendModel.AnalyzeDuration isValidDuration(String value) throws InvalidParameterException{


        StringBuilder errorMessage = new StringBuilder();

        for(TrendModel.AnalyzeDuration duration : TrendModel.AnalyzeDuration.values()){
            errorMessage.append(duration.name()).append(",");
            if(value.equals(duration.name()))
                return TrendModel.AnalyzeDuration.valueOf(value);

        }
        throw new InvalidParameterException("not legal duration. It should be one of these values : " + errorMessage);
    }

    static LocalDate isValidLocalDate(String value) throws InvalidParameterException{
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try{
            return LocalDate.parse(value, dateFormatter);
        }
        catch (DateTimeParseException e){
            throw new InvalidParameterException("cannot parse " + value + " to valid date (yyyy-mm-dd)");
        }
    }




}
