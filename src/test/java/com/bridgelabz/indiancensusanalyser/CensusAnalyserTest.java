package com.bridgelabz.indiancensusanalyser;

import com.bridgelabz.indiancensusanalyser.controller.model.IndianCensusCSV;
import com.bridgelabz.indiancensusanalyser.controller.model.StateCodeCSV;
import com.bridgelabz.indiancensusanalyser.controller.model.USCensusCSV;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.service.CensusAnaslyser;

public class CensusAnalyserTest {
    private static final String INDIAN_CENSUS_FILE_PATH =
            "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "\"E:\\\\IndianCensusAnalyser\\\\src\\\\test";
    private static final String INDIAN_CENSUS_CSV_WRONG_DELIMITER =
            "./src/test/resources/IndiaStateCensusDataWrongDelimiter.csv";
    private static final String INDIAN_CENSUS_CSV_MISSING = "./src/test/resources/IndiaStateCensusDataMissingHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH =
            "E:\\IndianCensusAnalyser\\src\\test\\resources\\USCensusData.csv";
    private final String INDIA_CENSUS_CSV_FILE_PATH =
            "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndianStateCensusData.csv";
    private static final String INDIAN_STATE_CODE_FILE_PATH =
            "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndianStateCode.csv";

    @Test
    public void given_IndianCensusCSVFile_Should_ReturnsCorrectRecords() {
        try {
            CensusAnaslyser censusAnaslyser = new CensusAnaslyser();
            int numOfRecords = censusAnaslyser.loadIndianCensusCsvData(INDIAN_CENSUS_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
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
        } catch (CensusAnalyserException e) {
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

    @Test
    public void givenIndianCensusData_WhenSortedOneState_ShouldReturnSortResult() {
        try {
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
            String SortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndianCensusCSV censusCsv[] = new Gson().fromJson(SortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCsv[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOneState_Code_ShouldReturnSortResult() {
        try {
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
            String SortedCensusData = censusAnalyser.getStateCodeSortedCensusData();
            StateCodeCSV censusCsv[] = new Gson().fromJson(SortedCensusData, StateCodeCSV[].class);
            Assert.assertEquals("Andhra Pradesh New", censusCsv[0].stateCode);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndianCensusData_Populationwise_Code_ShouldReturnSortResult() {
        try {
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
            String SortedCensusData = censusAnalyser.getStatePopulousSortedCensusData();
            IndianCensusCSV censusCsv[] = new Gson().fromJson(SortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals("Uttarakhand", censusCsv[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortResult() {
        try {
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
            censusAnalyser.StateCodeCSVData(INDIAN_CENSUS_FILE_PATH);
            String SortedCensusData = censusAnalyser.getStatePopulationDensitySortedCensusData();
            IndianCensusCSV censusCsv[] = new Gson().fromJson(SortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh", censusCsv[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnAreaInSqKm_ShouldReturnSortedResult() {
        try {
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
            censusAnalyser.loadIndianCensusCsvData(INDIAN_CENSUS_FILE_PATH);
            String SortedCensusData = censusAnalyser.getAreaInSqKmWiseSortedCensusData();
            IndianCensusCSV[] censusCSV = new Gson().fromJson(SortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals(342239, censusCSV[censusCSV.length - 1].areaInSqKm);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUS_CensusData_Should_Return_Currect_Records() {
        try {
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
            int usCensusDataCount = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals("51", usCensusDataCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenUSCensusData_Sorted_ShouldReturn_SortedValues() {
        try {
            CensusAnaslyser censusAnalyser = new CensusAnaslyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getUSPopulationSortedCensusData();
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("AK", censusCSV[0].Population);

        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}