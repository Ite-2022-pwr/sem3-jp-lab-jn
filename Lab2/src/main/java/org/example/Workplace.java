package org.example;

import java.util.ArrayList;

public class Workplace {

    public String technology;

    public int initialVacantyNumber;
    public int vacantyNumber;
    public String[] employeesNamesAssigned;

    public Workplace(String technology, int vacantyNumber){
        this.technology = technology;
        this.vacantyNumber = vacantyNumber;
        this.initialVacantyNumber = vacantyNumber;
        this.employeesNamesAssigned = new String[this.vacantyNumber];
        for(int i = 0; i < this.vacantyNumber; i++){
            this.employeesNamesAssigned[i] = "empty";
        }
    }

    public void addVacanty(){
        this.vacantyNumber++;
        this.initialVacantyNumber++;
        String[] copy = this.employeesNamesAssigned.clone();
        this.employeesNamesAssigned = new String[this.vacantyNumber];
        System.arraycopy(copy, 0, this.employeesNamesAssigned, 0, copy.length);
        this.employeesNamesAssigned[this.vacantyNumber - 1] = "empty";
    }

    public boolean addEmployee(String employeeName){
        for (int i = 0; i < this.employeesNamesAssigned.length; i++){
            if(this.employeesNamesAssigned[i].equals("empty")){
                this.vacantyNumber = this.vacantyNumber - 1;
                this.employeesNamesAssigned[i] = employeeName;
                return true;
            }
        }

        return false;
    }

    public int getEmptyVacanties(){
        int output = 0;
        for(int i = 0; i < this.employeesNamesAssigned.length; i++){
            if(this.employeesNamesAssigned[i].equals("empty"))
                output++;
        }

        return output;
    }

    /**
     *
     * @return name of freed employee
     */
    public String freeVacancy(){

        if(this.vacantyNumber >= this.initialVacantyNumber){
            return "";
        }
        this.vacantyNumber++;
        String freedEmployeeName;
        for (int i = 0; i < this.employeesNamesAssigned.length; i++){
            if(!this.employeesNamesAssigned[i].equals("empty")){
                freedEmployeeName =  this.employeesNamesAssigned[i];
                this.employeesNamesAssigned[i] = "empty";
                return freedEmployeeName;
            }
        }
        return "";
    }

    @Override
    public String toString(){
        return "Tech: %s | Free seats: %d".formatted(technology, vacantyNumber);
    }
}
