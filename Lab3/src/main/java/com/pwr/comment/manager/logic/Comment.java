package com.pwr.comment.manager.logic;

import com.pwr.comment.manager.abstractModels.CommentModel;

import java.time.LocalDate;

public class Comment extends CommentModel {
    public Comment(int id, int employeeId, LocalDate date, CommentModel.commentType commentType, int wage, String note) {
        super(id, employeeId, date, commentType, wage, note);
    }

    @Override
    public String serialize() {
        return "<id> : " + this.getId() + "| <employee_id> : " + this.getEmployeeId() + " | <comment_date> : " + this.getDate() + " | <comment_type> : " + this.getCommentType() + " | <wage> : " + this.getWage() + " | <note> : " + this.getNote();
    }

    public String deserialize(){
        return this.getId()+";"+this.getEmployeeId()+";"+this.getDate()+";"+this.getCommentType()+";"+this.getWage()+";"+this.getNote();
    }



    @Override
    public void setEmployeeId(int employeeId) {
        if(employeeId < 0)
            throw new IllegalArgumentException("<employee_id> must be positive integer");

        super.setEmployeeId(employeeId);
    }

    @Override
    public void setWage(int wage) {
        if(!(wage > 0 && wage < 11))
            throw new IllegalArgumentException("<wage> must be integer in range of <1;10>");

        super.setWage(wage);
    }
}
