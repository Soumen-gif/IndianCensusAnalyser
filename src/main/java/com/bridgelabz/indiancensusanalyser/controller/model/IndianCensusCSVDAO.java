package com.bridgelabz.indiancensusanalyser.controller.model;

public class IndianCensusCSVDAO {
    public String state;
    public int densityPerSqKm;
    public int areaInSqKm;
    public int population;
    public String stateName;
    public String stateCode;
    public String SrNo;
    public String TIN;

    public IndianCensusCSVDAO(IndianCensusCSV indianCensusCSV) {
        densityPerSqKm = indianCensusCSV.densityPerSqKm;
        areaInSqKm = indianCensusCSV.areaInSqKm;
        population = indianCensusCSV.population;
        state = indianCensusCSV.state;
    }

    public IndianCensusCSVDAO(StateCodeCSV stateCodeDAO) {
        stateName = stateCodeDAO.stateName;
        stateCode = stateCodeDAO.stateCode;
        SrNo =stateCodeDAO.SrNo;
        TIN = stateCodeDAO.TIN;
    }

}
