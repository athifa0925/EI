
package com.virtualclassroom.commands;

import com.virtualclassroom.service.VirtualClassroomService;

public class AddStudentCommand implements Command {
    private final VirtualClassroomService service;

    public AddStudentCommand(VirtualClassroomService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: add_student <student_id> <class_name>");
        }
        service.addStudent(args[0], args[1]);
        System.out.println("Student " + args[0] + " has been enrolled in " + args[1] + ".");
    }
}