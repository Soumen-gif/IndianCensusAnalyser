package com.bridgelabz.indiancensusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.service.CensusAnaslyser;

public class CensusAnalyserTest {
    private static final String INDIAN_CENSUS_FILE_PATH =
                                      "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndianStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "\"E:\\\\IndianCensusAnalyser\\\\src\\\\test";
    private static final String INDIAN_CENSUS_CSV_WRONG_DELIMITER =
                                       "./src/test/resources/IndiaStateCensusDataWrongDelimiter.csv";
    private static final String INDIAN_CENSUS_CSV_MISSING = "./src/test/resources/IndiaStateCensusDataMissingHeader.csv";

    @Test
    public void given_IndianCensusCSVFil_Should_ReturnsCorrectRecords() {
        try {
            CensusAnaslyser censusAnaslyser = new CensusAnaslyser();
            int numOfRecords = censusAnaslyser.loadIndianCensusCsvData(INDIAN_CENSUS_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianCensusCsvData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
        try {
            final String WRONG_CSV_FILE_TYPE = "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndianStateCensusData.txt";
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianCensusCsvData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenWrongDelimiter_InIndiaCensusData_ShouldReturnCustomExceptionType() {
        CensusAnaslyser censusAnaslyser = new CensusAnaslyser();
        try {
            censusAnaslyser.loadIndianCensusCsvData(INDIAN_CENSUS_CSV_WRONG_DELIMITER);
        } catch ( CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenMissingHeader_InIndiaCensusData_ShouldReturnCustomExceptionType() {
        try {
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
             censusAnalyser.loadIndianCensusCsvData(INDIAN_CENSUS_CSV_MISSING);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
}

