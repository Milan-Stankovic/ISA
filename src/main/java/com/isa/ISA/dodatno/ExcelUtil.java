package com.isa.ISA.dodatno;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ExcelUtil {

    public static XYChart.Series<String, Number> makeData(Map<Date, Integer> map) {
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        Calendar c = Calendar.getInstance();
        for (Map.Entry<Date, Integer> e : map.entrySet()) {
            c.setTime(e.getKey());
            String k = c.toString();
            //String k = String.valueOf(e.getKey());
            Integer v = e.getValue();

            data.add(new XYChart.Data<String, Number>(k, v));
        }

        series.getData().addAll(data);
        return series;
    }

    public static void makeChart(XYChart.Series<String, Number> series){
      //  stage.setTitle("Bar Chart Sample");
         CategoryAxis xAxis = new CategoryAxis();
         NumberAxis yAxis = new NumberAxis();
         BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Attendance");
        xAxis.setLabel("Date");
        yAxis.setLabel("Number of people");

        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series);

    }
}
