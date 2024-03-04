package com.pwr.comment.manager.abstractModels;

abstract public class EmployeeModel {

    private int id;
    private String name;

    public EmployeeModel(int id, String name){
        this.setId(id);
        this.setName(name);
    }

    public void setId(int newId){
        this.id = newId;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

}
