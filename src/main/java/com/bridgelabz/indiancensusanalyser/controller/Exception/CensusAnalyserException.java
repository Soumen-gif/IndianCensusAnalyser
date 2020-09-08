package com.bridgelabz.indiancensusanalyser.controller.Exception;

public class CensusAnalyserException extends Exception {

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type=type;
    }

    // public CensusAnalyserException(String message, ExceptionType type) {
    //   this.type = type;
   //}
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM
    }
    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
}

