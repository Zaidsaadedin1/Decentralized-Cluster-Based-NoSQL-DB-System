package org.example.Table;

import org.example.Document.Document;

import java.util.Map;

public interface NoSqlTable {
    Document getDocumentById(int id);

    void addDocument(Map<String, String> documentData);

    Map<Integer, Document> getAllDocuments();

    boolean isDocumentAvailable(int id);

    void deleteDocument(int id);

    void modifyDocument(int documentId, Map<String, String> newDocumentData);

    String getTableName();
}
