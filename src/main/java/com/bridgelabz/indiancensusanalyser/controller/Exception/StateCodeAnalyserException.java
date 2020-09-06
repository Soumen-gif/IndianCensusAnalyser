package com.bridgelabz.indiancensusanalyser.controller.Exception;

public class StateCodeAnalyserException extends Exception {

    public StateCodeAnalyserException(String message, StateCodeAnalyserException.ExceptionType type) {
        super(message);
        this.type=type;
    }

    public enum ExceptionType {
        STATECODE_FILE_PROBLEM
    }

    public ExceptionType type;

//
}
