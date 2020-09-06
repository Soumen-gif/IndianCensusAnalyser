package com.bridgelabz.indiancensusanalyser;

import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.Exception.StateCodeAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.service.CensusAnaslyser;
import com.bridgelabz.indiancensusanalyser.controller.service.StateCodeAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateCodeAnalyserTest {
    private static final String INDIAN_STATE_CODE_FILE_PATH =
            "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndianStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "\"E:\\\\IndianCensusAnalyser\\\\src\\\\test";

    @Test
    public void given_StateCodeCSV_Should_ReturnsCorrectRecords() {
        try {
            StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
            int numOfRecords = stateCodeAnalyser.StateCodeCSVData(INDIAN_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
           } catch (StateCodeAnalyserException e) {
        }
    }
    @Test
    public void givenStaeCode_CSV_WithWrongFile_ShouldThrowException() {
        try {
            StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(StateCodeAnalyserException.class);
            stateCodeAnalyser.StateCodeCSVData(WRONG_CSV_FILE_PATH);
        } catch (StateCodeAnalyserException e) {
            Assert.assertEquals(StateCodeAnalyserException.ExceptionType.STATECODE_FILE_PROBLEM, e.type);
        }
    }
}
