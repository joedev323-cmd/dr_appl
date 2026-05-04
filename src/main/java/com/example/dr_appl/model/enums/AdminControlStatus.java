package com.example.dr_appl.model.enums;

public enum AdminControlStatus {
    ACTIVE,             // Regular operational status
    SUSPENDED,          // Admin has cut off access
    PENDING_APPROVAL    // Newly registered, waiting for credential check
}