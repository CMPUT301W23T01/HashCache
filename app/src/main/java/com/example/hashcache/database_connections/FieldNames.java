package com.example.hashcache.database_connections;

public enum FieldNames {
    GENERATED_NAME("generatedName"),
    GENERATED_SCORE("generatedScore"),
    CODE_LOCATION_ID("codeLocationId"),
    SCANNABLE_CODE_ID("scannableCodeId"),
    COMMENT_BODY("body"),
    COMMENTATOR_ID("commentatorId");

    public final String fieldName;

    private FieldNames(String fieldName) {
        this.fieldName = fieldName;
    }
}
