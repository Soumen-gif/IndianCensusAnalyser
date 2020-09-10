package com.bridgelabz.indiancensusanalyser.controller.model;

import com.opencsv.bean.CsvBindByName;
public class StateCodeCSV {
    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @CsvBindByName(column = "SrNo", required = true)
    public String SrNo;

    @CsvBindByName(column = "TIN", required = true)
    public String TIN;

    @Override
    public String toString() {
        return "CSVStatesCode{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", SrNo='" + SrNo + '\'' +
                ", TIN='" + TIN + '\'' +
                '}';
    }
}

