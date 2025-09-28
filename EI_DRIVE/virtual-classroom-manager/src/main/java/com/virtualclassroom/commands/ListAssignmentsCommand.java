
package com.virtualclassroom.commands;

import com.virtualclassroom.exceptions.ClassroomNotFoundException;
import com.virtualclassroom.service.VirtualClassroomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListAssignmentsCommand implements Command {
    private final VirtualClassroomService service;

    public ListAssignmentsCommand(VirtualClassroomService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: list_assignments <class_name>");
        }
        String className = args[0];
        List<String> assignments;
        try {
            assignments = service.listAssignments(className);
        } catch (ClassroomNotFoundException e) {
            throw e;
        }
        System.out.println("Total assignments in " + className + ": " + assignments.size());
        if (assignments.isEmpty()) {
            System.out.println("No assignments in " + className + ".");
            return;
        }
        List<String> students = service.listStudents(className);
        System.out.println("Assignments in " + className + ":");
        for (String title : assignments) {
            System.out.println("- " + title + ":");
            if (students.isEmpty()) {
                System.out.println("  No students enrolled.");
            } else {
                Set<String> submissions = service.getSubmissions(className, title);
                List<String> sortedStudents = new ArrayList<>(students);
                sortedStudents.sort(String::compareTo);
                for (String student : sortedStudents) {
                    String status = submissions.contains(student) ? "Submitted" : "Not Submitted";
                    System.out.println("  - " + student + ": " + status);
                }
            }
        }
    }
}