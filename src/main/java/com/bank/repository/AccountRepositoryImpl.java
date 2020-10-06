package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.CreditCard;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountRepositoryImpl implements AccountRepository {
    public static final String SQL_GET_AMOUNT_BY_ACCOUNT_NUMBER = "SELECT amount FROM accounts WHERE number = ?";
    public static final String SQL_GET_AMOUNT_BY_ACCOUNT_ID = "SELECT amount FROM accounts WHERE id = ?";
    private static final String SQL_UPDATE_AMOUNT = "UPDATE accounts SET amount = ? WHERE number = ?";
    //    private static final String SQL_GET_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE id = ?";
    private static final String SQL_GET_ACCOUNT_LIST_BY_CLIENT_ID = "SELECT * FROM accounts WHERE clients_id = ?";
    private static final String SQL_GET_CREDIT_CARD_LIST_BY_ACCOUNT_ID = "SELECT * FROM credit_cards WHERE account_id = ?";
    private static final String SQL_ADD_CREDIT_CARD = "INSERT INTO credit_cards (account_id, number) VALUES (?, ?)";
    private static final String SQL_IS_CARD_NUMBER_EXISTS = "SELECT * FROM credit_cards WHERE number = ?";

    private DataSource dataSource;
    private ResourceReader resourceReader = new ResourceReader();


    public AccountRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addAccount(Client client, Account account) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.GET_ALL_CLIENT_ACCOUNTS.getPath());
        Connection connection = getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, client.getId());
            ps.setString(2, account.getNumber());
            ps.setBigDecimal(3, account.getAmount());
            ps.setString(4, account.getCurrency());
            ps.execute();
        }
    }

    @Override
    public Account getAccountByClientId(Client client) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.GET_ACCOUNT_BY_CLIENT_ID.getPath());
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, client.getId());
            ResultSet rs = stmt.executeQuery();
            Account account = null;
            while (rs.next()) {
                 account = Account.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .amount(rs.getBigDecimal("amount"))
                        .currency(rs.getString("currency"))
                        .build();
            }
            if(account != null){
                return account;
            }else {
                throw new SQLException("Not found any account!");
            }
        }

    }

    @Override
    public List<Account> getAllClientAccounts(Client client) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.GET_ALL_CLIENT_ACCOUNTS.getPath());
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, client.getId());
            ResultSet rs = stmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = Account.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .amount(rs.getBigDecimal("amount"))
                        .currency(rs.getString("currency"))
                        .build();
                accounts.add(account);
            }
            if (accounts.isEmpty()) {
                throw new SQLException("Not found any account!");
            } else {
                return accounts;
            }
        }
    }

