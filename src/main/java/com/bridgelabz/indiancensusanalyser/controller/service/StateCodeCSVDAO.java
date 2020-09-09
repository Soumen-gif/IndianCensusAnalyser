package com.bridgelabz.indiancensusanalyser.controller.service;

public class StateCodeCSVDAO {
    String stateName;
    String stateCode;
    String SrNo;
    String TIN;

    StateCodeCSVDAO(StateCodeCSV stateCodeDAO) {

        stateName = stateCodeDAO.stateName;
        stateCode = stateCodeDAO.stateCode;
        SrNo = stateCodeDAO.SrNo;
        TIN = stateCodeDAO.TIN;
    }
}
