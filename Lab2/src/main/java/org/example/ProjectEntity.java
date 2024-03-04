package org.example;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class ProjectEntity {

    private String name;

    private int initialVacanty;

    private ArrayList<Workplace> stuffNeeded = new ArrayList<>();

    public ProjectEntity(String name, String[] requiredTechnologyList){
        this.name = name;
        this.initialVacanty = requiredTechnologyList.length;

        for (String technology: requiredTechnologyList) {
                int i = this.getIndexOfTechnology(technology);

                if(i == -1)
                    stuffNeeded.add(new Workplace(technology, 1));
                else
                    stuffNeeded.get(i).addVacanty();
        }
    }

    public boolean recruitEmployee(String technology, String employeeName){
        int i = this.getIndexOfTechnology(technology);
        if(i != -1){
            return this.stuffNeeded.get(i).addEmployee(employeeName);
        }
        return false;
    }

    public int getIndexOfTechnology(String technology){
        for(int i = 0; i < this.stuffNeeded.size(); i++){
            if(this.stuffNeeded.get(i).technology.equals(technology))
                return i;
        }
        return -1;
    }

    public int getCurrentVacantyNumber(){
        int output = 0;

        for(Workplace w : stuffNeeded){
            output += w.getEmptyVacanties();
        }

        return output;
    }

    public ArrayList<Workplace> getStuffNeeded(){
        return this.stuffNeeded;
    }


    /**
     * 0.4 means 40% is not filled
     * @return
     */
    public Float getVacantyPercentage(){
        int currentVacanty = 0;

        for(Workplace w : this.stuffNeeded){
            currentVacanty += w.getEmptyVacanties();
        }

        return  ((float)(this.initialVacanty - currentVacanty) / this.initialVacanty);
    }

    public boolean isNeededTechnology(String technology){
        int i = this.getIndexOfTechnology(technology);
        return i != -1 && this.stuffNeeded.get(i).getEmptyVacanties() > 0;
    }


    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("Project %s".formatted(name));

        for(Workplace wk : this.stuffNeeded){
            output.append("\n").append(wk.toString());
        }
        return output.toString();
    }

}
