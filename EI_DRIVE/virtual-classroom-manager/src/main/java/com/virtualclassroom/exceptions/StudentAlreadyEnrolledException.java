package com.virtualclassroom.exceptions;

public class StudentAlreadyEnrolledException extends Exception {
    public StudentAlreadyEnrolledException() {
        super("Student already enrolled in this classroom.");
    }
}