
package com.virtualclassroom.commands;

import com.virtualclassroom.service.VirtualClassroomService;

public class StudentSubmitAssignmentCommand implements Command {
    private final VirtualClassroomService service;
    private final String studentId;
    private final String className;

    public StudentSubmitAssignmentCommand(VirtualClassroomService service, String studentId, String className) {
        this.service = service;
        this.studentId = studentId;
        this.className = className;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: submit_assignment <assignment_title>");
        }
        service.submitAssignment(studentId, className, args[0]);
        System.out.println("Assignment submitted by Student " + studentId + " in " + className + ".");
    }
}