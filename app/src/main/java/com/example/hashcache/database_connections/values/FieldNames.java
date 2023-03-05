package com.example.hashcache.database_connections.values;

public enum FieldNames {
    GENERATED_NAME("generatedName"),
    GENERATED_SCORE("generatedScore"),
    CODE_LOCATION_ID("codeLocationId"),
    SCANNABLE_CODE_ID("scannableCodeId"),
    COMMENT_BODY("body"),
    RECORD_GEOLOCATION("recordGeoLocation"),
    COMMENTATOR_ID("commentatorId");

    public final String fieldName;

    private FieldNames(String fieldName) {
        this.fieldName = fieldName;
    }
}