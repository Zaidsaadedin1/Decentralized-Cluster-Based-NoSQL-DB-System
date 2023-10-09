package org.example.NoSqlApplications;

import org.example.DataBase.DataBase;
import java.util.ArrayList;


public class Application implements NoSqlApplications{
    private  String appName;

    private final ArrayList<DataBase> dataBases;
    public Application(String appName){
        this.appName=appName;
        dataBases=new ArrayList<>();
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public void createDataBase(String DataBaseName) {
        dataBases.add(new DataBase(DataBaseName));
    }

    @Override
    public DataBase getDataBaseByName(String DataBaseName) {
        for (DataBase dataBase : dataBases) {
            if (dataBase.getDatabaseName().equals(DataBaseName)) {
                return dataBase;
            }
        }
        return null; // DataBase not found
    }

    @Override
    public void deleteDataBase(String DataBaseName) {
        DataBase databaseToRemove = null;
        for (DataBase dataBase : dataBases) {
            if (dataBase.getDatabaseName().equals(DataBaseName)) {
                databaseToRemove = dataBase;
                break;
            }
        }
        if (databaseToRemove != null) {
            dataBases.remove(databaseToRemove);
        }
    }

    @Override
    public String getAppByName() {
        return getAppName();
    }

    @Override
    public ArrayList<DataBase> getDataBases() {
        return dataBases;
    }
}