//    @Override
//    public BigDecimal checkBalanceByAccountNumber(String accountNumber) throws SQLException {
//        Connection conn = getConnection();
//        PreparedStatement ps = conn.prepareStatement(SQL_GET_AMOUNT_BY_ACCOUNT_NUMBER);
//        ps.setString(1, accountNumber);
//        ResultSet resultSet = ps.executeQuery();
//        BigDecimal amount = null;
//        if (resultSet.next()) {
//            amount = resultSet.getBigDecimal("amount");
//        }
//        resultSet.close();
//        ps.close();
//        Utils.closeQuietly(conn);
//        if (amount == null) {
//            throw new SQLException("Account with number = " + accountNumber + ", not found");
//        } else {
//            return amount;
//        }
//
//    }
//
//
//
//    @Override
//    public BigDecimal checkBalanceByAccountId(Integer accountId) throws SQLException {
//        Connection conn = getConnection();
//        PreparedStatement ps = conn.prepareStatement(SQL_GET_AMOUNT_BY_ACCOUNT_ID);
//        ps.setInt(1, accountId);
//        ResultSet resultSet = ps.executeQuery();
//        BigDecimal amount = null;
//        if (resultSet.next()) {
//            amount = resultSet.getBigDecimal("amount");
//        }
//        resultSet.close();
//        ps.close();
//        Utils.closeQuietly(conn);
//        if (amount == null) {
//            throw new SQLException("Account with Account Id = " + accountId + ", not found");
//        } else {
//            return amount;
//        }
//    }
//
//    @Override
//    public boolean depositFunds(String accountNumber, BigDecimal amount) throws SQLException {
//        BigDecimal balance = checkBalanceByAccountNumber(accountNumber);
//        Connection conn = getConnection();
//        PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_AMOUNT);
//        ps.setBigDecimal(1, balance.add(amount));
//        ps.setString(2, accountNumber);
//        int res = ps.executeUpdate();
//        ps.close();
//        Utils.closeQuietly(conn);
//        return res != 0;
//    }
//
//    @Override
//    public List<Account> getAccountListByClientId(Integer clientId) throws SQLException {
//        Connection conn = getConnection();
//        PreparedStatement ps = conn.prepareStatement(SQL_GET_ACCOUNT_LIST_BY_CLIENT_ID);
//        ps.setInt(1, clientId);
//        ResultSet resultSet = ps.executeQuery();
//
//        List<Account> accountList = new ArrayList<>();
//        while (resultSet.next()) {
//            Account account = new Account();
//            account = new Account();
//            account.setId(resultSet.getInt("id"));
//            account.setNumber(resultSet.getString("number"));
//            account.setAmount(resultSet.getBigDecimal("amount"));
//            account.setCurrency(resultSet.getString("currency"));
//            accountList.add(account);
//        }
//
//        resultSet.close();
//        ps.close();
//        Utils.closeQuietly(conn);
//
//        if (accountList.isEmpty()) {
//            throw new SQLException("For the Client with Id = " + clientId + ", not found any account");
//        } else {
//            return accountList;
//        }
//    }
//
//    @Override
//    public List<CreditCard> getCreditCardListByAccountId(Integer accountId) throws SQLException {
//        Connection conn = getConnection();
//        PreparedStatement ps = conn.prepareStatement(SQL_GET_CREDIT_CARD_LIST_BY_ACCOUNT_ID);
//        ps.setInt(1, accountId);
//        ResultSet resultSet = ps.executeQuery();
//
//        List<CreditCard> cardList = new ArrayList<>();
//        while (resultSet.next()) {
//            CreditCard card = new CreditCard();
//            card.setId(resultSet.getInt("id"));
//            card.setNumber(resultSet.getString("number"));
//            card.setRegistered(resultSet.getTimestamp("registered"));
//            cardList.add(card);
//        }
//
//        resultSet.close();
//        ps.close();
//        Utils.closeQuietly(conn);
//
//        if (cardList.isEmpty()) {
//            throw new SQLException("For the Account with Id = " + accountId + ", no cards found");
//        } else {
//            return cardList;
//        }
//    }
//
//    @Override
//    public boolean addCreditCard(Integer accountId, String cardNumber) throws SQLException {
//        Connection conn = getConnection();
//        PreparedStatement ps = conn.prepareStatement(SQL_ADD_CREDIT_CARD);
//        ps.setInt(1, accountId);
//        ps.setString(2, cardNumber);
//
//        if (ps.executeUpdate() == 0) {
//            throw new SQLException("For the Account with Id = " + accountId + ". Credit card was now issued!");
//        }
//        return true;
//    }
//
//    @Override
//    public boolean isCardNumberExists(String cardNumber) throws SQLException {
//        Connection conn = getConnection();
//        PreparedStatement ps = conn.prepareStatement(SQL_IS_CARD_NUMBER_EXISTS);
//        ps.setString(1, cardNumber);
//        ResultSet resultSet = ps.executeQuery();
//        boolean isExists = resultSet.next();
//
//        resultSet.close();
//        ps.close();
//        Utils.closeQuietly(conn);
//        return isExists;
//    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
