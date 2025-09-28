
package com.virtualclassroom.model;

import com.virtualclassroom.exceptions.AssignmentAlreadyExistsException;
import com.virtualclassroom.exceptions.AssignmentNotFoundException;
import com.virtualclassroom.exceptions.StudentAlreadyEnrolledException;
import com.virtualclassroom.exceptions.StudentNotEnrolledException;

import java.util.*;

public class Classroom {
    private final String name;
    private final Set<String> students;
    private final Map<String, Set<String>> assignments; // assignment title -> set of submitted student IDs

    public Classroom(String name) {
        this.name = name;
        this.students = new HashSet<>();
        this.assignments = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addStudent(String studentId) throws StudentAlreadyEnrolledException {
        if (studentId == null || studentId.isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        if (!students.add(studentId)) {
            throw new StudentAlreadyEnrolledException();
        }
    }

    public void scheduleAssignment(String title) throws AssignmentAlreadyExistsException {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Assignment title cannot be null or empty.");
        }
        if (assignments.containsKey(title)) {
            throw new AssignmentAlreadyExistsException();
        }
        assignments.put(title, new HashSet<>());
    }

    public void submitAssignment(String studentId, String title) throws StudentNotEnrolledException, AssignmentNotFoundException, StudentAlreadyEnrolledException {
        if (!students.contains(studentId)) {
            throw new StudentNotEnrolledException();
        }
        Set<String> submissions = assignments.get(title);
        if (submissions == null) {
            throw new AssignmentNotFoundException();
        }
        if (!submissions.add(studentId)) {
            throw new StudentAlreadyEnrolledException(); // Reusing exception for already submitted, but could create new
        }
    }

    public Set<String> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    public Set<String> getAssignments() {
        return Collections.unmodifiableSet(assignments.keySet());
    }

    public Set<String> getSubmissionsForAssignment(String title) {
        Set<String> submissions = assignments.get(title);
        if (submissions == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(submissions);
    }
}