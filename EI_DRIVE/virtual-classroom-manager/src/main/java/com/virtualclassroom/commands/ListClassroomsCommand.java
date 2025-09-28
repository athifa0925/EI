
package com.virtualclassroom.commands;

import com.virtualclassroom.service.VirtualClassroomService;

import java.util.List;

public class ListClassroomsCommand implements Command {
    private final VirtualClassroomService service;

    public ListClassroomsCommand(VirtualClassroomService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 0) {
            throw new IllegalArgumentException("Usage: list_classrooms");
        }
        List<String> classrooms = service.listClassrooms();
        if (classrooms.isEmpty()) {
            System.out.println("Total classrooms: 0");
            System.out.println("No classrooms available.");
        } else {
            System.out.println("Total classrooms: " + classrooms.size());
            System.out.println("Classrooms:");
            for (String name : classrooms) {
                List<String> students = service.listStudents(name);
                int count = students.size();
                System.out.println("- " + name + " (Students: " + count + ")");
            }
        }
    }
}