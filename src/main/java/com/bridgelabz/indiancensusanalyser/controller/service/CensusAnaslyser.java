package com.bridgelabz.indiancensusanalyser.controller.service;

import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnaslyser {
    public int loadIndianCensusCsvData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndianCensusCSV> censusCSVIterator = this.getCSVFileIterator(reader, IndianCensusCSV.class);
            return this.getCount(censusCSVIterator);
           } catch (Exception e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public int StateCodeCSVData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<StateCodeCSV> censusCSVIterator = this.getCSVFileIterator(reader,StateCodeCSV.class);
            return this.getCount(censusCSVIterator);

        } catch (Exception e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    private <E> int getCount(Iterator<E> iterator)
    {
        Iterable<E> iterable = () -> iterator;
        int namOfEateries = (int) StreamSupport.stream(iterable.spliterator(), false).count();
        return namOfEateries;
    }
    private <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> CSVClass) throws CensusAnalyserException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
          } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
