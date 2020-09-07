package com.bridgelabz.indiancensusanalyser.controller.service;

import com.opencsv.bean.CsvBindByName;

public class StateCodeCSV {
    @CsvBindByName(required = true)
    public String state;

    @CsvBindByName(required = true)
    public int population;

    @CsvBindByName(required = true)
    public int areaInSqKm;

    @CsvBindByName(required = true)
    public int densityPerSqKm;

    @Override
    public String toString() {
        return "StateCodeCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}

