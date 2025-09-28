package com.virtualclassroom.exceptions;

public class InvalidRoleException extends Exception {
    public InvalidRoleException() {
        super("Invalid role. Please use 'assigner' or 'student'.");
    }

    public InvalidRoleException(String message) {
        super(message);
    }
}