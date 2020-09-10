package com.bridgelabz.indiancensusanalyser.controller.model;

     public class CensusCSVDAO {
    public String state;
    public int densityPerSqKm;
    public int areaInSqKm;
    public int population;
    public String stateName;
    public String stateCode;
    public String SrNo;
    public String TIN;
    public double PopulationDensity;
    public double Totalarea;

    public CensusCSVDAO(IndianCensusCSV indianCensusCSV) {
        densityPerSqKm = indianCensusCSV.densityPerSqKm;
        areaInSqKm = indianCensusCSV.areaInSqKm;
        population = indianCensusCSV.population;
        state = indianCensusCSV.state;
    }

    public CensusCSVDAO(USCensusCSV censusCSV) {
        state= censusCSV.state;
        stateCode=censusCSV.StateId;
        population=censusCSV.Population;
        PopulationDensity=censusCSV.PopulationDensity;
        Totalarea=censusCSV.Totalarea;
    }

    public CensusCSVDAO(StateCodeCSV censusCSV) {
        stateName = censusCSV.stateName;
        stateCode = censusCSV.stateCode;
        SrNo =censusCSV.SrNo;
        TIN = censusCSV.TIN;
    }
}
