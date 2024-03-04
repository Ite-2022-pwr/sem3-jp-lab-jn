package com.pwr.comment.manager.abstractModels;

import com.pwr.comment.manager.logic.Trend;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public abstract class TrendModel {

    public enum AnalyzeDuration {
        WEEK, MONTH, QUARTER
    }


    // mapa <data, wage>
    protected abstract void makeTrend(Map<LocalDate, Integer> data, AnalyzeDuration duration, LocalDate startDate);

    protected abstract void makeTrend(int employeeId, Map<String, Integer> data, Trend.AnalyzeDuration duration);

    protected abstract float calculateAverageWage(Set<Map.Entry<LocalDate, Integer>> data);

    protected abstract float calculateStandardDevation(Set<Map.Entry<LocalDate, Integer>> data, float average);


}
