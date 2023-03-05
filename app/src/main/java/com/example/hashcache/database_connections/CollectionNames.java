package com.example.hashcache.database_connections;

public enum CollectionNames {
    PLAYERS("players"),
    CODE_LOCATIONS("codeLocations"),
    SCANNABLE_CODES("scannableCodes");
    public final String collectionName;

    private CollectionNames(String collectionName) {
        this.collectionName = collectionName;
    }
}
