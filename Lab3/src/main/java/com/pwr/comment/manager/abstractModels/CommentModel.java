package com.pwr.comment.manager.abstractModels;

import java.time.LocalDate;

abstract public class CommentModel {

    private int id;
    private int employeeId;
    private LocalDate date;
    private commentType commentType;
    private int wage;
    private String note;

    public enum commentType {
        POSITIVE, NEGATIVE
    }

    public CommentModel(int id, int employeeId, LocalDate date, commentType commentType, int wage){
        this.setId(id);
        this.setEmployeeId(employeeId);
        this.setWage(wage);
        this.setNote("");
        this.setDate(date);
        this.setCommentType(commentType);
    }

    public CommentModel(int id, int employeeId, LocalDate date, commentType commentType, int wage, String note){
        this.setId(id);
        this.setEmployeeId(employeeId);
        this.setWage(wage);
        this.setNote(note);
        this.setDate(date);
        this.setCommentType(commentType);
    }


    public void setId(int id){ this.id = id;}
    public void setEmployeeId(int employeeId){
        this.employeeId = employeeId;
    }

    public void setWage(int wage){
        this.wage = wage;
    }

    public void setCommentType(CommentModel.commentType commentType) {
        this.commentType = commentType;
    }

    public CommentModel.commentType getCommentType() {
        return commentType;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public int getId(){ return this.id; }

    public int getEmployeeId(){
        return this.employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getWage() {
        return wage;
    }

    public String getNote() {
        return note;
    }

    public abstract String serialize();
}
