
package com.virtualclassroom.exceptions;

public class ClassroomNotFoundException extends Exception {
    public ClassroomNotFoundException() {
        super("Classroom not found.");
    }
}