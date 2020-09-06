package com.bridgelabz.indiancensusanalyser.controller.service;

import com.opencsv.bean.CsvBindByName;

public class IndianCensusCSV {
    @CsvBindByName(column = "slNo", required = true)
    public int slNo;

    @CsvBindByName(column = "name", required = true)
    public String name;

    @CsvBindByName(column = "Tin", required = true)
    public int Tin;

    @CsvBindByName(column = "stateCode", required = true)
    public int stateCode;

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "slNo='" + slNo + '\'' +
                ", name='" + name + '\'' +
                ", Tin='" + Tin + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
