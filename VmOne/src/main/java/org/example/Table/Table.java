package org.example.Table;

import org.example.Document.Document;

import java.util.*;

public class Table implements NoSqlTable {
    private final String tableName;
    private final Map<Integer, Document> documents;

    private int latestDocumentId = 1;

    public Table(String tableName) {
        this.tableName = tableName;
        this.documents = new HashMap<>();
    }

    @Override
    public Document getDocumentById(int id) {
        return documents.get(id);
    }

    @Override
    public void addDocument(Map<String, String> documentData) {
        Document document = new Document(documentData);
        int documentId = latestDocumentId++;
        documents.put(documentId, document);
    }

    @Override
    public Map<Integer, Document> getAllDocuments() {
        return documents;
    }

    @Override
    public boolean isDocumentAvailable(int id) {
        return documents.containsKey(id);
    }

    @Override
    public void deleteDocument(int id) {
        if (isDocumentAvailable(id)) {
            documents.remove(id);
        }
    }

    @Override
    public void modifyDocument(int documentId, Map<String, String> newDocumentData) {
        Document targetDocument = getDocumentById(documentId);
        targetDocument.setDocumentData(newDocumentData);
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
