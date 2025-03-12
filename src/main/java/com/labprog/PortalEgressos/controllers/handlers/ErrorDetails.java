package com.labprog.PortalEgressos.controllers.handlers;

import java.util.Date;

public record ErrorDetails(Date time, String message) {
    public ErrorDetails(String message) {
        this(new Date(), message);
    }
}
