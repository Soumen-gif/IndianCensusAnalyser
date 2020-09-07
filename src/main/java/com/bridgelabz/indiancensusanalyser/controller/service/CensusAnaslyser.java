package com.bridgelabz.indiancensusanalyser.controller.service;

import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.Exception.StateCodeAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CensusAnaslyser {
    public int loadIndianCensusCsvData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvToBeanBuilder<IndianCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndianCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndianCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndianCensusCSV> indianCensusCSVIterator = csvToBean.iterator();
            int numOfEntries = 0;
            while (indianCensusCSVIterator.hasNext()) {
                numOfEntries++;
                indianCensusCSVIterator.next();
            }
            return numOfEntries;
        } catch (Exception e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        }
    }
    public int StateCodeCSVData(String csvFilePath) throws StateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvToBeanBuilder<StateCodeCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(StateCodeCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<StateCodeCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<StateCodeCSV> indianStateCodeCSVIterator = csvToBean.iterator();
            int numOfEntries = 0;
            while (indianStateCodeCSVIterator.hasNext()) {
                numOfEntries++;
                indianStateCodeCSVIterator.next();
            }
            return numOfEntries;
        } catch (Throwable e) {
            throw new StateCodeAnalyserException(e.getMessage(),
                    StateCodeAnalyserException.ExceptionType.STATECODE_FILE_PROBLEM);

        }

    }
}
