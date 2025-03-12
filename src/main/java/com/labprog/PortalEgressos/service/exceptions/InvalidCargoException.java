package com.labprog.PortalEgressos.service.exceptions;

public class InvalidCargoException extends IllegalArgumentException {
    public InvalidCargoException(String s) {
        super(s);
    }
}
