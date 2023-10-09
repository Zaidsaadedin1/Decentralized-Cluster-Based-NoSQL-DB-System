package org.example.Document;

import java.util.Map;

public interface NoSqlSchemaLessDocument {
    void setDocumentData(Map<String, String> documentData);

    String getValues();

    String getKeys();

    boolean isEmpty();
}
