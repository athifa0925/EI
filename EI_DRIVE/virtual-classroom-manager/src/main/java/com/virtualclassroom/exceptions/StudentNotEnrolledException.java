
package com.virtualclassroom.exceptions;

public class StudentNotEnrolledException extends Exception {
    public StudentNotEnrolledException() {
        super("Student not enrolled in this classroom.");
    }
}