package com.bridgelabz.indiancensusanalyser.controller.service;

import com.opencsv.bean.CsvBindByName;

public class IndianCensusCSV {
    @CsvBindByName(required = true)
    public String state;

    @CsvBindByName(required = true)
    public String population;

    @CsvBindByName(required = true)
    public int areaInSqKm;

    @CsvBindByName(required = true)
    public int densityPerSqKm;

    @Override
    public String toString() {
        return "IndianCensusCSV{" +
                "state=" + state +
                ", population='" + population + '\'' +
                ", areaInSqKm=" + areaInSqKm +
                ", densityPerSqKm=" + densityPerSqKm +
                '}';
    }
}
