
package com.virtualclassroom.exceptions;

public class AssignmentNotFoundException extends Exception {
    public AssignmentNotFoundException() {
        super("Assignment not found in this classroom.");
    }
}