package com.bridgelabz.indiancensusanalyser.controller.Exception;

public class CSVBuilderException extends Throwable {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM
    }

    public ExceptionType type;
    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
