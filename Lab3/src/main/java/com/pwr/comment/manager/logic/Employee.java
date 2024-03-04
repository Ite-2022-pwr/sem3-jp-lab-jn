package com.pwr.comment.manager.logic;

import com.pwr.comment.manager.abstractModels.EmployeeModel;

public class Employee extends EmployeeModel {


    public Employee(int id, String name) {
        super(id, name);
    }

    @Override
    public void setId(int newId) {

        if(newId < 0)
            throw new IllegalArgumentException("<employee_id> must be positive integer");

        super.setId(newId);
    }



    public String serialize() {
        return "<id> : " +this.getId() + " | <name> : " + this.getName();
    }


}
