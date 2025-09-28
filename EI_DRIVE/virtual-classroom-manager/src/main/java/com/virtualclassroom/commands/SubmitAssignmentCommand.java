
package com.virtualclassroom.commands;

import com.virtualclassroom.service.VirtualClassroomService;

public class SubmitAssignmentCommand implements Command {
    private final VirtualClassroomService service;

    public SubmitAssignmentCommand(VirtualClassroomService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: submit_assignment <student_id> <class_name> <assignment_title>");
        }
        service.submitAssignment(args[0], args[1], args[2]);
        System.out.println("Assignment submitted by Student " + args[0] + " in " + args[1] + ".");
    }
}