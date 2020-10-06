package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.CreditCard;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {


    BigDecimal checkBalanceByAccountNumber(String accountNumber) throws SQLException;
    BigDecimal checkBalanceByAccountId(Integer accountId) throws SQLException;

    boolean depositFunds(String accountNumber, BigDecimal amount) throws SQLException;

    List<Account> getAccountListByClientId (Integer clientId) throws SQLException;

    List<CreditCard> getCreditCardListByAccountId(Integer accountId) throws SQLException;


    boolean addCreditCard(Integer accountId, String cardNumber) throws SQLException;

    boolean isCardNumberExists(String cardNumber) throws SQLException;


}