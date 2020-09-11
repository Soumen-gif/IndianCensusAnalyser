package com.bridgelabz.indiancensusanalyser.controller.service;
import com.google.gson.Gson;
import com.bridgelabz.csvbuilder.exception.CSVBuilderException;
import com.bridgelabz.csvbuilder.service.CSVBuilderFactory;
import com.bridgelabz.csvbuilder.service.ICSVBuilder;
import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.controller.model.CensusCSVDAO;
import com.bridgelabz.indiancensusanalyser.controller.model.IndianCensusCSV;
import com.bridgelabz.indiancensusanalyser.controller.model.StateCodeCSV;
import com.bridgelabz.indiancensusanalyser.controller.model.USCensusCSV;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnaslyser {
    Map<String, CensusCSVDAO> CensusMap;

    public CensusAnaslyser() {
        this.CensusMap = new HashMap<>();
    }

    public <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> censusCSV = () -> censusCSVIterator;
            String className = censusCSVClass.getSimpleName();
            if (className.equals("IndianCensusCSV")) {
                StreamSupport.stream(censusCSV.spliterator(), false)
                        .map(IndianCensusCSV.class::cast)
                        .forEach(csvCensus -> this.CensusMap.put(csvCensus.state, new CensusCSVDAO(csvCensus)));
                return CensusMap.size();
            }
            if (className.equals("USCensusCSV")) {
                StreamSupport.stream(censusCSV.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(csvCensus -> this.CensusMap.put(csvCensus.State, new CensusCSVDAO(csvCensus)));
                return CensusMap.size();
            }
            if (className.equals("StateCodeCSV")) {
                StreamSupport.stream(censusCSV.spliterator(), false)
                        .map(StateCodeCSV.class::cast)
                        .forEach(csvCensus -> this.CensusMap.put(csvCensus.stateName, new CensusCSVDAO(csvCensus)));
                return CensusMap.size();
            }
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        }
        return 0;
    }
    public int loadIndianCensusCsvData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath, IndianCensusCSV.class);
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath, USCensusCSV.class);
    }

    public int StateCodeCSVData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath, StateCodeCSV.class);
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
    public String getAreaWiseSortedCensusDataForUS() throws CensusAnalyserException {
        if (CensusMap == null || CensusMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusCSVComparator = Comparator.comparing(census -> census.Totalarea);
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