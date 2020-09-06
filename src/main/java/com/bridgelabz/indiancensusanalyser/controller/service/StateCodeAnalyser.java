package com.bridgelabz.indiancensusanalyser.controller.service;

import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.Exception.StateCodeAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCodeAnalyser {
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

