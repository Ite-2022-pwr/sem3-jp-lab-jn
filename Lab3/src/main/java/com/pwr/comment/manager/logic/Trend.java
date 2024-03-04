package com.pwr.comment.manager.logic;

import com.pwr.comment.manager.abstractModels.TrendModel;


import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Trend extends TrendModel {


    // mapa <data, wage>
    @Override
    protected void makeTrend(Map<LocalDate, Integer> data, AnalyzeDuration duration, LocalDate startDate) {

        int daysToAnalyze = this.calculateDaysAnalyze(duration);

        Set<Map.Entry<LocalDate, Integer>> entries = data.entrySet();
        entries = entries.stream()
                            .filter(d -> d.getKey().isBefore(startDate))
                            .filter(d -> d.getKey().isAfter(startDate.minusDays(daysToAnalyze)))
                            .sorted((d1,d2) -> dateCompare(d2,d1))
                            .collect(Collectors.toCollection(LinkedHashSet::new));

        float average = calculateAverageWage(entries);
        System.out.println("Average wage of comments for this period is " + average);
        System.out.println("Standard devation of wage for comments for this period is " +calculateStandardDevation(entries, average));

    }




    protected void makeTrend(int employeeId, Map<String, Integer> data, Trend.AnalyzeDuration duration) {

    }

    @Override
    protected float calculateAverageWage(Set<Map.Entry<LocalDate, Integer>> data) {
        float averageWage = 0;
        int numberOfEntries = 0;
        for (Map.Entry<LocalDate, Integer> entry : data) {
            numberOfEntries++;
            averageWage+=entry.getValue();
        }


        return averageWage / (float)numberOfEntries;


    }

    @Override
    protected float calculateStandardDevation(Set<Map.Entry<LocalDate, Integer>> data, float average) {
        float standardDevation = 0;

        int numberOfEntries = 0;
        for (Map.Entry<LocalDate, Integer> entry : data) {
            numberOfEntries++;
            standardDevation+=Math.pow(entry.getValue() - average, 2);
        }

        return (float) Math.sqrt(standardDevation / numberOfEntries);
    }



    private int calculateDaysAnalyze(TrendModel.AnalyzeDuration duration){
        switch (duration){
            case WEEK -> {return 7;}
            case MONTH -> {return 31;}
            case QUARTER -> {return 90;}
            default -> {return 0;}
        }
    }

    private int dateCompare(Map.Entry<LocalDate, Integer> d1, Map.Entry<LocalDate, Integer> d2){

        if(d1.getKey().isEqual(d2.getKey()))
            return 0;
        else if(d1.getKey().isBefore(d2.getKey()))
            return -1;
        else
            return 1;

    }


}
