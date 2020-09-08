package com.bridgelabz.indiancensusanalyser.controller.service;

import com.bridgelabz.indiancensusanalyser.controller.Exception.CSVBuilderException;
import com.bridgelabz.indiancensusanalyser.controller.Exception.CensusAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder<E> implements ICSVBuilder {

    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class CSVClass)
                                              throws CSVBuilderException {

        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(),
                         CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);

        }
    }
}
