package com.bridgelabz.indiancensusanalyser.controller.service;

public class IndianCensusCSVDAO {
    public String state;
    public int densityPerSqKm;
    public int areaInSqKm;
    public int population;
     IndianCensusCSVDAO(IndianCensusCSV indianCensusCSV) {
        densityPerSqKm = indianCensusCSV.densityPerSqKm;
        areaInSqKm = indianCensusCSV.areaInSqKm;
        population = indianCensusCSV.population;
        state = indianCensusCSV.state;
    }

}
