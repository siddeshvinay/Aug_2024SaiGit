package com.corcentric.utiles;

public enum GridRecordAction {
    VIEW_COMPANY_SUPPLIER("View Company Suppliers"),
    OPEN_IN_NEW_TAB("Open in New Tab"),
    LOGIN_TO_PAYNET("Login to PayNet"),
    VIEW("View"),
    EDIT("Edit");

    private final String operation;

    GridRecordAction(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return this.operation;
    }
}
