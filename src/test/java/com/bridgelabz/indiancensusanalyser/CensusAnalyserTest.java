package com.bridgelabz.indiancensusanalyser;

import org.junit.Assert;
import org.junit.Test;
import service.CensusAnalyserException;
import service.CensusAnaslyser;

public class CensusAnalyserTest {
    private static final String INDIAN_CENSUS_FILE_PATH =
            "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndianStateCensusData.csv";
   // private static final String WRONG_CSV_FILE_PATH = "\"E:\\\\IndianCensusAnalyser\\\\src\\\\test";

    @Test
    public void given_IndianCensusCSVFil_Should_ReturnsCorrectRecords()  {
       try {
           CensusAnaslyser censusAnaslyser = new CensusAnaslyser();
           int numOfRecords = censusAnaslyser.loadIndianCensusCsvData(INDIAN_CENSUS_FILE_PATH);
           Assert.assertEquals(29,numOfRecords);
       }catch (CensusAnalyserException e){
       }
       }
}
