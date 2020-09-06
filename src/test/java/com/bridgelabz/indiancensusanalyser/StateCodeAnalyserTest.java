package com.bridgelabz.indiancensusanalyser;

import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.service.CensusAnaslyser;
import org.junit.Assert;
import org.junit.Test;

public class StateCodeAnalyserTest {
    private static final String INDIAN_STATE_CODE_FILE_PATH =
            "E:\\IndianCensusAnalyser\\src\\test\\resources\\IndianStateCode.csv";
    @Test
    public void given_StateCodeCSV_Should_ReturnsCorrectRecords() {
        try {
            CensusAnaslyser censusAnaslyser = new CensusAnaslyser();
            int numOfRecords = censusAnaslyser.loadIndianCensusCsvData(INDIAN_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }
}
