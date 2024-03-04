package org.example;

import java.util.*;


public class SimulatedAnnealing {

    private Float currentTemperature = 50.0F;

    private Float coolingRate = 0.95F;

    private ArrayList<ProjectEntity> projectEntities;

    private ArrayList<EmployeeEntity> employeeEntities;


    public SimulatedAnnealing(ArrayList<ProjectEntity> projectEntities, ArrayList<EmployeeEntity> employeeEntities){
        this.projectEntities = projectEntities;
        this.employeeEntities = employeeEntities;
    }


    /**
     * main and only public method in this class
     * im większy koszt tym lepiej
     */
    public void simulate(){
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.println("Begin of simulated annealing");
        System.out.println("start temperature: " + this.currentTemperature);
        System.out.println("cooling factor: " + this.coolingRate);
        System.out.println("---------------------------------------------------------------------------------------------------");

        Random rand = new Random();
        ArrayList<ProjectEntity> bestSolution = this.generateRandomSolution();
        ArrayList<ProjectEntity> newSolution;
        Float bestCost = this.calculateCost(bestSolution);
        Float newCost;

        while(currentTemperature > 1.0F){

            newSolution = this.makeProposedState(bestSolution);
            newCost = this.calculateCost(newSolution);

            if(rand.nextFloat() > this.calculateSigma(bestCost, newCost)){
                bestSolution = newSolution;
                bestCost = newCost;
            }

            this.printIterationMessage(bestCost);
            this.currentTemperature *= this.coolingRate;

            for (ProjectEntity project : bestSolution){
                System.out.println(project.toString() + "\n");
            }

            System.out.println("\n\n");
            for (EmployeeEntity employee : employeeEntities){
                System.out.println(employee.toString() + "\n");
            }

        }

        System.out.println("END");
    }

    private Float calculateSigma(Float previousCost, Float currentCost){
        Float delta = Math.abs(currentCost - previousCost);

        return (float) (-1/(1+Math.pow(2.718, delta/this.currentTemperature)));
    }

    private void printIterationMessage(Float cost){
        System.out.println("Temperature current: " + this.currentTemperature + " | calculated cost of solution: " + cost);
    }

    private ArrayList<ProjectEntity> makeProposedState(ArrayList<ProjectEntity> previousSolution){

        // tutaj logika zwalniania pracowników z projektów
        Random rand = new Random();
        int numberOfChangedProjects = rand.nextInt(previousSolution.size() / 2);

        for(int i = 0; i < numberOfChangedProjects; i++){
            int randIndex = rand.nextInt(previousSolution.size());
            for(Workplace workplace : previousSolution.get(randIndex).getStuffNeeded()){
                double percentage = rand.nextDouble();
                if(percentage > 0.4){
                    String employeeName = workplace.freeVacancy();

                    // could free vacancy, cant go up more than initial seats
                    if(employeeName.equals(""))
                        continue;

                    for(EmployeeEntity employee : employeeEntities){
                        if(employee.getName().equals(employeeName)){
                            employee.leaveWork(workplace.technology);
                            break;
                        }
                    }
                }
            }
        }

        Collections.shuffle(this.employeeEntities);

        for(EmployeeEntity employee : this.employeeEntities){
            ArrayList<String> technologies = employee.getOpenTechnologies();
            Collections.shuffle(technologies);

            for(String technology : technologies){
                for(ProjectEntity project : previousSolution){
                    if(project.isNeededTechnology(technology)){
                        boolean isHired = employee.hire(technology);
                        if(isHired){
                            project.recruitEmployee(technology, employee.getName());
                        }
                    }
                }
            }
        }

        return previousSolution;
    }

    private ArrayList<ProjectEntity> generateRandomSolution(){

        ArrayList<ProjectEntity> solution = new ArrayList<>(this.projectEntities);
        Collections.shuffle(solution);
        // shuffle employees
        for (EmployeeEntity employee : this.employeeEntities){
            // shuffle technologies
            for (String technology : employee.getTechnologies().keySet()){

                for (ProjectEntity project : solution) {
                    if(project.isNeededTechnology(technology)){
                        project.recruitEmployee(technology, employee.getName());
                        break;
                    }
                }


                employee.hire(technology);

                if(employee.isSpecialTechnology(technology)){
                    for (ProjectEntity project : solution) {
                        if(project.isNeededTechnology(technology)){
                            project.recruitEmployee(technology, employee.getName());
                            break;
                        }
                    }
                    employee.hire(technology);
                }
            }
        }

        return solution;

    }

    public Float calculateCost(ArrayList<ProjectEntity> solution){

        Float currentCost = 0.0F;
        int i;
        float help;
        for (i = 0; i < solution.size(); i++){

            help = solution.get(i).getVacantyPercentage();
            currentCost += help;
            if(help == 1.0F){
                currentCost+=1;
            }
        }

        return currentCost;
    }


}
