package com.virtualclassroom.exceptions;

public class ClassroomAlreadyExistsException extends Exception {
    public ClassroomAlreadyExistsException() {
        super("Classroom already exists.");
    }
}