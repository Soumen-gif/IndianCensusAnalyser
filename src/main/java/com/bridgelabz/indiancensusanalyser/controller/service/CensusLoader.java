package com.bridgelabz.indiancensusanalyser.controller.service;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {
    public <E> Map loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
        {
            Map<String, CensusCSVDAO> CensusMap = new HashMap<>();
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
                Iterable<E> censusCSV = () -> censusCSVIterator;
                String className = censusCSVClass.getSimpleName();
                if (className.equals("IndianCensusCSV")) {
                    StreamSupport.stream(censusCSV.spliterator(), false)
                            .map(IndianCensusCSV.class::cast)
                            .forEach(csvCensus -> CensusMap.put(csvCensus.state, new CensusCSVDAO(csvCensus)));
                    return CensusMap;
                }
                if (className.equals("USCensusCSV")) {
                    StreamSupport.stream(censusCSV.spliterator(), false)
                            .map(USCensusCSV.class::cast)
                            .forEach(csvCensus -> CensusMap.put(csvCensus.State, new CensusCSVDAO(csvCensus)));
                    return CensusMap;
                }
                if (className.equals("StateCodeCSV")) {
                    StreamSupport.stream(censusCSV.spliterator(), false)
                            .map(StateCodeCSV.class::cast)
                            .forEach(csvCensus -> CensusMap.put(csvCensus.stateName, new CensusCSVDAO(csvCensus)));
                    return CensusMap;
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
            return null;
        }
    }
}
