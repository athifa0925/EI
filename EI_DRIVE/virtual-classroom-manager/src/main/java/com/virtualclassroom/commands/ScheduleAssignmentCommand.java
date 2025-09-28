
package com.virtualclassroom.commands;

import com.virtualclassroom.service.VirtualClassroomService;

public class ScheduleAssignmentCommand implements Command {
    private final VirtualClassroomService service;

    public ScheduleAssignmentCommand(VirtualClassroomService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: schedule_assignment <class_name> <assignment_title>");
        }
        service.scheduleAssignment(args[0], args[1]);
        System.out.println("Assignment for " + args[0] + " has been scheduled.");
    }
}