package com.bridgelabz.indiancensusanalyser.controller.service;

public class CSVBuilderFactory {

    public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
