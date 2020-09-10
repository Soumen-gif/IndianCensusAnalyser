package com.bridgelabz.indiancensusanalyser.controller.service;

import com.bridgelabz.csvbuilder.exception.CSVBuilderException;
import com.bridgelabz.csvbuilder.service.CSVBuilderFactory;
import com.bridgelabz.csvbuilder.service.ICSVBuilder;
import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.model.IndianCensusCSV;
import com.bridgelabz.indiancensusanalyser.controller.model.IndianCensusCSVDAO;
import com.bridgelabz.indiancensusanalyser.controller.model.StateCodeCSV;
import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnaslyser {
    List<IndianCensusCSVDAO> csvFileList = null;
    List<IndianCensusCSVDAO> stateList = null;
    Map<String, IndianCensusCSVDAO> stateCensusMap;
    Map<String, IndianCensusCSVDAO> stateCodeMap;

    public CensusAnaslyser() {
        this.csvFileList = new ArrayList<>();
        this.stateList = new ArrayList<>();
        this.stateCensusMap = new HashMap<>();
        this.stateCodeMap = new HashMap<>();
    }

    public int loadIndianCensusCsvData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            //csvFileList = csvBuilder.getCSVFileList(reader, IndianCensusCSV.class);
            Iterator<IndianCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndianCensusCSV.class);
            Iterable<IndianCensusCSV> csvIterable = () -> csvIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(censusCSV -> this.stateCensusMap.put(censusCSV.state, new IndianCensusCSVDAO(censusCSV)));

//            while (csvIterator.hasNext()) {
//                this.csvFileList.add(new IndianCensusCSVDAO(csvIterator.next()));
//            }
            return this.stateCensusMap.size();
        } catch (Exception | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public int StateCodeCSVData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCodeCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
            Iterable<StateCodeCSV> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(censusCSV -> this.stateCodeMap.put(censusCSV.stateName, new IndianCensusCSVDAO(censusCSV)));
//            while (csvFileIterator.hasNext()) {
//                this.stateList.add(new StateCodeCSVDAO(csvFileIterator.next()));
//            }
            return stateCensusMap.size();
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

        Comparator<IndianCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(csvFileList, censusComparator);
        String toJson = new Gson().toJson(this.csvFileList);
        return toJson;
    }

    public String getStateCodeSortedCensusData(String INDIAN_STATE_CODE_FILE_PATH) throws CensusAnalyserException {
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndianCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(csvFileList, censusComparator);
        return new Gson().toJson(csvFileList);
    }

    public String getStatePopulousSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndianCensusCsvData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndianCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sort(csvFileList, censusComparator);
        String toJson = new Gson().toJson(csvFileList);
        return toJson;
    }

    public String getStatePopulationDensitySortedCensusData() throws CensusAnalyserException {
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndianCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sort(csvFileList, censusComparator);
        return new Gson().toJson(csvFileList);
    }

    public String getAreaInSqKmWiseSortedCensusData() throws CensusAnalyserException {
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndianCensusCSVDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sort(csvFileList, censusComparator);
        return new Gson().toJson(csvFileList);
    }

    public void sort(List<IndianCensusCSVDAO> csvFileList, Comparator<IndianCensusCSVDAO> censusComparator) {
        for (int i = 0; i < csvFileList.size(); i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                IndianCensusCSVDAO census1 = csvFileList.get(j);
                IndianCensusCSVDAO census2 = csvFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j + 1, census1);
                }
            }
        }
    }
}
