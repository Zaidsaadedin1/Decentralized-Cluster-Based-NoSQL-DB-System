package org.example.Document;

import java.util.Map;

public class Document implements NoSqlSchemaLessDocument {

    public Map<String, String> documentData;

    public Document(Map<String, String> documentData) {
        this.documentData = documentData;
    }

    @Override
    public void setDocumentData(Map<String, String> documentData) {
        this.documentData = documentData;
    }

    @Override
    public String getValues() {
        return documentData.values().toString();
    }

    @Override
    public String getKeys() {
        return documentData.keySet().toString();
    }

    @Override
    public boolean isEmpty() {
        if (documentData.isEmpty()) {
            return true;
        }
        return false;
    }


}
