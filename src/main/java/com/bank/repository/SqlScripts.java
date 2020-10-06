package com.bank.repository;


/**
 * SQL скрипты с указанием имени файла
 */
public enum SqlScripts {

    /**
     * Выбор всех счетов клиента
     */
    SELECT_ALL_ACCOUNTS("dataBase/sql/query/AccountSelectAll.sql"),

    ADD_CLIENT("dataBase/sql/query/AddClient.sql"),

    SELECT_ALL_CLIENTS("dataBase/sql/query/SelectAllClients.sql"),

    UPDATE_CLIENT("dataBase/sql/query/UpdateClient.sql"),

    GET_CLIENT_BY_ID("dataBase/sql/query/GetClientByID.sql"),

    DELETE_CLIENT("dataBase/sql/query/DeleteClient.sql"),

    ADD_CLIENT_ACCOUNT("dataBase/sql/query/AddClientAccount.sql"),

    GET_ALL_CLIENT_ACCOUNTS("dataBase/sql/query/GetAllClientIAccounts.sql"),

    GET_ACCOUNT_BY_CLIENT_ID("dataBase/sql/query/GetAccountByClientId.sql"),

    ADD_ACCOUNT("dataBase/sql/query/AddAccount.sql");

    private final String path;

    SqlScripts(String path) {
        this.path = path;
    }
    public String getPath(){return path;}
}
