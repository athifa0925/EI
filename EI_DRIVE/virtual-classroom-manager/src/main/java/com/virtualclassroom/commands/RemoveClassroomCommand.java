
package com.virtualclassroom.commands;

import com.virtualclassroom.service.VirtualClassroomService;

public class RemoveClassroomCommand implements Command {
    private final VirtualClassroomService service;

    public RemoveClassroomCommand(VirtualClassroomService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: remove_classroom <name>");
        }
        service.removeClassroom(args[0]);
        System.out.println("Classroom " + args[0] + " has been removed.");
    }
}