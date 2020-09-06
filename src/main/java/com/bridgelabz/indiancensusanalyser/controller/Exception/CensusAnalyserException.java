package com.bridgelabz.indiancensusanalyser.controller.Exception;

public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM
    }

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}

