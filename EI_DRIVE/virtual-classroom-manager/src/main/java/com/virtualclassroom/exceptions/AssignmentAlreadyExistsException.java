
package com.virtualclassroom.exceptions;

public class AssignmentAlreadyExistsException extends Exception {
    public AssignmentAlreadyExistsException() {
        super("Assignment already exists for this classroom.");
    }
}