package org.example.DataBase;

import org.example.Table.Table;

import java.util.ArrayList;

public interface NoSqlDataBase {
    void createTable(String tableName);

    Table getTableByName(String tableName);

    void deleteTable(String tableName);

    String getDatabaseName();

    ArrayList<Table> getTables();


}
