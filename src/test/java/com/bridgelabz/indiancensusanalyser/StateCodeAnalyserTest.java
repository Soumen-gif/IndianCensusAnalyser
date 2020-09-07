package com.bridgelabz.indiancensusanalyser;
import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.service.CensusAnaslyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateCodeAnalyserTest {
    private static final String INDIAN_STATE_CODE_FILE_PATH =
            "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndianStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "\"E:\\\\IndianCensusAnalyser\\\\src\\\\test";
    private static final String INDIAN_STATE_CODE_CSV_WRONG_DELIMITER =
            "./src/test/resources/IndiaStateCodeWrongDelimiter.csv";
    @Test
    public void given_StateCodeCSV_Should_ReturnsCorrectRecords() {
        try {
            CensusAnaslyser censusAnaslyser = new CensusAnaslyser();
            int numOfRecords = censusAnaslyser.StateCodeCSVData(INDIAN_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
          } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenStaeCode_CSV_WithWrongFile_Path_ShouldThrowException() {
        try {
            CensusAnaslyser censusAnaslyser = new CensusAnaslyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnaslyser.StateCodeCSVData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenStaeCode_CSV_WithWrongFile_ShouldThrowException() {
        try {
            final String WRONG_CSV_FILE_TYPE = "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndianStateCensusData.txt";
            CensusAnaslyser censusAnaslyser = new CensusAnaslyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnaslyser.StateCodeCSVData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenWrongDelimiter_InIndiaStateCodeData_ShouldReturnCustomExceptionType() {
        CensusAnaslyser censusAnaslyser = new CensusAnaslyser();
        try {
            censusAnaslyser.StateCodeCSVData(INDIAN_STATE_CODE_CSV_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
}
