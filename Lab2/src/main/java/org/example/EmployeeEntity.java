package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeEntity {

    private Map<String, Integer> technologies;

    private String name;

    public static enum SpecialTechnologies {
            QA, PM
    }

    public EmployeeEntity(String name, String[] givenTechnologies){
        this.name = name;
        this.technologies = new HashMap<>();
        for(String technology : givenTechnologies){

            if(this.isSpecialTechnology(technology))
                this.technologies.put(technology, 2);
            else
                this.technologies.put(technology, 1);

        }
    }

    public Map<String, Integer> getTechnologies(){
        return this.technologies;
    }

    public boolean hire(String technology){
        if(!this.technologies.containsKey(technology))
            return false;

        if(this.technologies.get(technology) <= 0)
            return false;

        this.technologies.put(technology, this.technologies.get(technology) - 1);
        return true;
    }

    public static boolean isSpecialTechnology(String name){

        for (SpecialTechnologies t: SpecialTechnologies.values()) {
            if(name.equals(t.name()))
                return true;
        }

        return false;
    }


    public ArrayList<String> getOpenTechnologies(){

        ArrayList<String> output = new ArrayList<>();
        for(String technology : this.technologies.keySet()){
            if(this.technologies.get(technology) > 0){
                output.add(technology);
            }
        }
        return output;
    }


    public void leaveWork(String technology){
        this.technologies.put(technology, this.technologies.get(technology) + 1);
    }

    public String getName(){
        return this.name;
    }



    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("Employee %s".formatted(name));

        for(String tech : technologies.keySet()){
            output.append("\n").append(tech).append(" ").append(technologies.get(tech) > 0 ? "unemployeed" : "employed");
        }

        return output.toString();
    }


}
