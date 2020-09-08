package com.bridgelabz.indiancensusanalyser.controller.service;

import com.bridgelabz.csvbuilder.exception.CSVBuilderException;
import com.bridgelabz.csvbuilder.service.CSVBuilderFactory;
import com.bridgelabz.csvbuilder.service.ICSVBuilder;
import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnaslyser {
    List<IndianCensusCSV> csvFileList = null;

    public int loadIndianCensusCsvData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            csvFileList = csvBuilder.getCSVFileList(reader, IndianCensusCSV.class);
            return csvFileList.size();
        } catch (Exception | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public int StateCodeCSVData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<StateCodeCSV> csvFileList = csvBuilder.getCSVFileList(reader, StateCodeCSV.class);
            return csvFileList.size();
        } catch (Exception | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> iterable = () -> iterator;
        int noOfEntries = (int) StreamSupport.stream(iterable.spliterator(), false).count();
        return noOfEntries;
    }
    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndianCensusCsvData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndianCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(csvFileList, censusComparator);
        String toJson = new Gson().toJson(csvFileList);
        return toJson;
    }
    public String getStateCodeSortedCensusData(String INDIA_CENSUS_CSV_FILE_PATH) throws CensusAnalyserException {
        if(csvFileList == null || csvFileList.size() == 0){
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndianCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(csvFileList,censusComparator);
        return new Gson().toJson(csvFileList);

    }
    public void sort (List<IndianCensusCSV> csvFileList, Comparator<IndianCensusCSV> censusComparator) {
        for (int i = 0; i < csvFileList.size(); i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                IndianCensusCSV census1 = csvFileList.get(j);
                IndianCensusCSV census2 = csvFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j + 1, census1);
                }

            }
        }
    }
}