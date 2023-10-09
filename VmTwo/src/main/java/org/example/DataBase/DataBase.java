package org.example.DataBase;

import org.example.Table.Table;

import java.util.ArrayList;

public class DataBase implements NoSqlDataBase {
    private final String databaseName;
    private final ArrayList<Table> tables;

    public DataBase(String databaseName) {
        this.databaseName = databaseName;
        this.tables = new ArrayList<>();
    }

    @Override
    public void createTable(String tableName) {
        tables.add(new Table(tableName));
    }

    @Override
    public Table getTableByName(String tableName) {
        for (Table table : tables) {
            if (table.getTableName().equals(tableName)) {
                return table;
            }
        }
        return null; // Table not found
    }

    @Override
    public void deleteTable(String tableName) {
        Table tableToRemove = null;
        for (Table table : tables) {
            if (table.getTableName().equals(tableName)) {
                tableToRemove = table;
                break;
            }
        }
        if (tableToRemove != null) {
            tables.remove(tableToRemove);
        }
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public ArrayList<Table> getTables() {
        return tables;
    }
}
