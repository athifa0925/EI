
package com.virtualclassroom.commands;

import com.virtualclassroom.exceptions.ClassroomNotFoundException;
import com.virtualclassroom.service.VirtualClassroomService;

import java.util.List;

public class ListStudentsCommand implements Command {
    private final VirtualClassroomService service;

    public ListStudentsCommand(VirtualClassroomService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: list_students <class_name>");
        }
        String className = args[0];
        List<String> students;
        try {
            students = service.listStudents(className);
        } catch (ClassroomNotFoundException e) {
            throw e;
        }
        System.out.println("Total students in " + className + ": " + students.size());
        if (students.isEmpty()) {
            System.out.println("No students in " + className + ".");
        } else {
            System.out.println("Students in " + className + ":");
            for (String student : students) {
                System.out.println("- " + student);
            }
        }
    }
}