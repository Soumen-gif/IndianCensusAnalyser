package com.bridgelabz.indiancensusanalyser.controller.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "state" ,required = true)
    public String state;

    @CsvBindByName(column = "State Id" ,required = true)
    public String StateId;

    @CsvBindByName(column = "Population" ,required = true)
    public int Population;

    @CsvBindByName(column = "Total area" ,required = true)
    public double Totalarea;

    @CsvBindByName(column = "Population Density" ,required = true)
    public double PopulationDensity;


}
