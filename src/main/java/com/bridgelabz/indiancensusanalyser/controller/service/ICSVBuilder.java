package com.bridgelabz.indiancensusanalyser.controller.service;

import com.bridgelabz.indiancensusanalyser.controller.Exception.CSVBuilderException;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder {
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class CSVClass)
            throws CSVBuilderException;
    public <E> List<E> getCSVFileList(Reader reader, Class CSVClass)
            throws CSVBuilderException;
}
