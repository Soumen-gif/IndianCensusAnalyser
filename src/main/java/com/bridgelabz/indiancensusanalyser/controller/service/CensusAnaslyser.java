package com.bridgelabz.indiancensusanalyser.controller.service;

import com.google.gson.Gson;
import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.model.CensusCSVDAO;
import com.bridgelabz.indiancensusanalyser.controller.model.IndianCensusCSV;

import java.util.*;

public class CensusAnaslyser {
    Map<String, CensusCSVDAO> CensusMap;

    public CensusAnaslyser() {
        this.CensusMap = new HashMap<>();
    }

    public int loadIndianCensusCsvData(String csvFilePath) throws CensusAnalyserException {
        //return this.loadCensusData(csvFilePath, IndianCensusCSV.class);
        CensusMap = new CensusLoader().loadCensusData(csvFilePath, IndianCensusCSV.class);
        return CensusMap.size();
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        CensusMap = new CensusLoader().loadCensusData(csvFilePath, IndianCensusCSV.class);
        return CensusMap.size();
    }

    public int StateCodeCSVData(String csvFilePath) throws CensusAnalyserException {
        CensusMap = new CensusLoader().loadCensusData(csvFilePath, IndianCensusCSV.class);
        return CensusMap.size();
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (CensusMap == null || CensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.state);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(CensusMap.values());
        this.sort(censusDAOList, censusComparator);
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getStateCodeSortedCensusData() throws CensusAnalyserException {
        if (CensusMap == null || CensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(CensusMap.values());
        this.sort(censusDAOList, censusComparator);
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getStatePopulousSortedCensusData() throws CensusAnalyserException {
        if (CensusMap == null || CensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.population);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(CensusMap.values());
        this.sort(censusDAOList, censusComparator.reversed());
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getStatePopulationDensitySortedCensusData() throws CensusAnalyserException {
        if (CensusMap == null || CensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(CensusMap.values());
        this.sort(censusDAOList, censusComparator.reversed());
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getAreaInSqKmWiseSortedCensusData() throws CensusAnalyserException {
        if (CensusMap == null || CensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(CensusMap.values());
        this.sort(censusDAOList, censusComparator.reversed());
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getUSPopulationSortedCensusData() throws CensusAnalyserException {
        if (CensusMap == null || CensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.population);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(CensusMap.values());
        this.sort(censusDAOList, censusComparator.reversed());
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getPopulationDensityWiseSortedCensusDataForUS() throws CensusAnalyserException {
        if (CensusMap == null || CensusMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusCSVComparator = Comparator.comparing(census -> census.PopulationDensity);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(CensusMap.values());
        this.sort(censusDAOList, censusCSVComparator.reversed());
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    private void sort(List<CensusCSVDAO> censusDAOList, Comparator<CensusCSVDAO> censusCSVComparator) {
        for (int i = 0; i < censusDAOList.size(); i++) {
            for (int j = 0; j < censusDAOList.size() - i - 1; j++) {
                CensusCSVDAO censusCSV = censusDAOList.get(j);
                CensusCSVDAO censusCSV1 = censusDAOList.get(j + 1);
                if (censusCSVComparator.compare(censusCSV, censusCSV1) > 0) {
                    censusDAOList.set(j, censusCSV1);
                    censusDAOList.set(j + 1, censusCSV);
                }
            }
        }
    }
}