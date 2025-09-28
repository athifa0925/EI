
package com.virtualclassroom.exceptions;

public class AlreadySubmittedException extends Exception {
    public AlreadySubmittedException() {
        super("Student has already submitted this assignment.");
    }
}