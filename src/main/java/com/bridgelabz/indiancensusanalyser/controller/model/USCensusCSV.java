package com.bridgelabz.indiancensusanalyser.controller.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "State" ,required = true)
    public String State;

    @CsvBindByName(column = "State Id" ,required = true)
    public String StateId;

    @CsvBindByName(column = "Population" ,required = true)
    public int Population;

    @CsvBindByName(column = "Total area" ,required = true)
    public double totalarea;

    @CsvBindByName(column = "Population Density" ,required = true)
    public double PopulationDensity;

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "State='" + State + '\'' +
                ", StateId='" + StateId + '\'' +
                ", Population=" + Population +
                ", Totalarea=" + totalarea +
                ", PopulationDensity=" + PopulationDensity +
                '}';
    }
}
