package org.example.NoSqlApplications;

import org.example.DataBase.DataBase;

import java.util.ArrayList;

public interface NoSqlApplications {
    String getAppName();

    void setAppName(String appName);

    void createDataBase(String DataBaseName);

    DataBase getDataBaseByName(String DataBaseName);

    void deleteDataBase(String DataBaseName);

    String getAppByName();

    ArrayList<DataBase> getDataBases();
}
