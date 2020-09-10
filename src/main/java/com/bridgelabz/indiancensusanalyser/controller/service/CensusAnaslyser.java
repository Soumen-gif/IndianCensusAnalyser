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
    Map<String, CensusCSVDAO> stateCensusMap;
    Map<String, CensusCSVDAO> stateCodeMap;
    Map<String, CensusCSVDAO> usCensusMap;

    public CensusAnaslyser() {
        this.stateCensusMap = new HashMap<>();
        this.stateCodeMap = new HashMap<>();
        this.usCensusMap = new HashMap<>();
    }

    public int loadIndianCensusCsvData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndianCensusCSV.class);
            Iterable<IndianCensusCSV> indiaCensusCSV = () -> csvFileIterator;
            StreamSupport.stream(indiaCensusCSV.spliterator(), false)
                    .forEach(csvCensus -> this.stateCensusMap.put(csvCensus.state, new CensusCSVDAO((csvCensus))));
            return stateCensusMap.size();
        } catch (Exception | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, USCensusCSV.class);
            Iterable<USCensusCSV> usCensusCSVS = () -> censusCSVIterator;
            StreamSupport.stream(usCensusCSVS.spliterator(), false)
                    .forEach(csvCensus -> this.usCensusMap.put(csvCensus.State, new CensusCSVDAO(csvCensus)));
            return usCensusMap.size();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int StateCodeCSVData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCodeCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
            Iterable<StateCodeCSV> indiaStateCodeCSV = () -> csvFileIterator;
            StreamSupport.stream(indiaStateCodeCSV.spliterator(), false)
                    .forEach(csvCensus -> this.stateCodeMap.put(csvCensus.stateName, new CensusCSVDAO(csvCensus)));
            return stateCodeMap.size();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> statesCodeIterable = () -> iterator;
        int numberOfEntries = (int) StreamSupport.stream(statesCodeIterable.spliterator(), false).count();
        return numberOfEntries;
    }

    public String getStateWiseSortedCensusData(String INDIA_CENSUS_CSV_FILE_PATH) throws CensusAnalyserException {
        if (stateCensusMap == null || stateCensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.state);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(stateCensusMap.values());
        this.sort(censusDAOList, censusComparator);
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getStateCodeSortedCensusData(String indianStateCodeFilePath) throws CensusAnalyserException {
        if (stateCensusMap == null || stateCensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(stateCensusMap.values());
        this.sort(censusDAOList, censusComparator);
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getStatePopulousSortedCensusData() throws CensusAnalyserException {
        if (stateCensusMap == null || stateCensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.population);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(stateCensusMap.values());
        this.sort(censusDAOList, censusComparator.reversed());
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getStatePopulationDensitySortedCensusData() throws CensusAnalyserException {
        if (stateCensusMap == null || stateCensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(stateCensusMap.values());
        this.sort(censusDAOList, censusComparator.reversed());
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }

    public String getAreaInSqKmWiseSortedCensusData() throws CensusAnalyserException {
        if (stateCensusMap == null || stateCensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(stateCensusMap.values());
        this.sort(censusDAOList, censusComparator.reversed());
        String sortedData = new Gson().toJson(censusDAOList);
        return sortedData;
    }
    public String getUSPopulationSortedCensusData() throws CensusAnalyserException {
        if (stateCensusMap == null || stateCensusMap.size() == 0) {
            throw new CensusAnalyserException("NO Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusCSVDAO> censusComparator = Comparator.comparing(census -> census.population);
        List<CensusCSVDAO> censusDAOList = new ArrayList<>(stateCensusMap.values());
        this.sort(censusDAOList, censusComparator.reversed());
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