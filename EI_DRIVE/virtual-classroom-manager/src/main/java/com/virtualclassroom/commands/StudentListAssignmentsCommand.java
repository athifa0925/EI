
package com.virtualclassroom.commands;

import com.virtualclassroom.exceptions.ClassroomNotFoundException;
import com.virtualclassroom.service.VirtualClassroomService;

import java.util.List;
import java.util.Set;

public class StudentListAssignmentsCommand implements Command {
    private final VirtualClassroomService service;
    private final String className;

    public StudentListAssignmentsCommand(VirtualClassroomService service, String className) {
        this.service = service;
        this.className = className;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 0) {
            throw new IllegalArgumentException("Usage: list_assignments");
        }
        List<String> assignments;
        try {
            assignments = service.listAssignments(className);
        } catch (ClassroomNotFoundException e) {
            throw e;
        }
        if (assignments.isEmpty()) {
            System.out.println("No assignments in " + className + ".");
            return;
        }
        System.out.println("Assignments in " + className + ":");
        for (String title : assignments) {
            System.out.println("- " + title);
        }
    }
}